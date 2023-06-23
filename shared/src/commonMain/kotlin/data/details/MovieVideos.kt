package data.details

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieVideos(
    @SerialName("id")
    val id: Int = 0,
    @SerialName("results")
    val results: List<Result> = listOf()
) {
    @Serializable
    data class Result(
        @SerialName("id")
        val id: String = "",
        @SerialName("iso_3166_1")
        val iso3166_1: String = "",
        @SerialName("iso_639_1")
        val iso639_1: String = "",
        @SerialName("key")
        val key: String = "",
        @SerialName("name")
        val name: String = "",
        @SerialName("official")
        val official: Boolean = false,
        @SerialName("published_at")
        val publishedAt: String = "",
        @SerialName("site")
        val site: String = "",
        @SerialName("size")
        val size: Int = 0,
        @SerialName("type")
        val type: String = ""
    )
}
