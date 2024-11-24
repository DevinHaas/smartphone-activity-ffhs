//
//  MonitorReport.swift
//  MonitorReport
//
//  Created by Devin Hasler on 23.11.2024.
//

import DeviceActivity
import SwiftUI

@main
struct MonitorReport: DeviceActivityReportExtension {
    var body: some DeviceActivityReportScene {
        // Create a report for each DeviceActivityReport.Context that your app supports.
        TotalActivityReport { totalActivity in
            TotalActivityView(totalActivity: totalActivity)
        }
        // Add more reports here...
    }
}
