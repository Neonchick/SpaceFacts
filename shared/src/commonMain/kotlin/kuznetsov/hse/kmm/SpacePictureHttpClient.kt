package kuznetsov.hse.kmm

import co.touchlab.kermit.StaticConfig
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*

class SpacePictureHttpClient() {

    private val log = co.touchlab.kermit.Logger(config = StaticConfig(), tag = "KTOR_")

    private val client = HttpClient {
        install(JsonFeature) {
            val jsonFormat = kotlinx.serialization.json.Json { ignoreUnknownKeys = true }
            serializer = KotlinxSerializer(json = jsonFormat)
        }
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    log.v { message }
                }
            }
            level = LogLevel.ALL
        }
    }

    suspend fun test() {
        val jsonResponse: List<SpacePicture> =
            client.get("https://api.nasa.gov/planetary/apod?api_key=$NASA_API_KEY&count=5")
    }

    suspend fun getPictures(count: Int): List<SpacePicture> {
        return client.get("https://api.nasa.gov/planetary/apod?api_key=$NASA_API_KEY&count=$count")
    }

    companion object {

        const val NASA_API_KEY = "LzrEqGiUEJ1eiKjg3Yv42dKFp57dMqbybvgGfnMw"

    }

}