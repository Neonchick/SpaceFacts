package kuznetsov.hse.kmm

import co.touchlab.kermit.StaticConfig
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*

class SpacePictureHttpClient() {

    private val client = HttpClient {
        install(JsonFeature) {
            val jsonFormat = kotlinx.serialization.json.Json { ignoreUnknownKeys = true }
            serializer = KotlinxSerializer(json = jsonFormat)
        }
        install(Logging) {
            val log = co.touchlab.kermit.Logger(config = StaticConfig(), tag = "KTOR_")

            logger = object : Logger {
                override fun log(message: String) {
                    log.v { message }
                }
            }
            level = LogLevel.ALL
        }
    }

    suspend fun getPictures(count: Int): List<SpacePicture> {
        return client.get("https://api.nasa.gov/planetary/apod?api_key=$NASA_API_KEY&count=$count")
//        return listOf(
//            SpacePicture(
//                "1",
//                "sdsdzxczcsdfsdfsadfadfasdf",
//                "https://data.1freewallpapers.com/detail/crystal-mountain-lake.jpg",
//                "2020-01-06"
//            ),
//            SpacePicture(
//                "2",
//                "sdsdzxczcsdfsdfsadfadfasdf",
//                "https://data.1freewallpapers.com/detail/crystal-mountain-lake.jpg",
//                "2020-01-06"
//            ),
//            SpacePicture(
//                "3",
//                "sdsdzxczcsdfsdfsadfadfasdf",
//                "https://data.1freewallpapers.com/detail/crystal-mountain-lake.jpg",
//                "2020-01-06"
//            )
//        )
    }

    companion object {

        const val NASA_API_KEY = "sUMvShjcOjpyPlCwwgviIxrF9rDTi599anUktRbj"

    }

}