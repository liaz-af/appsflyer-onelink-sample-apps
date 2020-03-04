//
//  ViewController.swift
//  helloworldaf1
//
//  Created by Liaz Kamper on 13/02/2020.
//  Copyright © 2020 Liaz Kamper. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        view.backgroundColor = .blue
    }
    
    @IBAction func testNavToShoes(_ sender: Any) {
        let storyBoard: UIStoryboard = UIStoryboard(name: "Main", bundle: nil)
//        let newViewV = storyBoard.instantiateViewController(withIdentifier: "shoes_vc")
//
        if let newViewC = storyBoard.instantiateVC(withIdentifier: "foobla") {
            // Use viewController here
            navigationController?.pushViewController(newViewC, animated: false)
        }
    }
}


