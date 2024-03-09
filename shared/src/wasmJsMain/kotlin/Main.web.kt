import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.resume
import decompose.MovieBuffRootImpl



@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    val lifecycle = LifecycleRegistry()
    lifecycle.resume()
    val root = MovieBuffRootImpl(DefaultComponentContext(lifecycle = lifecycle))
    CanvasBasedWindow(title = "movieBuff", canvasElementId = "movieBuff"){
        App(root)
    }
}
