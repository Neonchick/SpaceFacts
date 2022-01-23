import SwiftUI
import shared

struct ViewStateScreen: View {
    @ObservedObject
    var observableModel = ObservableViewStateModel()
    var body: some View {
        ScrollView(.vertical, showsIndicators: false) {
            VStack{
                Text(observableModel.viewState?.title ?? "")
                    .font(Font.system(size: 22, design: Font.Design.default))
                    .bold()
                AsyncImage(url: URL(string: observableModel.viewState?.url ?? "")) { image in
                   image.resizable()
                       .aspectRatio(contentMode: .fit)
                } placeholder: {
                   ProgressView()
                }.padding(.top, 20)
                Text(observableModel.viewState?.date ?? "")
                    .padding(.top, 20)
                Text(observableModel.viewState?.explanation ?? "")
                    .padding(.top, 20)
            }.onAppear(perform: {
                    observableModel.activate()
                }
            ).padding(30)
        }
    }
}

class ObservableViewStateModel: ObservableObject {

    private var viewModel: MpViewModel?

    @Published
    var viewState: ViewState?

    func activate() {
        viewModel = MpViewModel { [weak self] viewState in
            self?.viewState = viewState
        }
        viewModel?.getPictureTitle()
    }

}
