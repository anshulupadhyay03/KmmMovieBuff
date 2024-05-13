package util

import kotlinx.coroutines.Dispatchers

internal actual fun getDispatcherProvider():DispatcherProvider = wasmDispatcherProvider()

private class wasmDispatcherProvider : DispatcherProvider {
    override val main = Dispatchers.Main
    override val io = Dispatchers.Default
    override val default = Dispatchers.Default
    override val unconfined = Dispatchers.Unconfined
}