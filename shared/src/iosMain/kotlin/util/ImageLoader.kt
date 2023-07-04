package util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.cache.memory.maxSizePercent
import com.seiko.imageloader.component.setupDefaultComponents
import okio.FileSystem

@Composable
actual fun generateImageLoader(): ImageLoader =
    ImageLoader(
        requestCoroutineContext = rememberCoroutineScope().coroutineContext,
    ) {
       // logger = DebugLogger(LogPriority.DEBUG)
        components {
            setupDefaultComponents(imageScope)
        }
        interceptor {

            memoryCacheConfig {
                maxSizePercent(0.55)
            }

            diskCacheConfig {
                directory(FileSystem.SYSTEM_TEMPORARY_DIRECTORY)
                maxSizeBytes(1024 * 1024 * 100)
            }
        }
    }

/*actual fun ComponentRegistryBuilder.setupDefaultComponents() = this.setupDefaultComponents()
actual fun getImageCacheDirectoryPath(): Path {
    val cacheDir = NSSearchPathForDirectoriesInDomains(
        NSCachesDirectory,
        NSUserDomainMask,
        true
    ).first() as String
    return (cacheDir + "/media").toPath()
}*/
