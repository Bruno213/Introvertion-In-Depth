package com.example.introversion_in_depth.base

import androidx.lifecycle.ViewModel

abstract class BaseViewModel: ViewModel() {

    abstract fun collect()

    abstract fun processEvent()
}