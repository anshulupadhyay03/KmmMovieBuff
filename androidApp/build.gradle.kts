plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    kotlin("android")
}

android {
    namespace = "com.retroent.moviebuff.android"
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    defaultConfig {
        applicationId = "com.retroent.moviebuff.android"
        minSdk = (findProperty("android.minSdk") as String).toInt()
        targetSdk = (findProperty("android.targetSdk") as String).toInt()
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    //coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.4")
    implementation(project(":shared"))
    //implementation("androidx.activity:activity-compose:1.5.0")
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.androidx.activity.compose)
    debugImplementation(libs.compose.ui.tooling)
}