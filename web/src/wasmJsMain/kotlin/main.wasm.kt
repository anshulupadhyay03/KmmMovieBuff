import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.resume

fun main() {
    val lifecycle = LifecycleRegistry()
    lifecycle.resume()
    MainWebController(lifecycle)
}