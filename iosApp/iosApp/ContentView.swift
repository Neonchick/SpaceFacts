import SwiftUI
import shared

struct ContentView: View {
    @ObservedObject var viewStateModel = ObservableViewStateModel()

	var body: some View {
        Text(viewStateModel.viewState?.title ?? "")
            .onAppear {
                viewStateModel.activate()
            }
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}

struct ViewStateScreen: View {
    @ObservedObject
    var observableModel = ObservableViewStateModel()
    var body: some View {
        Text(observableModel.viewState?.title ?? "null")
            .onAppear(perform: {
                observableModel.activate()
            }
        )
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
