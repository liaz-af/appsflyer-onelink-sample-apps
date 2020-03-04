//
//  SceneDelegate.swift
//  helloworldaf1
//
//  Created by Liaz Kamper on 13/02/2020.
//  Copyright © 2020 Liaz Kamper. All rights reserved.
//

import UIKit
import AppsFlyerLib

@available(iOS 13.0, *)
class SceneDelegate: UIResponder, UIWindowSceneDelegate, AppsFlyerTrackerDelegate {

    var window: UIWindow?
    
    func scene(_ scene: UIScene, continue userActivity: NSUserActivity) {
        AppsFlyerTracker.shared().continue(userActivity, restorationHandler: nil)
    }
    
    func scene(_ scene: UIScene, openURLContexts URLContexts: Set<UIOpenURLContext>) {
        if let url = URLContexts.first?.url {
            AppsFlyerTracker.shared().handleOpen(url, options: nil)
        }
    }

    func scene(_ scene: UIScene, willConnectTo session: UISceneSession, options connectionOptions: UIScene.ConnectionOptions) {
        // Use this method to optionally configure and attach the UIWindow `window` to the provided UIWindowScene `scene`.
        // If using a storyboard, the `window` property will automatically be initialized and attached to the scene.
        // This delegate does not imply the connecting scene or session are new (see `application:configurationForConnectingSceneSession` instead).
        guard let _ = (scene as? UIWindowScene) else { return }
    }

    func sceneDidDisconnect(_ scene: UIScene) {
        // Called as the scene is being released by the system.
        // This occurs shortly after the scene enters the background, or when its session is discarded.
        // Release any resources associated with this scene that can be re-created the next time the scene connects.
        // The scene may re-connect later, as its session was not neccessarily discarded (see `application:didDiscardSceneSessions` instead).
    }

    func sceneDidBecomeActive(_ scene: UIScene) {
        // Called when the scene has moved from an inactive state to an active state.
        // Use this method to restart any tasks that were paused (or not yet started) when the scene was inactive.
        
        AppsFlyerTracker.shared().delegate = self
//        AppsFlyerTracker.shared().trackAppLaunch()
    }

    func sceneWillResignActive(_ scene: UIScene) {
        // Called when the scene will move from an active state to an inactive state.
        // This may occur due to temporary interruptions (ex. an incoming phone call).
    }

    func sceneWillEnterForeground(_ scene: UIScene) {
        // Called as the scene transitions from the background to the foreground.
        // Use this method to undo the changes made on entering the background.
    }

    func sceneDidEnterBackground(_ scene: UIScene) {
        // Called as the scene transitions from the foreground to the background.
        // Use this method to save data, release shared resources, and store enough scene-specific state information
        // to restore the scene back to its current state.
    }
    
    // AppsFlyerTrackerDelegate implementation
    
    //Handle Conversion Data (Deferred Deep Link)
    func onConversionDataSuccess(_ data: [AnyHashable: Any]) {
        print("\(data)")
        if let status = data["af_status"] as? String{
            if(status == "Non-organic"){
                if let is_first_launch = data["is_first_launch"] , let launch_code = is_first_launch as? Int {
                    if(launch_code == 1){
                        print("First Launch")
                        if let section = data["af_sub1"] as? String{
                            print("This is deferred deep linking. Campaign: \(section)")
                            deepLinkingDemux(section, data["parent"])
                        }
                    } else {
                        print("Not First Launch")
                    }
                }
            } else {
                print("This is an organic install.")
            }
        }
    }
    func onConversionDataFail(_ error: Error) {
       print("\(error)")
    }
    
    //Handle Direct Deep Link
    func onAppOpenAttribution(_ data: [AnyHashable: Any]) {
        if let link = data["link"] as? String, let section = data["af_sub1"] as? String{
            print("link:  \(link)")
            deepLinkingDemux(section, data["parent"])
        } else {
            print("Could not find link in conversion data")
        }
    }
    
    private func deepLinkingDemux(_ section:String, _ parent:Any?){
     
        let navigationController = self.window?.rootViewController as? UINavigationController
        //We pop all the VCs till Root in case we push the deep-link VC more than once
        navigationController?.popToRootViewController(animated: false)
        
        let storyBoard: UIStoryboard = UIStoryboard(name: "Main", bundle: nil)
        
        // If parent exists, push the parent right above the root
        // Push the deeplink destination to stack, after checking it exists
        if parent != nil {
            let destParentVC = String(describing: parent!) + "_vc"
            if let parentVC = storyBoard.instantiateVC(withIdentifier: destParentVC) {
                // Use viewController here
                print("AppsFlyer Onelink: routing to section \(parent!)")
                navigationController?.pushViewController(parentVC, animated: false)
            } else {
                print("AppsFlyer Onelink: could not find section: \(parent!)")
            }
        } else {
            print("Not parent parameter")
        }
                
        // Push the deeplink destination to stack, after checking it exists
        let destVC = section + "_vc"
        if let newVC = storyBoard.instantiateVC(withIdentifier: destVC) {
            // Use viewController here
            print("AppsFlyer Onelink: routing to section \(section)")
            navigationController?.pushViewController(newVC, animated: true)
        } else {
            print("AppsFlyer Onelink: could not find section: \(section)")
        }
    }
    
    func onAppOpenAttributionFailure(_ error: Error) {
        print("\(error)")
    }
}

extension UIStoryboard {
    func instantiateVC(withIdentifier identifier: String) -> UIViewController? {
        // "identifierToNibNameMap" – dont change it. It is a key for searching IDs
        if let identifiersList = self.value(forKey: "identifierToNibNameMap") as? [String: Any] {
            if identifiersList[identifier] != nil {
                return self.instantiateViewController(withIdentifier: identifier)
            }
        }
        return nil
    }
}
