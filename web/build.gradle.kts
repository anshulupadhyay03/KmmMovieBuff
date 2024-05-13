import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsCompose)
}

kotlin {

    wasmJs {
        moduleName = "MovieBuff"
        browser {
            commonWebpackConfig {
                outputFileName = "MovieBuff.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    open = mapOf(
                        "app" to mapOf(
                            "name" to "google chrome dev"
                        )
                    )
                }
            }
        }
        binaries.executable()
    }

    sourceSets {
        val wasmJsMain by getting {
            dependencies {
                implementation(project(":shared"))

            }
        }
    }
}




compose.experimental {
    web.application {}
}

