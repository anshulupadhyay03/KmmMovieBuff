package di

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json


object Network {

    private val jsonConfiguration
        get() = Json {
            prettyPrint = true
            ignoreUnknownKeys = true
            useAlternativeNames = false
        }

    val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(jsonConfiguration)
        }
        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.ALL
        }
    }
}