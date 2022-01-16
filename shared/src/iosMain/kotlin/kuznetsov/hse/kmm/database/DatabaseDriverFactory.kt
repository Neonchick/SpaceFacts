package kuznetsov.hse.kmm.database

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import kuznetsov.hse.kmm.SpacePicturesDatabase

actual class DatabaseDriverFactory {

    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(SpacePicturesDatabase.Schema, "spacePicturesDatabase.db")
    }

}