package data

import io.ktor.client.request.*
import io.ktor.http.*

/**
 * Use this method for configuring the request url
 */
fun HttpRequestBuilder.apiUrl(path: String) {
    url {
        takeFrom("https://api.themoviedb.org")
        path(path)
    }
}