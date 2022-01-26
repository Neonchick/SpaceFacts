package kuznetsov.hse.kmm.database

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kuznetsov.hse.kmm.SpacePictureDB
import kuznetsov.hse.kmm.SpacePicturesDatabase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DatabaseInitializer: KoinComponent {

    private val databaseDriverFactory: DatabaseDriverFactory by inject()

    val database = SpacePicturesDatabase(databaseDriverFactory.createDriver())

}

fun SpacePicturesDatabase.selectByPlatform(platform: String): Flow<List<SpacePictureDB>> =
    this.spacePicturesQueries
        .selectByPlatform(platform)
        .asFlow()
        .mapToList()
        .flowOn(Dispatchers.Default)

fun SpacePicturesDatabase.selectAll(): Flow<List<SpacePictureDB>> =
    this.spacePicturesQueries
        .selectAll()
        .asFlow()
        .mapToList()
        .flowOn(Dispatchers.Default)

