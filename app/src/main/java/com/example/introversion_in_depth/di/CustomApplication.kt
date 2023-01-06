package com.example.introversion_in_depth.di

import android.app.Application

class CustomApplication: Application() {
    val appContainer = AppContainer(this)
}