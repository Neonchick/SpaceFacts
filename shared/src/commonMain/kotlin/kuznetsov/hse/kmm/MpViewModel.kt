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
        ViewState(
            title = Greeting().greeting(),
            explanation = "",
            url = "",
        )
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
                spacePictureDB?.let { spacePictureDB ->
                    _viewStateFlow.value = ViewState(
                        title = spacePictureDB.title,
                        explanation = spacePictureDB.explanation,
                        url = spacePictureDB.url,
                    )
                }
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
            val picture: SpacePicture? = client.getPictures(1).firstOrNull()
            picture?.let { picture ->
                database.spacePicturesQueries
                    .insertStats(
                        id = null,
                        platform = platform,
                        title = picture.title,
                        explanation = picture.explanation,
                        url = picture.url,
                    )
            }
        } catch (error: Exception) {
            platformLogWriter().d(
                message = "No network",
                tag = "GET_PICTURE_TITLE",
                throwable = error
            )
        }
    }

}