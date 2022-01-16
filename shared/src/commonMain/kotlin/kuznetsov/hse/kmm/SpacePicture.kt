package kuznetsov.hse.kmm

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpacePicture(@SerialName("title") val title: String)