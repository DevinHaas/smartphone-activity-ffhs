package expo.modules.smartphoneactivityffhs

import android.app.usage.UsageStats
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DeviceDataForm(deviceDataModel: DeviceDataModel) {

    val dailyUsage by deviceDataModel.dailyUsage.collectAsState()
    val weeklyUsage by deviceDataModel.weeklyUsage.collectAsState()
    val usageStats by deviceDataModel.usageStats.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Daily Usage: ${formatTime(dailyUsage)}",
            style = MaterialTheme.typography.bodyLarge ,
                    color = Color.White

        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Weekly Usage: ${formatTime(weeklyUsage)}",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "App Usage Details",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(usageStats.entries.toList()) { (packageName, stats) ->
                AppUsageItem(packageName = packageName, timeInForeground = stats.totalTimeInForeground)
            }
        }
    }
}


@Composable
fun AppUsageItem(packageName: String, timeInForeground: Long) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Package: $packageName",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Usage Time: ${formatTime(timeInForeground)}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

fun formatTime(timeInMillis: Long): String {
    val totalMinutes = timeInMillis / 1000 / 60
    val hours = totalMinutes / 60
    val minutes = totalMinutes % 60
    return "${hours}h ${minutes}m"
}