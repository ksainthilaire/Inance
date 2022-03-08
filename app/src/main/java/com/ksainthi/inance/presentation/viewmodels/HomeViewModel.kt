package com.ksainthi.inance.presentation.viewmodels

import android.content.res.Resources
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ksainthi.inance.components.AlertDesc
import com.ksainthi.inance.components.AlertType
import com.ksainthi.inance.presentation.model.HomeAction
import com.ksainthi.inance.presentation.model.HomeIntent
import com.ksainthi.inance.presentation.model.HomePartialState
import com.ksainthi.inance.presentation.model.HomeState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject
import kotlin.coroutines.CoroutineContext

class HomeViewModel : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext = Job() + Dispatchers.Default
    private val channel = Channel<HomePartialState>()
    private val resources : Resources by inject(Resources::class.java)
    val state = MutableLiveData<HomeState>()

    init {
        state.value = HomeState()
        launch(Dispatchers.Main) {
            for (partialState in channel) {
                val newState = reduce(state.value!!, partialState)
                state.value = newState
            }
        }
    }

    private fun reduce(state: HomeState, partialState: HomePartialState): HomeState {
        return when (partialState) {

            is HomePartialState.Loading -> state.copy(
                isLoadingEnabled = partialState.isLoadingEnabled,
                step = state.step
            )

            is HomePartialState.Successful -> state.copy(
                step = partialState.step
            )

            is HomePartialState.HomeAlert -> state.copy(
                alert = partialState.alert,
            )


        }
    }

    fun dispatchIntent(intent: HomeIntent) {
        handleAction(intentToAction(intent))
    }

    private fun intentToAction(intent: HomeIntent): HomeAction {
        return when (intent) {
            is HomeIntent.SignInWithFacebook -> HomeAction.SignInWithFacebook
            is HomeIntent.SignInWithGoogle -> HomeAction.SignInWithGoogle
        }
    }

    private fun handleAction(action: HomeAction) {
        launch {
            return@launch when (action) {
                is HomeAction.SignInWithGoogle -> {

                    val alert = AlertDesc(AlertType.ERROR, "Connexion refusé")
                    channel.send(HomePartialState.HomeAlert(alert))
                }
                is HomeAction.SignInWithFacebook -> {

                    val alert = AlertDesc(AlertType.SUCCESS, "Connexion réussie!")
                    channel.send(HomePartialState.HomeAlert(alert))
                }
            }
        }
    }



}