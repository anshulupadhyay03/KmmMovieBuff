package domain

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import data.PopularMoviesDataRepository

class MovieListViewModel(
    private val repository: PopularMoviesDataRepository
) : InstanceKeeper.Instance {


    override fun onDestroy() {

    }
}