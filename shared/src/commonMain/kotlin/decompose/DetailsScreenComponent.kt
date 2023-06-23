package decompose

import domain.MovieDetailsViewModel

interface DetailsScreenComponent {

    val viewModel : MovieDetailsViewModel

    fun onBackPressed()
}