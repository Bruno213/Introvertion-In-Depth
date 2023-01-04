package com.example.introversion_in_depth.ui.fragments.startFragment

import com.example.introversion_in_depth.ui.MVIAction

sealed class StartAction: MVIAction() {
    object PickLanguage: StartAction()
}
