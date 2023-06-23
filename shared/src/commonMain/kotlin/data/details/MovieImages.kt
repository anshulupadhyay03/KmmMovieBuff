package data.details

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieImages(
    @SerialName("backdrops")
    val backdrops: List<Backdrop> = listOf(),
    @SerialName("id")
    val id: Int = 0,
    @SerialName("posters")
    val posters: List<Poster> = listOf()
) {
    @Serializable
    data class Backdrop(
        @SerialName("aspect_ratio")
        val aspect_ratio: Double = 0.0,
        @SerialName("file_path")
        val file_path: String = "",
        @SerialName("height")
        val height: Int = 0,
        @SerialName("iso_639_1")
        val iso_639_1: String? = "",
        @SerialName("vote_average")
        val vote_average: Double = 0.0,
        @SerialName("vote_count")
        val vote_count: Int = 0,
        @SerialName("width")
        val width: Int = 0
    )

    @Serializable
    data class Poster(
        @SerialName("aspect_ratio")
        val aspect_ratio: Double = 0.0,
        @SerialName("file_path")
        val file_path: String = "",
        @SerialName("height")
        val height: Int = 0,
        @SerialName("iso_639_1")
        val iso_639_1: String? = "",
        @SerialName("vote_average")
        val vote_average: Double = 0.0,
        @SerialName("vote_count")
        val vote_count: Int = 0,
        @SerialName("width")
        val width: Int = 0
    )
}
