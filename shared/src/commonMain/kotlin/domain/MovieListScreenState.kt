package domain

sealed class MovieListScreenState {

    object Loading : MovieListScreenState()

    object Success : MovieListScreenState() {
        private val result = mutableListOf<MovieResult>()
        fun addData(data: List<MovieResult>){
            result.addAll(data)
        }

        fun getResults():List<MovieResult> = result
    }
}
