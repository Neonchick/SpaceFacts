import SwiftUI
import shared

struct ViewStateScreen: View {
    
    @ObservedObject
    var observableModel = ObservableViewStateModel()
    var body: some View {
        TabView {
            NavigationView {
                SpacePictureListView(
                    pictures: observableModel.picturesFromNet ?? [:],
                    observableModel: observableModel
                )
            }.tabItem {
                    Image(systemName: "cloud")
                    Text("New")
                }

            NavigationView {
                SpacePictureListView(
                    pictures: observableModel.picturesFromDB ?? [:],
                    observableModel: observableModel
                )
            }.tabItem {
                    Image(systemName: "heart.fill")
                    Text("Saved")
                }
        }.onAppear(perform: { observableModel.activate() })
    }
}

class ObservableViewStateModel: ObservableObject {

    var viewModel: MpViewModel?

    @Published
    var picturesFromNet: Dictionary<String, SpacePictureVO>?
    
    @Published
    var picturesFromDB: Dictionary<String, SpacePictureVO>?

    func activate() {
        viewModel = MpViewModel (
            onPicturesFromNet: { [weak self] picturesFromNet in
                self?.picturesFromNet = picturesFromNet
            },
            onPicturesFromDB: { [weak self] picturesFromDB in
                self?.picturesFromDB = picturesFromDB
            }
        )
        viewModel?.getPicturesFromNet()
    }

}
