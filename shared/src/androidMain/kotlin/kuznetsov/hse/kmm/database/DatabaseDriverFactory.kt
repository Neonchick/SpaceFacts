package kuznetsov.hse.kmm.database

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import kuznetsov.hse.kmm.SpacePicturesDatabase

actual class DatabaseDriverFactory(private val context: Context) {

    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(SpacePicturesDatabase.Schema, context, "spacePicturesDatabase.db")
    }

}