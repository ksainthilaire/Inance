package com.ksainthi.inance.presentation.viewmodels

import android.content.res.Resources
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
import kotlin.coroutines.CoroutineContext

class LoginViewModel : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext = Job() + Dispatchers.Default
    private val channel = Channel<LoginPartialState>()
    private val resources: Resources by inject(Resources::class.java)
    val state = MutableLiveData<LoginState>()

    init {
        state.value = LoginState()
        launch(Dispatchers.Main) {
            for (partialState in channel) {
                val newState = reduce(state.value!!, partialState)
                state.postValue(newState)
            }
        }
    }

    private fun reduce(state: LoginState, partialState: LoginPartialState): LoginState {
        return when (partialState) {

            is LoginPartialState.Loading -> state.copy(
                isLoadingEnabled = partialState.isLoadingEnabled,
                step = state.step
            )

            is LoginPartialState.LoginSuccessful -> state.copy(
                isLoadingEnabled = partialState.isLoadingEnabled,
                step = partialState.step
            )

            is LoginPartialState.LoginAlert -> state.copy(
                alert = partialState.alert
            )

            is LoginPartialState.PasswordResetRequestFailed -> state.copy(
                step = partialState.step
            )

            is LoginPartialState.PasswordResetRequestSuccessful -> state.copy(
                step = partialState.step
            )


        }
    }

    fun dispatchIntent(intent: LoginIntent) {
        handleAction(intentToAction(intent))
    }

    private fun intentToAction(intent: LoginIntent): LoginAction {
        return when (intent) {
            is LoginIntent.Login -> LoginAction.Login(intent.mail, intent.password)
            is LoginIntent.RequestResetPassword -> LoginAction.RequestPasswordReset(intent.mail)
        }
    }

    private fun handleAction(action: LoginAction) {
        launch {
            when (action) {
                is LoginAction.Login -> {


                    val alert = AlertDesc(AlertType.ERROR, resources.getString(R.string.error_unknown))
                    channel.send(LoginPartialState.LoginAlert(alert))

                }
                is LoginAction.RequestPasswordReset -> {


                    val alert =
                        AlertDesc(AlertType.ERROR, resources.getString(R.string.error_login_failed))
                    channel.send(LoginPartialState.LoginAlert(alert))
                }
            }
        }
    }

}