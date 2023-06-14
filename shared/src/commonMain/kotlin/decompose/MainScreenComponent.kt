package decompose

import domain.MovieListViewModel

interface MainScreenComponent {

    val viewModel : MovieListViewModel

    fun onMovieSelected(id:String)


}