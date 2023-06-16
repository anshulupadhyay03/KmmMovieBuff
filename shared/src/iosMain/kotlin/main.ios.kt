import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import decompose.MovieBuffRoot
import decompose.MovieBuffRootImpl

fun MainViewController(lifecycle: LifecycleRegistry) = ComposeUIViewController {
    CompositionLocalProvider(){
        val root = MovieBuffRootImpl(DefaultComponentContext(lifecycle = lifecycle))
        App(root)
    }
}