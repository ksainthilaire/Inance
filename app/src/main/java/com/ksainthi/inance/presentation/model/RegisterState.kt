package com.ksainthi.inance.presentation.model

enum class RegisterStep {
    REGISTER_STEP_ONE,
    REGISTER_STEP_TWO,
    REGISTER_STEP_THIRD
}

data class RegisterForm(
    val fullName: String,
    val password: String,
    val mail: String,
    var number: String? = null
)

data class RegisterState(
    val alert: Alert?,
    val step: RegisterStep = RegisterStep.REGISTER_STEP_ONE,

    val registerForm: RegisterForm? = null,

    val isLoadingEnabled: Boolean = false,
    val isVerifiedPhoneNumber: Boolean = false
)

sealed class RegisterPartialState {

    object Loading : RegisterPartialState() {
        const val isLoadingEnabled: Boolean = true
    }


    data class RegisterStepOneSuccessful(val registerForm: RegisterForm) : RegisterPartialState() {
        val isLoadingEnabled: Boolean = false
        val step: RegisterStep = RegisterStep.REGISTER_STEP_TWO
    }

    object RegisterStepTwoSuccessful : RegisterPartialState() {
        val step: RegisterStep = RegisterStep.REGISTER_STEP_THIRD
        const val isVerifiedPhoneNumber: Boolean = true
    }

    open class RegisterError(open val reason: String) : RegisterPartialState() {
        val isLoadingEnabled: Boolean = false
        val alert: Alert = Alert.Error(reason)
    }

    open class RegisterInfo(open val info: String) : RegisterPartialState() {
        val isLoadingEnabled: Boolean = false
        val alert: Alert = Alert.Info(info)
    }

    data class RegisterStepOneError(override val reason: String) : RegisterError(reason)
    data class RegisterStepTwoError(override val reason: String) : RegisterError(reason)

    data class RegisterStepOneInfo(override val info: String) : RegisterInfo(info)
}

sealed class RegisterIntent {
    data class RequestConfirmPhone(val number: String) : RegisterIntent()
    data class ConfirmPhoneWithCode(val code: String) : RegisterIntent()
    data class Register(
        val fullName: String,
        val mail: String,
        val password: String,
        val passwordConfirmation: String
    ) : RegisterIntent()
}

sealed class RegisterAction {
    data class Register(
        val fullName: String,
        val mail: String,
        val password: String,
        val passwordConfirmation: String
    ) : RegisterAction()

    data class RequestConfirmPhone(val number: String) : RegisterAction()
    data class ConfirmPhoneWithCode(val code: String) : RegisterAction()
}
