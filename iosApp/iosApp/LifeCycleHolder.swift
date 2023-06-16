//
//  LifeCycleHolder.swift
//  iosApp
//
//  Created by Anshul Upadhyay on 15/06/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import shared

class LifeCycleHolder : ObservableObject {
    
    let lifecycle:LifecycleRegistry
    
    init(){
        lifecycle = LifecycleRegistryKt.LifecycleRegistry()
        
        lifecycle.onCreate()
    }
    
    deinit {
        lifecycle.onDestroy()
    }

}
