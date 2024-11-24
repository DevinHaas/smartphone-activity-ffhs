//
//  FFHSDeviceActivityResults.swift
//  FFHSDeviceActivityResults
//
//  Created by Devin Hasler on 22.11.2024.
//

import DeviceActivity
import SwiftUI

struct FFHSDeviceActivityResults: DeviceActivityReportExtension {
    var body: some DeviceActivityReportScene {
        // Create a report for each DeviceActivityReport.Context that your app supports.
        TotalActivityReport { totalActivity in
            TotalActivityView(deviceActivity: totalActivity)
        }
        // Add more reports here...
    }
}
