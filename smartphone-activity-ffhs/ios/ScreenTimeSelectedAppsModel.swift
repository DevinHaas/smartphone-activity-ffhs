//
//  ScreenTimeSelectedAppsModel.swift
//  SmartphoneActivity
//
//  Created by Devin Hasler on 15.11.2024.
//

import Foundation
import FamilyControls


class ScreenTimeSelectedAppsModel: ObservableObject {
    @Published var activitySelection: FamilyActivitySelection = FamilyActivitySelection()
    
    private let userDefaultsKey = "ScreenTimeSelection"
    private let encoder = PropertyListEncoder()
    private let decoder = PropertyListDecoder()
    
    
    init() {
        loadSelection()
    }
    
    func saveSelection(selection: FamilyActivitySelection) {
        let defaults = UserDefaults.standard
        if let encodedSelection = try? encoder.encode(selection) {
            defaults.set(encodedSelection, forKey: userDefaultsKey)
        }
        print("Saved selection: \(selection.categories.count)")
    }
    
    
    private func loadSelection() {
        let defaults = UserDefaults.standard
        if let data = defaults.data(forKey: userDefaultsKey),
           let savedSelection = try? decoder.decode(FamilyActivitySelection.self, from: data) {
            activitySelection = savedSelection
        }
    }
    
}
