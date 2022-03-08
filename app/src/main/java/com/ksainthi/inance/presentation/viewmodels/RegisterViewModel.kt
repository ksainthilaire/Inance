package com.ksainthi.inance.presentation.viewmodels

import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ksainthi.inance.R
import com.ksainthi.inance.components.AlertDesc
import com.ksainthi.inance.components.AlertType
import com.ksainthi.inance.presentation.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject
import java.lang.IllegalStateException
import kotlin.coroutines.CoroutineContext

class RegisterViewModel : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext = Job() + Dispatchers.Default
    private val channel = Channel<RegisterPartialState>()
    private val resources: Resources by inject(Resources::class.java)
    val state = MutableLiveData<RegisterState>()

    init {
        state.value = RegisterState()
        launch(Dispatchers.Main) {
            for (partialState in channel) {
                val newState = reduce(state.value!!, partialState)
                state.postValue(newState)
            }
        }
    }

    private fun reduce(state: RegisterState, partialState: RegisterPartialState): RegisterState {
        return when (partialState) {

            is RegisterPartialState.Loading -> state.copy(
                isLoadingEnabled = partialState.isLoadingEnabled,
                step = state.step
            )

            is RegisterPartialState.RegisterStepOneSuccessful -> state.copy(
                isLoadingEnabled = partialState.isLoadingEnabled,
                fullName = partialState.fullName,
                mail = partialState.mail,
                password = partialState.password,
                rePassword = partialState.rePassword,

                step = partialState.step
            )

            is RegisterPartialState.RegisterFieldUpdates -> state.copy(
                fullName = partialState.fullName,
                mail = partialState.mail,
                password = partialState.password,
                rePassword = partialState.rePassword
            )

            is RegisterPartialState.RegisterAlert -> state.copy (
                isLoadingEnabled = partialState.isLoadingEnabled,
                alert = partialState.alert,
            )


            else -> throw IllegalStateException("Invalid state")

        }
    }

    fun dispatchIntent(intent: RegisterIntent) {
        handleAction(intentToAction(intent))
    }

    private fun intentToAction(intent: RegisterIntent): RegisterAction {
        return when (intent) {
            is RegisterIntent.Register -> RegisterAction.Register(
                intent.fullName, intent.mail,
                intent.password, intent.rePassword
            )
            is RegisterIntent.RequestConfirmPhone -> RegisterAction.RequestConfirmPhone(intent.number)
            is RegisterIntent.ConfirmPhoneWithCode -> RegisterAction.ConfirmPhoneWithCode(intent.code)
        }
    }

    private fun handleAction(action: RegisterAction) {
        launch {
            when (action) {
                is RegisterAction.Register -> {

                    Log.d("TAG", "RegisterAction.Register()")

                    val mail = Field(action.mail)
                    val password = Field(action.password)
                    val rePassword = Field(action.rePassword)
                    val fullName = Field(action.fullName)

                    if (android.util.Patterns.EMAIL_ADDRESS.matcher(mail.value).matches()) {
                        mail.isError = true
                        channel.send(

                            RegisterPartialState.RegisterFieldUpdates(
                                fullName,
                                mail,
                                password,
                                rePassword
                            )
                        )


                        val alert = AlertDesc(
                            AlertType.ERROR,
                            resources.getString(R.string.error_register_mail_invalid)
                        )

                        channel.send(RegisterPartialState.RegisterAlert(alert))
                        return@launch
                    }

                    if (password.value != rePassword.value) {
                        password.isError = true
                        rePassword.isError = true
                        channel.send(
                            RegisterPartialState.RegisterFieldUpdates(
                                mail,
                                password,
                                rePassword,
                                fullName
                            )
                        )

                        val alert = AlertDesc(
                            AlertType.ERROR,
                            resources.getString(R.string.error_register_password_different)
                        )


                        channel.send(
                            RegisterPartialState.RegisterAlert(alert)
                        )

                        return@launch
                    }

                   channel.send(
                        RegisterPartialState.RegisterStepOneSuccessful(
                            mail = Field(action.mail),
                            fullName = Field(action.fullName),
                            password = Field(action.password),
                            rePassword = Field(action.rePassword)
                        )
                    )
                }
                is RegisterAction.RequestConfirmPhone -> {
                    val phoneNumber = Field(action.number)


                }
                is RegisterAction.ConfirmPhoneWithCode -> {

                    channel.send(
                        RegisterPartialState.RegisterStepTwoSuccessful
                    )

                }
            }
        }
    }
}