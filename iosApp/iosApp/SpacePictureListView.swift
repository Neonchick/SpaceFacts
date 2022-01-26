//
//  SpacePictureListView.swift
//  iosApp
//
//  Created by Mikhail F. Kuznetsov on 25.01.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

struct SpacePictureListView: View {
    
    let pictures: Dictionary<String, SpacePictureVO>
    
    @ObservedObject
    var observableModel: ObservableViewStateModel
    
    init(
        pictures: Dictionary<String, SpacePictureVO>,
        observableModel: ObservableViewStateModel
    ) {
        self.pictures = pictures
        self.observableModel = observableModel
    }
    
    var body: some View {
        let picturesList = pictures.map { $0.value }.sorted(by: { $0.title < $1.title })
        List(picturesList, id: \.title) { picture in
            ListElementView(picture: picture, observableModel: observableModel)
        }
    }

}
