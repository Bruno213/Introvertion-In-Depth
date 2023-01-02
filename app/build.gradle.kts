plugins {
    id(Plugins.application)
    id(Plugins.jetbrains)
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
    implementation(Libs.navigation)
//    implementation(Libs.splashScreen)

    implementation(Libs.lifecycle)


    implementation(Libs.coroutines)

    implementation(Libs.workManager)

    testImplementation(TestLibs.jUnit)
    androidTestImplementation(TestLibs.testJUnit)
    androidTestImplementation(TestLibs.espresso)
}