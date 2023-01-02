object App {
    const val namespace = "com.example.introversion_in_depth"
    const val compileSdk = 32

    const val applicationId = "com.example.introversion_in_depth"
    const val minSdk = 21
    const val targetSdk = 32
    const val versionCode = 1
    const val versionName = "1.0"
    const val jvmTarget = "1.8"
}

object Plugins {
    const val application = "com.android.application"
    const val jetbrains = "org.jetbrains.kotlin.android"
    const val kotlinPlugin = "kotlin-android"
}

object Versions {
    const val androidXCore = "1.7.0"
//    const val splashScreen = "1.0.0-alpha01"
    const val appCompat = "1.4.1"
    const val material = "1.5.0"
    const val constraintLayout = "2.1.3"
    const val navigation = "2.5.3"

    const val awareComponents = "2.4.0"

    const val coroutines = "1.6.4"

    const val workManager = "2.7.1"

    const val jUnit = "4.13.2"
    const val testJUnit = "1.1.3"
    const val espresso = "3.4.0"

    const val toolsGradle = "7.0.0"
    const val jetbrainsGradle = "1.5.10"
}

object Libs {
    //Default
    const val androidXCore = "androidx.core:core-ktx:${Versions.androidXCore}"
//    const val splashScreen =  "androidx.core:core-splashscreen:${Versions.splashScreen}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"

    //Navigation
    const val navigation = "androidx.navigation:navigation-fragment:${Versions.navigation}"

    //Lifecycle Aware components
    const val lifecycle = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.awareComponents}"

    //Coroutines
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"

    // WorkManager
    const val workManager = "androidx.work:work-runtime-ktx:${Versions.workManager}"
}

object TestLibs {
    const val jUnit = "junit:junit:${Versions.jUnit}"
    const val testJUnit = "androidx.test.ext:junit:${Versions.testJUnit}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
}

object BuildScript {
    const val toolsGradlePlugin = "com.android.tools.build:gradle:${Versions.toolsGradle}"
    const val jetbrainsGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.jetbrainsGradle}"

    const val androidApplicationId = "com.android.application"
    const val libraryPlugin = "com.android.library"
    const val jetbrainsKotlinPlugin = "org.jetbrains.kotlin.android"
}