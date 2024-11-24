//
//  RequestAuthorization.swift
//  SmartphoneActivityFfhs
//
//  Created by Devin Hasler on 23.11.2024.
//

import Foundation
import FamilyControls

struct RequestAuthorization {
    
    private let center = AuthorizationCenter.shared
    
    func requestFamilyControls(for value: FamilyControlsMember) async -> Bool {
        
        do {
            try await center.requestAuthorization(for: value)
            return true
        } catch(let error) {
            debugPrint(error)
            return false
        }
        
    }
    
}
