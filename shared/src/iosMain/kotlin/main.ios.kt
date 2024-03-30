import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import decompose.MovieBuffRoot
import decompose.MovieBuffRootImpl

fun MainViewController() = ComposeUIViewController {
    CompositionLocalProvider(){
        val root = MovieBuffRootImpl(DefaultComponentContext(lifecycle = LifecycleRegistry()))
        App(root)
    }
}