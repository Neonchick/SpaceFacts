package kuznetsov.hse.kmm.koin

import kuznetsov.hse.kmm.database.DatabaseDriverFactory
import org.koin.core.module.Module
import org.koin.dsl.module

actual class KoinPlatformModuleInitializer actual constructor() {

    actual val platformModule: Module = module {
        single { DatabaseDriverFactory() }
    }

}