plugins {
    id(Plugins.application)
    kotlin(Plugins.jetbrains)
    kotlin(Plugins.kapt)
}
apply {
    plugin(Plugins.kotlinPlugin)
}

android {
    namespace = App.namespace
    compileSdk = App.compileSdk
    buildFeatures.viewBinding = true

    defaultConfig {
        applicationId = App.applicationId
        minSdk = App.minSdk
        targetSdk = App.targetSdk
        versionCode = App.versionCode
        versionName = App.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = App.jvmTarget
    }
}

dependencies {
    implementation(Libs.androidXCore)
    implementation(Libs.appCompat)
    implementation(Libs.material)
    implementation(Libs.constraintLayout)
    // Navigation
    implementation(Libs.navigation)
    implementation(Libs.navigationUI)
//    implementation(Libs.splashScreen)
    // Lifecycle
    implementation(Libs.lifecycle)
    //Coroutines
    implementation(Libs.coroutines)
    //WorkManager
    implementation(Libs.workManager)
    //Room
    implementation(Libs.roomRunTime)
    implementation(Libs.roomKtx)
    kapt(Libs.roomCompiler)

    testImplementation(TestLibs.jUnit)
    androidTestImplementation(TestLibs.testJUnit)
    androidTestImplementation(TestLibs.espresso)
}