package domain

import data.PopularMoviesDataRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import util.framework.MultiplatformViewModel

class MovieListViewModel(
    private val repository: PopularMoviesDataRepository,
    private val onMovieSelected: (movieId: Int) -> Unit
) : MultiplatformViewModel() {

    private var PAGENO = 1;
    private val _state = MutableStateFlow<MovieListScreenState>(MovieListScreenState.Loading)
    val state = _state.asStateFlow()

    init {
        loadMore()
    }

    fun onItemClicked(movieId: Int) {
        onMovieSelected(movieId)
    }

    fun loadMore() {
        _state.value = MovieListScreenState.Loading
        viewModelScope.launch {
            repository.fetchPopularMovies(PAGENO++)
                .catch {

                }
                .collect {
                    val newState = MovieListScreenState.Success
                    newState.addData(it)
                    _state.value = newState
                }
        }
    }

}