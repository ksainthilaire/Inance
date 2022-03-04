package com.ksainthi.inance.presentation.viewmodels

import android.content.res.Resources
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ksainthi.inance.R
import com.ksainthi.inance.presentation.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject
import kotlin.coroutines.CoroutineContext

class RegisterViewModel : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext = Job() + Dispatchers.Default
    private val channel = Channel<RegisterPartialState>()
    private val resources: Resources by inject(Resources::class.java)
    val state = MutableLiveData<RegisterState>()

    init {
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
                registerForm = partialState.registerForm
            )

            is RegisterPartialState.RegisterStepTwoSuccessful -> state.copy(
                step = partialState.step,
                isVerifiedPhoneNumber = partialState.isVerifiedPhoneNumber,
                registerForm = state.registerForm
            )

            is RegisterPartialState.RegisterError -> state.copy(
                isLoadingEnabled = partialState.isLoadingEnabled,
                alert = partialState.alert,
                step = state.step,
                registerForm = state.registerForm
            )

            is RegisterPartialState.RegisterStepOneInfo -> state.copy(
                isLoadingEnabled = partialState.isLoadingEnabled,
                alert = partialState.alert,
                step = state.step,
                registerForm = state.registerForm
            )

            else -> throw AssertionError()

        }
    }

    fun dispatchIntent(intent: RegisterIntent) {
        handleAction(intentToAction(intent))
    }

    private fun intentToAction(intent: RegisterIntent): RegisterAction {
        return when (intent) {
            is RegisterIntent.Register -> RegisterAction.Register(
                intent.fullName, intent.mail,
                intent.password, intent.passwordConfirmation
            )
            is RegisterIntent.RequestConfirmPhone -> RegisterAction.RequestConfirmPhone(intent.number)
            is RegisterIntent.ConfirmPhoneWithCode -> RegisterAction.ConfirmPhoneWithCode(intent.code)
        }
    }

    private fun handleAction(action: RegisterAction) {
        launch {
            when (action) {
                is RegisterAction.Register -> {
                    if (android.util.Patterns.EMAIL_ADDRESS.matcher(action.mail).matches()) {
                        channel.send(
                            RegisterPartialState.RegisterStepOneError(resources.getString(R.string.error_register_mail_invalid))
                        )
                        return@launch
                    }

                    if (action.password != action.passwordConfirmation) {
                        channel.send(
                            RegisterPartialState.RegisterStepOneError(resources.getString(R.string.error_register_password_different))
                        )
                        return@launch
                    }

                    /*
                        TODO: Vérifier si l'adresse e-mail soumise n'est pas enregistré dans la base de donnée


                    if () {
                        channel.send(
                            RegisterPartialState.RegisterStepOneError(resources.getString(R.string.error_register_mail_already_taken))
                        )
                        return@launch
                    }
                    */


                    val form = RegisterForm(action.fullName, action.password, action.mail)
                    channel.send(
                        RegisterPartialState.RegisterStepOneSuccessful(form)
                    )
                }
                is RegisterAction.RequestConfirmPhone -> {

                    val form = state.value?.registerForm
                    form?.let {
                        form.number = action.number

                        /*
                        TODO: Vérifier si le numéro de téléphone est valide



                        channel.send(
                            RegisterPartialState.RegisterStepTwoError(resources.getString(R.string.error_register_number_invalid))
                        )
                        */

                        channel.send(
                            RegisterPartialState.RegisterStepOneInfo(resources.getString(R.string.info_register_verification_code_sent))
                        )

                    }

                }
                is RegisterAction.ConfirmPhoneWithCode -> {

                    /*
                        TODO: Vérifier le code
                    channel.send(
                        RegisterPartialState.RegisterStepTwoError(resources.getString(R.string.error_register_incorrect_verification_code))
                    )*/

                    channel.send(
                        RegisterPartialState.RegisterStepTwoSuccessful
                    )

                }
            }
        }
    }
}