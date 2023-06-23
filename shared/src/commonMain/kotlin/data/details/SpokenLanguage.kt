package data.details

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpokenLanguage(
    @SerialName("iso_639_1")
    val iso_639_1: String = "",
    @SerialName("name")
    val name: String = ""
)