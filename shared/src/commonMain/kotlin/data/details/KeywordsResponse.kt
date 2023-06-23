package data.details

import kotlinx.serialization.Serializable

@Serializable
data class KeywordsResponse(
    val id: Int = 0,
    val keywords: List<Keyword> = listOf()
) {
    @Serializable
    data class Keyword(
        val id: Int = 0,
        val name: String = ""
    )
}