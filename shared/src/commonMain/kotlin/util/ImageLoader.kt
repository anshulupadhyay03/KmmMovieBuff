package util

import androidx.compose.runtime.Composable
import com.seiko.imageloader.ImageLoader

@Composable
expect fun generateImageLoader(): ImageLoader

/*
internal expect fun ComponentRegistryBuilder.setupDefaultComponents()
internal expect fun getImageCacheDirectoryPath(): Path*/
