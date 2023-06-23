package data

import data.details.MovieDetailsResponse
import di.Network
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MovieApiService {
    suspend fun getPopularMovies(pageNo: Int): Flow<PUNTMoviesResponse> = flow {
        val result: PUNTMoviesResponse = doGet {
            apiUrl("3/movie/popular")
            parameter("page", pageNo)
        }
        emit(result)
    }

    suspend fun getMovieDetails(movieId: Int): Flow<MovieDetailsResponse> = flow {
        val result: MovieDetailsResponse = doGet {
            apiUrl("3/movie/$movieId")
            parameter("append_to_response", "keywords,reviews,credits,images,videos")
        }

        emit(result)
    }
}