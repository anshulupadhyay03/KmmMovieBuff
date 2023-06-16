package domain

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import data.PopularMoviesDataRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class MovieListViewModel(
    private val repository: PopularMoviesDataRepository
) : InstanceKeeper.Instance {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private var PAGE_NO = 1;
    private val _state = MutableStateFlow<MovieListScreenState>(MovieListScreenState.Loading)
    val state = _state.asStateFlow()

    init {
        loadMore()
    }

    fun loadMore() {
        _state.value = MovieListScreenState.Loading
        scope.launch {
            repository.fetchPopularMovies(PAGE_NO++)
                .catch {

                }
                .collect {
                   val newState  = MovieListScreenState.Success
                    newState.addData(it)
                    _state.value = newState
                }
        }
    }

    override fun onDestroy() {

    }
}