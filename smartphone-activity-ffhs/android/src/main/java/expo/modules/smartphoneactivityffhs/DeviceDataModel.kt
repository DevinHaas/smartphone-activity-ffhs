package expo.modules.smartphoneactivityffhs

import android.app.usage.UsageStats
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DeviceDataModel: ViewModel() {

    private val _usageStats = MutableStateFlow<Map<String, UsageStats>>(emptyMap())
    private val _dailyUsage = MutableStateFlow(0L)
    private val _weeklyUsage = MutableStateFlow(0L)


    val dailyUsage: StateFlow<Long> get() = _dailyUsage
    val weeklyUsage: StateFlow<Long> get() = _weeklyUsage
    val usageStats: StateFlow<Map<String, UsageStats>> get() = _usageStats

    // Function to update usage stats
    fun updateUsageStats(newStats: Map<String, UsageStats>) {
        _usageStats.value = newStats
    }


    fun updateDailyUsage(newDailyUsage: Long){
        _dailyUsage.value = newDailyUsage
    }

    fun updateWeeklyUsage(newWeeklyData:Long){
        _weeklyUsage.value = newWeeklyData
    }
}