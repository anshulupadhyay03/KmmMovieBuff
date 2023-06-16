package decompose

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

class MovieBuffRootImpl(
    componentContext: ComponentContext,
    private val mainScreen : (ComponentContext) -> MainScreenComponent
    ) : MovieBuffRoot , ComponentContext by componentContext {

    constructor(
        componentContext: ComponentContext
    ):this(componentContext ,
        mainScreen = {childContext ->
            MainScreenComponentImpl(childContext)
        }
    )

    private val navigation = StackNavigation<Configuration>()

    private val stack = childStack(
        source = navigation,
        initialConfiguration = Configuration.Dashboard,
        handleBackButton =  true,
        childFactory = ::createChild
    )

    private fun createChild(
        configuration: Configuration,
        componentContext: ComponentContext
    ): MovieBuffRoot.Child =
        when(configuration){
            Configuration.Dashboard -> MovieBuffRoot.Child.MainScreen(
                mainScreen(componentContext)
            )

            is Configuration.Details -> MovieBuffRoot.Child.DetailScreen("")
        }


    override val childStack: Value<ChildStack<*, MovieBuffRoot.Child>>
        get() = value()

    private fun value() = stack

    private sealed class Configuration : Parcelable {
        @Parcelize
        object Dashboard : Configuration()

        @Parcelize
        data class Details(
            val movieId :String
        ) : Configuration(), Parcelable
    }
}