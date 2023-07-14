package data

import domain.MovieResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import util.DispatcherProvider


class PopularMoviesDataRepository constructor(
    private val service: MovieApiService,
    private val dispatcherProvider: DispatcherProvider
) {

    suspend fun fetchPopularMovies(pageNo: Int): Flow<List<MovieResult>> {
        return service.getPopularMovies(pageNo).map {
            mapToDomain(it,pageNo == it.totalPages)
        }.flowOn(dispatcherProvider.io)
    }
}

private fun mapToDomain(data: PUNTMoviesResponse, hasMorePages: Boolean): List<MovieResult> {
    return data.results.map {
        MovieResult(
            it.posterPath ?: "",
            it.id,
            it.title,
            it.overview,
            it.releaseDate,
            it.voteAverage,
            hasMorePages
        )
    }
}


/*

 fun fetchUpcomingMovies(pageNo: Int) = flow {
     service.getUpcomingMovies(pageNo).suspendOnSuccess {
         val result = mapToDomain(this.data)
         emit(result)
     }.onError {
         println("Movies error :${this.errorBody}")
     }.onException {
         println("Movies error :${this}")
     }
 }.flowOn(Dispatchers.IO)

 fun fetchNowPlayingMovies(pageNo: Int) = flow {
     service.getNowPlayingMovies(pageNo).suspendOnSuccess {
         val result = mapToDomain(this.data)
         emit(result)
     }.onError {
         println("Movies error :${this.errorBody}")
     }.onException {
         println("Movies error :${this}")
     }
 }.flowOn(Dispatchers.IO)

 fun fetchTopRatedMovies(pageNo: Int) = flow {
     service.getTopRatedMovies(pageNo).suspendOnSuccess {
         val result = mapToDomain(this.data)
         emit(result)
     }.onError {
         println("Movies error :${this.errorBody}")
     }.onException {
         println("Movies error :${this}")
     }
 }.flowOn(Dispatchers.IO)
}*/