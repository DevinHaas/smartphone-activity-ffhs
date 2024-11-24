//
//  TotalActivityView.swift
//  FFHSDeviceActivityResults
//
//  Created by Devin Hasler on 22.11.2024.
//
import SwiftUI

struct TotalActivityView: View {
    
    var deviceActivity: DeviceActivity
    
    var body: some View {
        ActivitiesView(activities: deviceActivity)
    }
    
}
