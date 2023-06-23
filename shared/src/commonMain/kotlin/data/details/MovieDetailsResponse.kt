package data.details

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsResponse(
    @SerialName("adult")
    val adult: Boolean = false,
    @SerialName("backdrop_path")
    val backdrop_path: String = "",
    @SerialName("budget")
    val budget: Long,
    @SerialName("genres")
    val genres: List<Genre>,
    @SerialName("homepage")
    val homepage: String = "",
    @SerialName("id")
    val id: Int,
    @SerialName("imdb_id")
    val imdb_id: String = "",
    @SerialName("original_language")
    val original_language: String = "",
    @SerialName("original_title")
    val original_title: String = "",
    @SerialName("overview")
    val overview: String = "",
    @SerialName("popularity")
    val popularity: Double,
    @SerialName("poster_path")
    val poster_path: String = "",
    @SerialName("production_companies")
    val production_companies: List<ProductionCompany>,
    @SerialName("production_countries")
    val production_countries: List<ProductionCountry>,
    @SerialName("release_date")
    val release_date: String = "",
    @SerialName("revenue")
    val revenue: Long,
    @SerialName("runtime")
    val runtime: Int,
    @SerialName("spoken_languages")
    val spoken_languages: List<SpokenLanguage>,
    @SerialName("status")
    val status: String = "",
    @SerialName("tagline")
    val tagline: String = "",
    @SerialName("title")
    val title: String = "",
    @SerialName("video")
    val video: Boolean,
    @SerialName("vote_average")
    val vote_average: Double,
    @SerialName("vote_count")
    val vote_count: Int,
    @SerialName("keywords")
    val keywords: KeywordsResponse,
    @SerialName("reviews")
    val reviews: MovieReviewsResponse,
    @SerialName("credits")
    val credits: MovieCastResponse,
    @SerialName("images")
    val images: MovieImages,
    @SerialName("videos")
    val videos: MovieVideos
)
