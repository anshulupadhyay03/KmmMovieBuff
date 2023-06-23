package data.details

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieCastResponse(
    @SerialName("cast")
    val cast: List<Cast> = listOf(),
    @SerialName("id")
    val id: Int = 0
) {
    @Serializable
    data class Cast(
        val adult: Boolean = false,
        @SerialName("cast_id")
        val cast_id: Int = 0,
        @SerialName("character")
        val character: String = "",
        @SerialName("credit_id")
        val credit_id: String = "",
        @SerialName("gender")
        val gender: Int = 0,
        @SerialName("id")
        val id: Int = 0,
        @SerialName("known_for_department")
        val known_for_department: String = "",
        @SerialName("name")
        val name: String = "",
        @SerialName("order")
        val order: Int = 0,
        @SerialName("original_name")
        val original_name: String = "",
        @SerialName("popularity")
        val popularity: Double = 0.0,
        @SerialName("profile_path")
        val profile_path: String? = ""
    )
}
