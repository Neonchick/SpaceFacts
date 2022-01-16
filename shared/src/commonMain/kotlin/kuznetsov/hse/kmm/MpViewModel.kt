package kuznetsov.hse.kmm

import co.touchlab.kermit.platformLogWriter
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kuznetsov.hse.kmm.database.DatabaseInitializer
import kuznetsov.hse.kmm.database.selectByPlatform

class MpViewModel(private val onViewState: ((ViewState) -> Unit)? = null) {

    private val client = SpacePictureHttpClient()

    private val scope = MainScope()

    private val _viewStateFlow = MutableStateFlow(
        ViewState(Greeting().greeting())
    )

    val stateFlow: StateFlow<ViewState> = _viewStateFlow

    val database = DatabaseInitializer().database

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
            getPictureTitleDb().onEach { spacePictureDB ->
                val phrase = spacePictureDB?.title?.let { pictureTitle ->
                     "Here will be picture with title \"$pictureTitle\" on ${Platform().platform}"
                } ?: Greeting().greeting()
                _viewStateFlow.value = ViewState(phrase)
            }.launchIn(scope = this)
            getPictureTitleNetw()
        }
    }

    private fun getPictureTitleDb(): Flow<SpacePictureDB?> {
        return database.selectByPlatform(Platform().platform).map { spacePictureDBList ->
            spacePictureDBList.firstOrNull()
        }
    }

    private suspend fun getPictureTitleNetw() {
        try {
            val platform = Platform().platform
            val pictures: List<SpacePicture> = client.getPictures(1)
            val pictureTitle = pictures.firstOrNull()?.title ?: ""
            database.spacePicturesQueries
                .insertStats(id = null, platform = platform, title = pictureTitle)
        } catch (error: Exception) {
            platformLogWriter().d(
                message = "No network",
                tag = "GET_PICTURE_TITLE",
                throwable = error
            )
        }
    }

}