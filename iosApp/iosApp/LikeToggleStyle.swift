//
//  LikeToggleStyle.swift
//  iosApp
//
//  Created by Mikhail F. Kuznetsov on 25.01.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct LikeToggleStyle: ToggleStyle {
    func makeBody(configuration: Configuration) -> some View {
        return HStack {
            configuration.label
            Spacer()
            Image(systemName: configuration.isOn ? "heart.fill" : "heart")
                .resizable()
                .renderingMode(.template)
                .foregroundColor(.pink)
                .frame(width: 22, height: 22)
                .onTapGesture { configuration.isOn.toggle() }
        }
    }
}
