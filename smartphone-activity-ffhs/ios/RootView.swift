//
//  RootView.swift
//  SmartphoneActivity
//
//  Created by Devin Hasler on 18.11.2024.
//

import SwiftUI
import FamilyControls

struct RootView: View {
    @State private var isAuthorized = false
    @ObservedObject var model: ScreenTimeSelectedAppsModel
    let requestAuthorization = RequestAuthorization()
    
    
    
    var body: some View {
        Color.blue
        ZStack {
            if isAuthorized {
                Text("Authorized")
                ReportView()
            } else {
                FamilyPickerView(model: model)
            }
        }
        .onAppear {
            Task {
                isAuthorized = await requestAuthorization.requestFamilyControls(for: .individual)
                debugPrint("\(isAuthorized)")
            }
        }
    }
    

    
}


