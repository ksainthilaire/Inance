package com.ksainthi.inance.presentation.model


import com.ksainthi.inance.components.AlertDesc


enum class HomeStep {
    SUCCESSFUL_IDENTIFICATION
}

data class HomeState(
    val isLoadingEnabled: Boolean = false,
    val alert: AlertDesc? = null,
    val step: HomeStep? = null
)

sealed class HomePartialState {

    object Loading : HomePartialState() {
        const val isLoadingEnabled: Boolean = true
    }

    data class HomeAlert(val alert: AlertDesc) : HomePartialState()

    data class Successful(val reason: String) : HomePartialState() {
        val step = HomeStep.SUCCESSFUL_IDENTIFICATION
    }
}

sealed class HomeIntent {
    object SignInWithFacebook : HomeIntent()
    object SignInWithGoogle : HomeIntent()
}

sealed class HomeAction {
    object SignInWithFacebook : HomeAction()
    object SignInWithGoogle : HomeAction()
}
