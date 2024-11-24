package expo.modules.smartphoneactivityffhs

import android.app.AppOpsManager
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import expo.modules.kotlin.AppContext
import expo.modules.kotlin.views.ExpoView
import java.util.Calendar

class SmartphoneActivityFfhsView(context: Context, appContext: AppContext) : ExpoView(context, appContext) {

  private val deviceDataModel = DeviceDataModel();

  internal val composeView = ComposeView(context).also {

    it.layoutParams = LayoutParams(
      LayoutParams.WRAP_CONTENT,
      LayoutParams.WRAP_CONTENT
    )

    it.setContent {
      if (checkUsageStatsPermission()) {
        val usageStats by deviceDataModel.usageStats.collectAsState()
        if (usageStats.isEmpty()) {
          fetchUsageStats(context, UsageType.WEEKLY)
          fetchUsageStats(context, UsageType.DAILY)
        }
        DeviceDataForm(deviceDataModel = deviceDataModel)
      } else {
        RequestPermissionScreen(onRequestPermission = {
          promptUsageStatsPermission(context)
        })
      }
    }

    addView(it)
  }


  private fun fetchUsageStats(context: Context, usageType: UsageType) {
    val calendar = Calendar.getInstance()
    val endTime = calendar.timeInMillis

    when (usageType) {
      UsageType.DAILY -> {
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
      }
      UsageType.WEEKLY -> {
        val currentDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        val daysToSubtract = if (currentDayOfWeek == Calendar.MONDAY) {
          0
        } else {
          if (currentDayOfWeek == Calendar.SUNDAY) {
            6
          } else {
            currentDayOfWeek - Calendar.MONDAY
          }
        }

        calendar.add(Calendar.DAY_OF_YEAR, -daysToSubtract)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
      }
    }

    val beginTime = calendar.timeInMillis // Start time based on usage type

    println("Fetching Usage Stats for $usageType: BeginTime = ${java.util.Date(beginTime)}, EndTime = ${java.util.Date(endTime)}")
    Log.d("UsageStats", "Fetching Usage Stats for DAILY")
    Log.d("UsageStats", "BeginTime: ${java.util.Date(beginTime)}, EndTime: ${java.util.Date(endTime)}")



    // Fetch stats and update the state
    val filteredUsageStats = getFilteredUsageStats(context, beginTime, endTime, usageType)

    when (usageType) {
      UsageType.DAILY -> deviceDataModel.updateDailyUsage(filteredUsageStats.values.sumOf { it.totalTimeInForeground })
      UsageType.WEEKLY -> deviceDataModel.updateWeeklyUsage(filteredUsageStats.values.sumOf { it.totalTimeInForeground })
    }

    deviceDataModel.updateUsageStats(filteredUsageStats)
  }

  private fun checkUsageStatsPermission(): Boolean {
    val appOpsManager = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
    val mode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
      appOpsManager.unsafeCheckOpNoThrow(
        AppOpsManager.OPSTR_GET_USAGE_STATS,
        android.os.Process.myUid(),
        context.packageName
      )
    } else {
      appOpsManager.checkOpNoThrow(
        AppOpsManager.OPSTR_GET_USAGE_STATS,
        android.os.Process.myUid(),
        context.packageName
      )
    }
    return mode == AppOpsManager.MODE_ALLOWED
  }

  private fun promptUsageStatsPermission(context: Context) {
    Toast.makeText(context, "Usage Stats Permission Required", Toast.LENGTH_SHORT).show()
    val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    context.startActivity(intent)
  }



  private fun getNonSystemAppsList(context: Context): Map<String, String> {
    val packageManager = context.packageManager
    val appInfoMap = HashMap<String, String>()

    try {
      val intent = Intent(Intent.ACTION_MAIN)
      intent.addCategory(Intent.CATEGORY_LAUNCHER)
      val resolveInfos = packageManager.queryIntentActivities(intent, PackageManager.MATCH_ALL)

      for (resolveInfo in resolveInfos) {
        val packageName = resolveInfo.activityInfo.packageName
        val applicationInfo = packageManager.getApplicationInfo(packageName, 0)

        if ((applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM) == 0) {
          appInfoMap[packageName] = packageManager.getApplicationLabel(applicationInfo).toString()
        }
      }
    } catch (e: Exception) {
      e.printStackTrace()
    }

    return appInfoMap
  }


  private fun getUsageStats(
    context: Context,
    beginTime: Long,
    endTime: Long,
    usageType: UsageType
  ): Map<String, UsageStats> {
    val usageStatsManager = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
    val usageStats = usageStatsManager.queryAndAggregateUsageStats(beginTime, endTime)

    var totalUsage: Long = 0L

    usageStats.forEach { (packageName, stats) ->
      totalUsage += stats.totalTimeInForeground
      println("Package: $packageName, Total Time: ${stats.totalTimeInForeground} ms")
    }

    when (usageType) {
      UsageType.DAILY -> deviceDataModel.updateDailyUsage(totalUsage)
      UsageType.WEEKLY -> deviceDataModel.updateWeeklyUsage(totalUsage)
    }

    return usageStats
  }

  private fun getFilteredUsageStats(
    context: Context,
    beginTime: Long,
    endTime: Long,
    usageType: UsageType
  ): Map<String, UsageStats> {
    val usageStatsManager = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
    val usageStats = usageStatsManager.queryAndAggregateUsageStats(beginTime, endTime)

    val nonSystemApps = getNonSystemAppsList(context)

    val filteredUsageStats = usageStats.filterKeys { packageName ->
      nonSystemApps.containsKey(packageName)
    }

    var usage : Long = 0L;

    filteredUsageStats.forEach { (packageName, stats) ->
      val appName = nonSystemApps[packageName] ?: packageName
      usage += stats.totalTimeInForeground
      println("App: $appName, Package: $packageName, Total Time: ${stats.totalTimeInForeground} ms")
    }

    when (usageType){
      UsageType.DAILY -> deviceDataModel.updateDailyUsage(usage)
      UsageType.WEEKLY -> deviceDataModel.updateWeeklyUsage(usage)
    }

    return filteredUsageStats
  }
}
