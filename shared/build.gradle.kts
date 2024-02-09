import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsCompose)
    kotlin("native.cocoapods")
    kotlin("plugin.serialization") version "1.9.22"
    id("kotlin-parcelize")
    //id("com.codingfeline.buildkonfig") version "+"
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }

   /* @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
       browser()
    }*/

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
            isStatic=true
            export(libs.arkivanov.decompose)
            transitiveExport = true
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.ui)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)
                implementation ("io.github.oleksandrbalan:textflow:1.1.0")


                implementation(libs.kotlinx.coroutines.core)

                //Image downloading
                implementation(libs.coil.coil)
                implementation(libs.coil.compose)
                implementation(libs.coil.network.ktor)

                // Ktor
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.client.serialization.json)
                implementation(libs.ktor.client.logging)

                //di,navigation,lifecycle
                api(libs.arkivanov.decompose)
                implementation(libs.arkivanov.jetbrains.uiext)

                //kotlin date&time
                implementation(libs.kotlinx.datetime)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
         androidMain.dependencies{
             implementation(libs.kotlinx.coroutines.android)

             // Android
             implementation(libs.androidx.activity.compose)
             implementation(libs.androidx.appcompat)
             implementation(libs.androidx.core.ktx)

             // Ktor
             implementation(libs.ktor.client.okhttp)
        }
        val androidUnitTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation(libs.ktor.client.darwin)
            }

        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }

       /* val wasmJsMain by getting{
            dependencies {
                implementation(libs.ktor.client.web)
            }
        }*/
    }
}

android {
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    namespace = "com.retroent.moviebuff"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        //applicationId = "com.retroent.moviebuff.android"
        minSdk = (findProperty("android.minSdk") as String).toInt()
        targetSdk = (findProperty("android.targetSdk") as String).toInt()
       // versionCode = 1
      //  versionName = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
compose.experimental {
    web.application {}
}
