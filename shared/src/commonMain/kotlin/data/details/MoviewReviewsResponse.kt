package data.details

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Result(
    @SerialName("author")
    val author: String = "",
    @SerialName("author_details")
    val author_details: AuthorDetails,
    @SerialName("content")
    val content: String = "",
    @SerialName("created_at")
    val created_at: String = "",
    @SerialName("id")
    val id: String = "",
    @SerialName("updated_at")
    val updated_at: String = "",
    @SerialName("url")
    val url: String = ""
)


@Serializable
data class MovieReviewsResponse(
    @SerialName("id")
    val id: Int = 1,
    @SerialName("page")
    val page: Int,
    @SerialName("results")
    val results: List<Result>,
    @SerialName("total_pages")
    val total_pages: Int,
    @SerialName("total_results")
    val total_results: Int
)


@Serializable
data class AuthorDetails(
    val avatar_path: String? = "",
    val name: String = "",
    val rating: Double? = 0.0,
    val username: String = ""
)