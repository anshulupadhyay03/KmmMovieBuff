package domain


sealed interface MovieDetailsUiState {

    object Loading : MovieDetailsUiState

    object Error : MovieDetailsUiState

    data class Success(val movieDetails: MovieDetailsModel) : MovieDetailsUiState
}