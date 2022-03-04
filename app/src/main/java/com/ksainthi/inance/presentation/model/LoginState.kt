package com.ksainthi.inance.presentation.model



enum class LoginError {
    INCORRECT_CREDENTIALS,
    INCORRECT_EMAIL_ADDRESS
}

enum class LoginStep {
    LOGIN_FORM,
    RESET_PASSWORD_FORM,
    LOGIN_SUCCEEDED
}

data class LoginState(
    val isLoadingEnabled: Boolean = false,
    val alert: Alert? = null,
    val step: LoginStep = LoginStep.LOGIN_FORM
)


sealed class LoginPartialState {

    object Loading : LoginPartialState() {
        const val isLoadingEnabled: Boolean = true
    }

    data class ResetPassword(val info: String) : LoginPartialState() {
        val alert: Alert = Alert.Info(info)
    }

    object LoginSuccessful : LoginPartialState() {
        val step: LoginStep = LoginStep.LOGIN_SUCCEEDED
    }

    data class PasswordResetSuccessful(val info: String) : LoginPartialState() {
        val step: LoginStep = LoginStep.LOGIN_FORM
        val alert: Alert = Alert.Info(info)
    }

    data class PasswordResetFailed(val reason: String) : LoginPartialState() {
        val step: LoginStep = LoginStep.RESET_PASSWORD_FORM
        val alert: Alert = Alert.Error(reason)
    }

    data class LoginFailed(val reason: String) : LoginPartialState() {
        val alert: Alert = Alert.Error(reason)
    }
}

sealed class LoginIntent {
    data class RequestResetPassword(val mail: String) : LoginIntent()
    data class ResetPassword(val code: String) : LoginIntent()
    data class Login(val username: String, val password: String) : LoginIntent()
}

sealed class LoginAction {
    data class Login(val mail: String, val password: String) : LoginAction()
    data class RequestPasswordReset(val mail: String) : LoginAction()
    data class ResetPassword(val code: String) : LoginAction()
}
