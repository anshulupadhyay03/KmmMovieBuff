import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import decompose.MovieBuffRoot
import decompose.MovieBuffRootImpl
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    val root = MovieBuffRootImpl(DefaultComponentContext(lifecycle = LifecycleRegistry()))
    return ComposeUIViewController {
        App(root)
    }
}