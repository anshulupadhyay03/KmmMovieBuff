package style

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.platform.Font
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.buffer
import org.jetbrains.compose.resources.ExperimentalResourceApi
import platform.Foundation.NSBundle


@Composable
actual fun getTajawalMediumFontFamily(): FontFamily {
    return FontFamily(
       Font(
           identity = "tajawal-medium",
           data = loadBytes("Tajawal-Medium.ttf")
       )
    )
}

private fun loadBytes(path: String): ByteArray {
    val fullPath = NSBundle.mainBundle.resourcePath + "/compose-resources/font/" + path
    println("Ansh : fullpath : $fullPath")
    return FileSystem.SYSTEM.source(fullPath.toPath()).buffer().readByteArray()
}