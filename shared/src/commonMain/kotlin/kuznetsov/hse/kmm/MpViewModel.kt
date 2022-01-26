package kuznetsov.hse.kmm

import co.touchlab.kermit.platformLogWriter
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.datetime.toLocalDate
import kuznetsov.hse.kmm.database.DatabaseInitializer
import kuznetsov.hse.kmm.database.selectAll
import kuznetsov.hse.kmm.database.selectByPlatform

class MpViewModel(
    private val onPicturesFromNet: ((Map<String, SpacePictureVO>) -> Unit)? = null,
    private val onPicturesFromDB: ((Map<String, SpacePictureVO>) -> Unit)? = null,
) {

    private val client = SpacePictureHttpClient()

    private val scope = MainScope()

    private val _picturesFromNet = MutableStateFlow(emptyMap<String, SpacePictureVO>())

    val picturesFromNet: StateFlow<Map<String, SpacePictureVO>> = _picturesFromNet

    private val _picturesFromDB = MutableStateFlow(emptyMap<String, SpacePictureVO>())

    val picturesFromDB: StateFlow<Map<String, SpacePictureVO>> = _picturesFromDB

    private val database = DatabaseInitializer().database

    init {
        observe()
    }

    private fun observe() {
        if (onPicturesFromNet != null) {
            scope.launch {
                _picturesFromNet.collect { picturesFromNet ->
                    onPicturesFromNet.invoke(picturesFromNet)
                }
            }
        }
        if (onPicturesFromDB != null) {
            scope.launch {
                _picturesFromDB.collect { picturesFromDB ->
                    onPicturesFromDB.invoke(picturesFromDB)
                }
            }
        }
    }

    fun getPicturesFromNet() {
        scope.launch {
            try {
                getPictureFromDb().onEach { picturesFromDB ->
                    setPictureFromDb(picturesFromDB)
                    _picturesFromNet.value = _picturesFromNet.value.values.map { spacePictureVO ->
                        val spacePictureVO = SpacePictureVO(
                            title = spacePictureVO.title,
                            explanation = spacePictureVO.explanation,
                            url = spacePictureVO.url,
                            date = spacePictureVO.date,
                            isSaved = picturesFromDB.containsKey(spacePictureVO.title),
                        )
                        spacePictureVO.title to spacePictureVO
                    }.toMap()
                }.launchIn(scope = this)
                val pictures = client.getPictures(20)
                _picturesFromNet.value = pictures.map { spacePicture ->
                    val localDate = spacePicture.date.toLocalDate()
                    val formattedDate =
                        "${localDate.dayOfMonth} ${localDate.month} ${localDate.year}"
                    val spacePictureVO = SpacePictureVO(
                        title = spacePicture.title,
                        explanation = spacePicture.explanation,
                        url = spacePicture.url,
                        date = formattedDate,
                        isSaved = _picturesFromDB.value.containsKey(spacePicture.title),
                    )
                    spacePicture.title to spacePictureVO
                }.toMap()
            } catch (error: Exception) {
                platformLogWriter().d(
                    message = "No network",
                    tag = "GET_PICTURE_TITLE",
                    throwable = error
                )
            }
        }
    }

    private fun getPictureFromDb(): Flow<Map<String, SpacePictureDB>> {
        return database.selectAll().map { spacePictureDBList ->
            spacePictureDBList.map { spacePictureDB ->
                spacePictureDB.title to spacePictureDB
            }.toMap()
        }
    }


    private fun setPictureFromDb(pictures: Map<String, SpacePictureDB>) {
        _picturesFromDB.value = pictures.values.map { spacePictureVO ->
            val spacePictureVO = SpacePictureVO(
                title = spacePictureVO.title,
                explanation = spacePictureVO.explanation,
                url = spacePictureVO.url,
                date = spacePictureVO.date,
                isSaved = true,
            )
            spacePictureVO.title to spacePictureVO
        }.toMap()
    }

    fun savePicture(spacePictureVO: SpacePictureVO) {
        val platform = Platform().platform
        database.spacePicturesQueries
            .insertStats(
                platform = platform,
                title = spacePictureVO.title,
                explanation = spacePictureVO.explanation,
                url = spacePictureVO.url,
                date = spacePictureVO.date,
            )
        val qq = database.spacePicturesQueries.selectAll().executeAsList()
    }

    fun deletePicture(spacePictureVO: SpacePictureVO) {
        database.spacePicturesQueries
            .deleteByTitle(title = spacePictureVO.title)
        val qq = database.spacePicturesQueries.selectAll().executeAsList()
    }

    fun getPictureTitle() {
        scope.launch {
            getPictureTitleDb().onEach { spacePictureDB ->
                spacePictureDB?.let { spacePictureDB ->
                    val localDate = spacePictureDB.date.toLocalDate()
                    val formattedDate =
                        "${localDate.dayOfMonth} ${localDate.month} ${localDate.year}"
                    val spacePictureVO = SpacePictureVO(
                        title = spacePictureDB.title,
                        explanation = spacePictureDB.explanation,
                        url = spacePictureDB.url,
                        date = formattedDate,
                        isSaved = true,
                    )
                    _picturesFromNet.value = mapOf(spacePictureDB.title to spacePictureVO)
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
                        platform = platform,
                        title = picture.title,
                        explanation = picture.explanation,
                        url = picture.url,
                        date = picture.date,
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