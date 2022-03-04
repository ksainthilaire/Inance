package com.ksainthi.inance.presentation.model


enum class HomeError {

}

enum class HomeStep {
    SUCCESSFUL_IDENTIFICATION
}

data class HomeState(
    val isLoadingEnabled: Boolean = false,
    val alert: Alert? = null,
    val step: HomeStep? = null
)

sealed class HomePartialState {

    object Loading : HomePartialState() {
        const val isLoadingEnabled: Boolean = true
    }

    data class Successful(val reason: String) : HomePartialState() {
        val step = HomeStep.SUCCESSFUL_IDENTIFICATION
        val alert = Alert.Success(reason)
    }

    data class Failed(val reason: String) : HomePartialState() {
        val alert = Alert.Error(reason)
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
