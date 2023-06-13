package decompose

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value

interface MovieBuffRoot {

    val childStack : Value<ChildStack<*, Child>>

    sealed class Child{

        data class MainScreen(val s:String) : Child()

        data class DetailScreen(val s:String) : Child()
    }
}