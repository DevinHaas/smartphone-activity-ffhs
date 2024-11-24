//
//  DeviceActivityReporterAdapter.swift
//  Pods
//
//  Created by Devin Hasler on 23.11.2024.
//


//
//  DeviceActivityReportAdapter.swift
//  DeviceActivityApp
//
//  Created by Pedro Somensi on 06/08/23.
//

import SwiftUI
import DeviceActivity

struct DeviceActivityReporterAdapter: View {
    
    @State private var context: DeviceActivityReport.Context = .totalActivity
    @State private var filter = DeviceActivityFilter(
        segment: .daily(
            during: Calendar.current.dateInterval(
                of: .day, for: .now
            )!
        ),
        users: .all,
        devices: .init([.iPad])
    )
    
    var body: some View {
        ZStack {
            DeviceActivityReport(context, filter: filter)
        }
    }
    
}
