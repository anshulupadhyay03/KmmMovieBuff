package decompose

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import data.MovieApiService
import data.PopularMoviesDataRepository
import domain.MovieListViewModel
import util.getDispatcherProvider

class MainScreenComponentImpl(
    componentContext: ComponentContext,
) : MainScreenComponent , ComponentContext by componentContext{

    private val repository =  PopularMoviesDataRepository(MovieApiService(), getDispatcherProvider())

    override val viewModel: MovieListViewModel
        get() = instanceKeeper.getOrCreate { MovieListViewModel(repository) }

    override fun onMovieSelected(id: String) {
        TODO("Not yet implemented")
    }
}