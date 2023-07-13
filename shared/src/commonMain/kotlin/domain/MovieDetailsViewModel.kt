package domain

import data.details.MovieDetailsRepo
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import util.framework.MultiplatformViewModel

class MovieDetailsViewModel(
    private val movieId: Int,
    private val repo: MovieDetailsRepo
) : MultiplatformViewModel() {

    init {
        fetchMovieDetails()
    }

    private val _movieDetailsState =
        MutableStateFlow<MovieDetailsUiState>(MovieDetailsUiState.Loading)
    val movieDetailsState = _movieDetailsState.asStateFlow()

    private fun fetchMovieDetails() {
        viewModelScope.launch {
            repo.getMovieDetails(movieId)
                .catch {
                    _movieDetailsState.value = MovieDetailsUiState.Error
                }
                .collect {
                _movieDetailsState.value = MovieDetailsUiState.Success(it)
            }
        }
    }
}

