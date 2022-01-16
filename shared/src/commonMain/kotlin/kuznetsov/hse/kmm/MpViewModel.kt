package kuznetsov.hse.kmm

import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MpViewModel(private val onViewState: ((ViewState) -> Unit)? = null) {

    private val client = SpacePictureHttpClient()

    private val scope = MainScope()

    private val _viewStateFlow = MutableStateFlow(
        ViewState(Greeting().greeting())
    )

    val stateFlow: StateFlow<ViewState> = _viewStateFlow

    init {
        observe()
    }

    private fun observe() {
        if (onViewState != null) {
            scope.launch {
                _viewStateFlow.collect { viewState ->
                    onViewState.invoke(viewState)
                }
            }
        }
    }

    fun getPictureTitle() {
        scope.launch {
            val pictures: List<SpacePicture> = client.getPictures(1)
            val pictureTitle = pictures.firstOrNull()?.title ?: ""
            _viewStateFlow.value = ViewState(
                "Here will be picture with title \"$pictureTitle\" on ${Platform().platform}"
            )
        }
    }

}