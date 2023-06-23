package decompose

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.*
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

class MovieBuffRootImpl(
    componentContext: ComponentContext,
    private val mainScreen: (ComponentContext, (movieId: Int) -> Unit) -> MainScreenComponent,
    private val movieDetails: (
        ComponentContext, movieId: Int, onBackPressed: () -> Unit
    ) -> DetailsScreenComponent
) : MovieBuffRoot, ComponentContext by componentContext {

    constructor(
        componentContext: ComponentContext
    ) : this(componentContext,
        mainScreen = { childContext, onMovieSelected ->
            MainScreenComponentImpl(childContext, onMovieSelected)
        },
        movieDetails = { childContext, movieId, onBackPressed ->
            DetailsScreenComponentImpl(childContext, movieId) {
                onBackPressed.invoke()
            }
        }
    )

    private val navigation = StackNavigation<Configuration>()

    private val stack = childStack(
        source = navigation,
        initialConfiguration = Configuration.Dashboard,
        handleBackButton = true,
        childFactory = ::createChild
    )

    private fun createChild(
        configuration: Configuration,
        componentContext: ComponentContext
    ): MovieBuffRoot.Child =
        when (configuration) {
            Configuration.Dashboard -> MovieBuffRoot.Child.MainScreen(
                mainScreen(componentContext, ::onMovieSelected)
            )

            is Configuration.Details -> MovieBuffRoot.Child.DetailScreen(
                movieDetails(componentContext, configuration.movieId, ::onDetailsScreenBackPressed)
            )
        }

    private fun onMovieSelected(movieId: Int) {
        navigation.push(Configuration.Details(movieId))
    }


    private fun onDetailsScreenBackPressed(){
        navigation.pop()
    }


    override val childStack: Value<ChildStack<*, MovieBuffRoot.Child>>
        get() = value()

    private fun value() = stack

    private sealed class Configuration : Parcelable {
        @Parcelize
        object Dashboard : Configuration()

        @Parcelize
        data class Details(
            val movieId: Int
        ) : Configuration(), Parcelable
    }
}