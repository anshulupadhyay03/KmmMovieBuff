package data

import di.Network
import io.ktor.client.call.*
import io.ktor.client.request.*

suspend inline fun <reified T> doGet(noinline block: HttpRequestBuilder.() -> Unit) : T {
   return Network.httpClient.get{
        block()
       //parameter("with_origin_country", "IN")
       parameter("api_key", "f09de2eadd49d17ae44e44bb07190295")
    }.body()
}
