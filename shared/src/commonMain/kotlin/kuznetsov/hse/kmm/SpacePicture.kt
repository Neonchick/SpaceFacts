package kuznetsov.hse.kmm

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpacePicture(

    @SerialName("title")
    val title: String,

    @SerialName("explanation")
    val explanation: String,

    @SerialName("url")
    val url: String,

    @SerialName("date")
    val date: String,

)