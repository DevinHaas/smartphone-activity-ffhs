//
//  ActivityView.swift
//  SmartphoneActivity
//
//  Created by Devin Hasler on 15.11.2024.
//

import SwiftUI
import FamilyControls


struct FamilyPickerView: View {
    
    @State private var isAuthorized = false
    @State private var pickerIsPresented = false
    @ObservedObject var model: ScreenTimeSelectedAppsModel
    
    var body: some View {
        
        Button {
            Task {
                pickerIsPresented = true
            }
        } label: {
            Text("Select Apps")
        }
        .familyActivityPicker(isPresented: $pickerIsPresented, selection: $model.activitySelection)
    }
    
    
}
