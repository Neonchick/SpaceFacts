//
//  FullInfoScreen.swift
//  iosApp
//
//  Created by Mikhail F. Kuznetsov on 25.01.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

struct FullInfoScreeen: View {
    
    let picture: SpacePictureVO
    
    @ObservedObject
    var observableModel: ObservableViewStateModel
    
    @State
    private var isSaved = false
    
    init(
        picture: SpacePictureVO,
        observableModel: ObservableViewStateModel
    ) {
        self.picture = picture
        self.isSaved = picture.isSaved
        self.observableModel = observableModel
    }
    
    var body: some View {
        ScrollView(.vertical, showsIndicators: false) {
            VStack{
                Text(picture.title)
                    .font(Font.system(size: 22, design: Font.Design.default))
                    .bold()
                AsyncImage(url: URL(string: picture.url)) { image in
                   image.resizable()
                       .aspectRatio(contentMode: .fit)
                } placeholder: {
                   ProgressView()
                }.padding(.top, 20)
                Text(picture.date)
                    .padding(.top, 20)
                Text(picture.explanation)
                    .padding(.top, 20)
            }.padding(30)
        }
    }
    
}

