package com.ksainthi.inance.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ksainthi.inance.presentation.model.ProfileAction
import com.ksainthi.inance.presentation.model.ProfileIntent
import com.ksainthi.inance.presentation.model.ProfilePartialState
import com.ksainthi.inance.presentation.model.ProfileState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import java.lang.AssertionError
import kotlin.coroutines.CoroutineContext

class ProfileViewModel : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext = Job() + Dispatchers.Default
    private val channel = Channel<ProfilePartialState>()
    private val state = MutableLiveData<ProfileState>()

    init {
        launch(Dispatchers.Main) {
            for (partialState in channel) {
                val newState = reduce(state.value!!, partialState)
                state.postValue(newState)
            }
        }
    }

    private fun reduce(state: ProfileState, partialState: ProfilePartialState): ProfileState {
        return when (partialState) {

            is ProfilePartialState.Loading -> state.copy(
                isLoadingEnabled = partialState.isLoadingEnabled,
                step = state.step
            )

            is ProfilePartialState.ProfileUpdateSuccessful -> state.copy(
                step = partialState.step
            )

            is ProfilePartialState.ProfileUpdateFailed -> state.copy(
                alert = partialState.alert,
                step = state.step
            )

            is ProfilePartialState.ProfileUpdateSuccessful -> state.copy(
                alert = partialState.alert,
                step = partialState.step
            )

            else -> { throw AssertionError() }
        }
    }

    fun dispatchIntent(intent: ProfileIntent) {
        handleAction(intentToAction(intent))
    }

    private fun intentToAction(intent: ProfileIntent): ProfileAction {
        return when (intent) {
            is ProfileIntent.DeleteAccount -> ProfileAction.DeleteAccount
            is ProfileIntent.UpdatePassword -> ProfileAction.UpdatePassword(
                intent.password,
                intent.newPassword
            )
            is ProfileIntent.EnableNotifications -> ProfileAction.EnableNotifications(intent.enable)
            is ProfileIntent.UpdateProfile -> ProfileAction.UpdateProfile(
                intent.username, intent.mail,
                intent.fullName, intent.country
            )
        }
    }

    private fun handleAction(action: ProfileAction) {
        when (action) {
            is ProfileAction.UpdateProfile -> {}
            is ProfileAction.EnableNotifications -> {}
            is ProfileAction.DeleteAccount -> {}
            is ProfileAction.UpdatePassword -> {}
        }
    }
}

