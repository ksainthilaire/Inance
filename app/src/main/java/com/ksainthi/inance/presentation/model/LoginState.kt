package com.ksainthi.inance.presentation.model


import com.ksainthi.inance.components.AlertDesc


enum class LoginStep {
    LOGIN_FORM,
    LOGIN_SUCCEEDED,
    RESET_PASSWORD_FORM
}

data class LoginState(
    val isLoadingEnabled: Boolean = false,

    val isMailError: Boolean = false,
    val isPasswordError: Boolean = false,

    val alert: AlertDesc? = null,

    val step: LoginStep = LoginStep.LOGIN_FORM
)

sealed class LoginPartialState {

    object Loading : LoginPartialState() {
        const val isLoadingEnabled: Boolean = true
    }

    data class LoginAlert(val alert: AlertDesc) : LoginPartialState()

    object LoginSuccessful : LoginPartialState() {
        const val isLoadingEnabled: Boolean = false
        val step: LoginStep = LoginStep.LOGIN_SUCCEEDED
    }

    object PasswordResetRequestSuccessful: LoginPartialState() {
        val step: LoginStep = LoginStep.LOGIN_FORM
    }

    object PasswordResetRequestFailed: LoginPartialState() {
        val step: LoginStep = LoginStep.RESET_PASSWORD_FORM
    }

}

sealed class LoginIntent {
    data class RequestResetPassword(val mail: String) : LoginIntent()
    data class Login(val mail: String, val password: String) : LoginIntent()
}

sealed class LoginAction {
    data class Login(val mail: String, val password: String) : LoginAction()
    data class RequestPasswordReset(val mail: String) : LoginAction()
}
