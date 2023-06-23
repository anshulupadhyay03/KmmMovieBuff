package data.details


import data.MovieApiService
import domain.MovieDetailsModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import util.DispatcherProvider

class MovieDetailsRepo constructor(
    private val service: MovieApiService,
    private val dispatcherProvider: DispatcherProvider
) {

    suspend fun getMovieDetails(id: Int): Flow<MovieDetailsModel> {
        return service.getMovieDetails(id).map {
            mapToDomain(it)
        }.flowOn(dispatcherProvider.io)
    }
}