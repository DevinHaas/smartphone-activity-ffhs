import ExpoModulesCore
import WebKit
import SwiftUI
import UIKit
import Combine
import FamilyControls


// This view will be used as a native component. Make sure to inherit from `ExpoView`
// to apply the proper styling (e.g. border radius and shadows).
class SmartphoneActivityFfhsView: ExpoView {
    let model = ScreenTimeSelectedAppsModel()
    
    private let contentView: UIHostingController<RootView>
    
    private var cancellables = Set<AnyCancellable>()

    
    private let userDefaultsKey = "ScreenTimeSelection"
    
    required init(appContext: AppContext? = nil) {
        let rootView = RootView(model: model)
        
        contentView = UIHostingController(rootView: rootView)
        
        super.init(appContext: appContext)
        
        clipsToBounds = true
        addSubview(contentView.view)
    }
    
    override func layoutSubviews() {
        contentView.view.frame = bounds
    }
}
