package kuznetsov.hse.kmm.android

import android.app.Application
import kuznetsov.hse.kmm.koin.KoinInitializer
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

class SpacePicturesApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val koinInitializer = KoinInitializer()
        koinInitializer.initKoin {
            //workaround for kotlin 1.6.0
            //see https://github.com/InsertKoinIO/koin/issues/1188
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@SpacePicturesApplication)
            modules( /*appModule, */ koinInitializer.commonModule)
        }
    }
}