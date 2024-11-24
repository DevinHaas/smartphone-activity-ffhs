package expo.modules.smartphoneactivityffhs

import android.app.usage.UsageStatsManager
import android.content.Context
import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Test
import java.util.Calendar
import kotlin.system.measureTimeMillis

class LoadTest {


    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
    private val usageStatsManager: UsageStatsManager =
        context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager

    // Simulates increasing loads by querying data multiple times
    @Test
    fun testLoadPerformance() {
        val loadLevels = listOf(10, 100, 500, 1000) // Simulate apps count

        loadLevels.forEach { load ->
            Log.d("LoadTest", "Testing with load: $load apps")

            val responseTime = measureTimeMillis {
                for (i in 1..load) {
                    fetchDailyUsageStats()
                }
            }

            Log.d("LoadTest", "Load: $load apps, Response Time: $responseTime ms")
        }
    }

    // Simulate fetching daily usage stats
    private fun fetchDailyUsageStats() {
        val calendar = Calendar.getInstance()
        val endTime = calendar.timeInMillis
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val beginTime = calendar.timeInMillis

        usageStatsManager.queryAndAggregateUsageStats(beginTime, endTime)
    }


    @Test
    fun testResponseTime() {
        val calendar = Calendar.getInstance()
        val endTime = calendar.timeInMillis
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val beginTime = calendar.timeInMillis

        val responseTime = measureTimeMillis {
            val usageStats = usageStatsManager.queryAndAggregateUsageStats(beginTime, endTime)
            Log.d("ResponseTimeTest", "Fetched ${usageStats.size} apps' stats")
        }

        Log.d("ResponseTimeTest", "Response Time: $responseTime ms")
    }
}