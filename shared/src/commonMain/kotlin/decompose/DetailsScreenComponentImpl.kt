package decompose

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import data.MovieApiService
import data.details.MovieDetailsRepo
import domain.MovieDetailsViewModel
import util.getDispatcherProvider

class DetailsScreenComponentImpl(
    componentContext: ComponentContext,
    private val movieId: Int,
    private val onBackPressed: () -> Unit
) : DetailsScreenComponent, ComponentContext by componentContext {

    private val repo = MovieDetailsRepo(MovieApiService(), getDispatcherProvider())

    override val viewModel: MovieDetailsViewModel
        get() = instanceKeeper.getOrCreate { MovieDetailsViewModel(movieId, repo) }

    override fun onBackPressed() {
        onBackPressed.invoke()
    }
}