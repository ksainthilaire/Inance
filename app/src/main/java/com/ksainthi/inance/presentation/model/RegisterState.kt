package com.ksainthi.inance.presentation.model

import com.ksainthi.inance.components.AlertDesc


enum class RegisterStep {
    REGISTER_STEP_ONE,
    REGISTER_STEP_TWO,
    REGISTER_STEP_THIRD
}

data class RegisterForm(
    val fullName: String,
    val mail: String,
    val password: String,
    var number: String? = null
)

data class Field(
    val value: String = "",
    var isError: Boolean = false
)

data class RegisterState(
    val alert: AlertDesc? = null,
    val step: RegisterStep = RegisterStep.REGISTER_STEP_ONE,

    val mail: Field = Field(),
    val password: Field = Field(),
    val rePassword: Field = Field(),
    val fullName: Field = Field(),
    val phoneNumber: Field = Field(),

    val isLoadingEnabled: Boolean = false,
    val isVerifiedPhoneNumber: Boolean = false
)

sealed class RegisterPartialState {

    object Loading : RegisterPartialState() {
        const val isLoadingEnabled: Boolean = true
    }


    data class RegisterStepOneSuccessful(
        val fullName: Field,
        val mail: Field,
        val password: Field,
        val rePassword: Field
    ) : RegisterPartialState() {
        val isLoadingEnabled: Boolean = false
        val step: RegisterStep = RegisterStep.REGISTER_STEP_TWO
    }

    data class RegisterFieldUpdates(
        val fullName: Field,
        val mail: Field,
        val password: Field,
        val rePassword: Field
    ) : RegisterPartialState()

    data class RegisterAlert(
        val alert: AlertDesc
    ) : RegisterPartialState() {
        val isLoadingEnabled: Boolean = false
    }

    object RegisterStepTwoSuccessful : RegisterPartialState() {
        val step: RegisterStep = RegisterStep.REGISTER_STEP_THIRD
        const val isVerifiedPhoneNumber: Boolean = true
    }


}

sealed class RegisterIntent {
    data class RequestConfirmPhone(val number: String) : RegisterIntent()
    data class ConfirmPhoneWithCode(val code: String) : RegisterIntent()
    data class Register(
        val fullName: String,
        val mail: String,
        val password: String,
        val rePassword: String
    ) : RegisterIntent()
}

sealed class RegisterAction {
    data class Register(
        val fullName: String,
        val mail: String,
        val password: String,
        val rePassword: String
    ) : RegisterAction()

    data class RequestConfirmPhone(val number: String) : RegisterAction()
    data class ConfirmPhoneWithCode(val code: String) : RegisterAction()
}
