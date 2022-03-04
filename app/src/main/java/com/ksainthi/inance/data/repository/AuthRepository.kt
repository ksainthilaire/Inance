package com.ksainthi.inance.data.repository


import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.res.Resources
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.ksainthi.inance.R
import com.ksainthi.inance.data.network.GoogleAuthService
import com.ksainthi.inance.domain.repository.IAuthRepository
import kotlinx.coroutines.flow.Flow
import org.koin.java.KoinJavaComponent.inject

class AuthRepository() : IAuthRepository {

    private val resources: Resources by inject(Resources::class.java)
    private val googleAuth: GoogleAuthService by inject(GoogleAuthService::class.java)
    private val firebaseAuth: FirebaseAuth by inject (FirebaseAuth::class.java)

    fun loginWithGoogle() {

    }

    private fun registerWithCredentials(mail: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(mail, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {

                } else {

                }
            }
    }

    private fun signWithCredentials(credentials: AuthCredential) {
        firebaseAuth.signInWithCredential(credentials)
            .addOnSuccessListener {
                it.additionalUserInfo?.let {
                    if (it.isNewUser) { }
                }
            }
            .addOnFailureListener {

            }
    }

    fun googleSignIn(activity: AppCompatActivity) {
        activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

            when (it.resultCode) {

                RESULT_OK -> {
                    val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
                    val account: GoogleSignInAccount = task.result

                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    signWithCredentials(credential)
                }

            }
        }
    }

    fun handleFacebookCallback() = object : FacebookCallback<LoginResult> {

        override fun onSuccess(result: LoginResult) {
            val token = result.accessToken.token
            val credential = FacebookAuthProvider.getCredential(token)
            firebaseAuth.signInWithCredential(credential)
        }

        override fun onCancel() {

        }

        override fun onError(error: FacebookException) {

        }
    }

}