package com.ksainthi.inance.presentation.model

import com.ksainthi.inance.components.AlertDesc
import com.ksainthi.inance.components.AlertType


enum class ProfileError {
    INCORRECT_CREDENTIALS,
    INCORRECT_EMAIL_ADDRESS
}

enum class ProfileStep {
    GENERAL,
    EDIT_PASSWORD_FORM
}

data class ProfileState(
    val isLoadingEnabled: Boolean = false,
    val alert: AlertDesc?,
    val step: ProfileStep = ProfileStep.GENERAL
)

sealed class ProfilePartialState {

    object Loading : ProfilePartialState() {
        const val isLoadingEnabled: Boolean = true
    }

   data class ProfileUpdateSuccessful(val info: String) : ProfilePartialState() {
        val alert: AlertDesc = AlertDesc(AlertType.INFO, info)
        val step: ProfileStep = ProfileStep.GENERAL
    }

    data class PasswordUpdateSuccessful(val info: String) : ProfilePartialState() {
        val isLoadingEnabled: Boolean = false
        val step: ProfileStep = ProfileStep.EDIT_PASSWORD_FORM
        val alert: AlertDesc = AlertDesc(AlertType.SUCCESS, info)
    }

    data class PasswordUpdateFailed(val reason: String) : ProfilePartialState() {
        val step: ProfileStep = ProfileStep.EDIT_PASSWORD_FORM
        val alert: AlertDesc = AlertDesc(AlertType.ERROR, reason)
    }

    data class ProfileUpdateFailed(val reason: String) : ProfilePartialState() {
        val alert: AlertDesc = AlertDesc(AlertType.ERROR, reason)
    }
}

sealed class ProfileIntent {
    data class UpdateProfile(
        val username: String,
        val mail: String,
        val fullName: String,
        val country: Int
    ) : ProfileIntent()

    data class UpdatePassword(
        val password: String,
        val newPassword: String
    ) : ProfileIntent()

    data class EnableNotifications(
        val enable: Boolean = false
    ) : ProfileIntent()

    object DeleteAccount : ProfileIntent()
}

sealed class ProfileAction {

    object DeleteAccount : ProfileAction()
    class EnableNotifications(enable: Boolean) : ProfileAction()

    data class UpdateProfile(
        val username: String,
        val mail: String,
        val fullName: String,
        val country: Int
    ) : ProfileAction()

    data class UpdatePassword(
        val password: String,
        val newPassword: String
    ) : ProfileAction()
}
