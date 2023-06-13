package data

import di.Network
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MovieApiService {
    suspend fun getPopularMovies(pageNo: Int): Flow<PUNTMoviesResponse> = flow {
       val result: PUNTMoviesResponse =   doGet {
            val result = apiUrl("3/movie/popular")
            parameter("api_key", "f09de2eadd49d17ae44e44bb07190295")

            parameter("page", pageNo)
        }
        emit(result)
    }
}