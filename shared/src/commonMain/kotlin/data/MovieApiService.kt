package data

import data.details.MovieDetailsResponse
import data.networking.MOVIE_DETAILS
import data.networking.POPULAR_MOVIES
import io.ktor.client.request.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MovieApiService {
    suspend fun getPopularMovies(pageNo: Int): Flow<PUNTMoviesResponse> = flow {
        val result: PUNTMoviesResponse = doGet {
            apiUrl("$POPULAR_MOVIES/popular")
            parameter("page", pageNo)
        }
        emit(result)
    }

    suspend fun getMovieDetails(movieId: Int): Flow<MovieDetailsResponse> = flow {
        val result: MovieDetailsResponse = doGet {
            apiUrl("$MOVIE_DETAILS/$movieId")
            parameter("append_to_response", "keywords,reviews,credits,images,videos")
        }
        emit(result)
    }
}