package decompose

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import data.MovieApiService
import data.PopularMoviesDataRepository
import domain.MovieListViewModel
import util.getDispatcherProvider

class MainScreenComponentImpl(
    componentContext: ComponentContext,
    private val onMovieSelected: (movieId: Int) -> Unit
) : MainScreenComponent, ComponentContext by componentContext {

    private val repository = PopularMoviesDataRepository(MovieApiService(), getDispatcherProvider())

    override val viewModel: MovieListViewModel
        get() = instanceKeeper.getOrCreate {
            MovieListViewModel(repository) { id ->
                onMovieSelected(id)
            }
        }
}