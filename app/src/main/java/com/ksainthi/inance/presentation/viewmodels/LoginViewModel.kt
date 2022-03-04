package com.ksainthi.inance.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ksainthi.inance.presentation.model.LoginAction
import com.ksainthi.inance.presentation.model.LoginIntent
import com.ksainthi.inance.presentation.model.LoginPartialState
import com.ksainthi.inance.presentation.model.LoginState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class LoginViewModel : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext = Job() + Dispatchers.Default
    private val channel = Channel<LoginPartialState>()
    val state = MutableLiveData<LoginState>()

    init {
        launch(Dispatchers.Main) {
            for (partialState in channel) {
                val newState = reduce(state.value!!, partialState)
                state.postValue(newState)
            }
        }
    }

    private fun reduce(state: LoginState, partialState: LoginPartialState) : LoginState {
        return when (partialState) {

            is LoginPartialState.Loading -> state.copy(
                isLoadingEnabled = partialState.isLoadingEnabled,
                step = state.step
            )

            is LoginPartialState.LoginSuccessful -> state.copy(
                step = partialState.step
            )

            is LoginPartialState.LoginFailed -> state.copy(
                alert = partialState.alert,
                step = state.step
            )

            is LoginPartialState.ResetPassword -> state.copy(
                step = state.step,
                alert = partialState.alert
            )

            is LoginPartialState.PasswordResetSuccessful -> state.copy(
                alert = partialState.alert,
                step = state.step
            )

            else  -> throw AssertionError()

        }
    }

    fun dispatchIntent(intent: LoginIntent) {
        handleAction(intentToAction(intent))
    }

    private fun intentToAction(intent: LoginIntent) : LoginAction {
        return when (intent) {
            is LoginIntent.Login -> LoginAction.Login(intent.username, intent.password)
            is LoginIntent.ResetPassword -> LoginAction.ResetPassword(intent.code)
            is LoginIntent.RequestResetPassword -> LoginAction.RequestPasswordReset(intent.mail)
        }
    }

    private fun handleAction(action: LoginAction) {
        when (action) {
            is LoginAction.Login -> { Unit }
            is LoginAction.ResetPassword -> { Unit }
            is LoginAction.RequestPasswordReset -> { Unit }
        }
    }

}