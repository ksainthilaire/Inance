package com.ksainthi.inance

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.ContentValues
import android.content.Intent
import android.util.Log

import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

private val CLIENT_ID =
    "133201850085-bl4gk067qv4cfs73m4avjlbj7hanoe80.apps.googleusercontent.com"

object AuthHelper {

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()


    var googleSignInOptions: GoogleSignInOptions? = null
    var googleSignInClient: GoogleSignInClient? = null
    var googleSignIn: ActivityResultLauncher<Intent>? = null

    private lateinit var facebookSignInHandler: () -> Unit

    private lateinit var onAuthSuccess: () -> Unit
    private lateinit var onAuthError: () -> Unit


    fun initCallbacks(onSuccess: () -> Unit, onError: () -> Unit) {
        onAuthSuccess = onSuccess
        onAuthError = onError
    }


    fun configureGoogleSignIn(activity: Activity, googleSignIn: ActivityResultLauncher<Intent>) {
        this.googleSignInOptions =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(CLIENT_ID)
                .requestEmail()
                .requestProfile()
                .build()

        this.googleSignIn = googleSignIn
        this.googleSignInClient = GoogleSignIn.getClient(activity, this.googleSignInOptions!!)

    }


    fun configureFacebookSignIn(activity: Activity) {


        facebookSignInHandler = {
            val callbackManager = CallbackManager.Factory.create()

            LoginManager.getInstance()
                .logInWithReadPermissions(activity, Arrays.asList("public_profile", "email"))
            Log.d("TAG", "Hello world")
            LoginManager.getInstance().registerCallback(callbackManager,
                object : FacebookCallback<LoginResult> {

                    override fun onSuccess(loginResult: LoginResult) {
                        val token = loginResult.accessToken.token

                        Log.d("TAG", "Succès")
                        Log.d("TAG", "Succés".plus(token))
                        val credential = FacebookAuthProvider.getCredential(token)
                        firebaseAuth.signInWithCredential(credential)
                            .addOnCompleteListener(activity) { task ->
                                if (task.isSuccessful) {
                                    onAuthSuccess()
                                } else {
                                    onAuthError()
                                }
                            }
                    }

                    override fun onCancel() {
                        Log.d("TAG", "Succèswtf")

                    }

                    override fun onError(error: FacebookException) {
                        Log.d("TAG", "error")
                        onAuthError()
                    }

                })
        }

    }

    fun isAlreadyLogged(): Boolean {
        if (this.firebaseAuth.currentUser != null) {
            return true
        }
        return false
    }


    fun registerUser(uuid: String, fullName: String, mail: String) {
        val user = hashMapOf(
            "uuid" to uuid,
            "fullName" to fullName,
            "mail" to mail
        )

        db.collection("users").document()
            .set(user)
            .addOnSuccessListener {
                Log.d(
                    ContentValues.TAG,
                    "DocumentSnapshot successfully written!"
                )
            }
            .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error writing document", e) }

    }

    fun registerWithCredentials(mail: String, password: String) {
        Log.d("LOGLOG", "registerWithCredentials()")
        this.firebaseAuth.createUserWithEmailAndPassword(mail, password)
            .addOnCompleteListener { authResult ->
                if (authResult.isSuccessful()) {
                    Log.d("LOGLOG", "onSuccess()")
                    this.onAuthSuccess()
                } else {
                    Log.d("LOGLOG", "ErrorAuth()")
                    // Erreur d'authentification
                }
            }
    }

    fun loginWithCredentials(mail: String, password: String) {
        this.firebaseAuth.signInWithEmailAndPassword(mail, password)
            .addOnCompleteListener { authResult ->
                if (authResult.isSuccessful()) {
                    val user = this.firebaseAuth.currentUser
                    this.onAuthError()
                } else {

                }
            }
    }



    fun loginWithGoogle() {
        val signInIntent = this.googleSignInClient?.getSignInIntent()
        this.googleSignIn?.launch(signInIntent)
    }


    fun loginWithGoogleHandler(result: ActivityResult) {
        when (result.resultCode) {
            RESULT_OK -> {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                val account: GoogleSignInAccount = task.getResult()

                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                this.firebaseAuth.signInWithCredential(credential)
                    .addOnSuccessListener { authResult ->
                        Log.d("Firebase", "Ok success")

                        if (authResult.additionalUserInfo!!.isNewUser) {
                            Log.d("Firebase", "Nouvel utilisateur")
                        }
                        this.onAuthSuccess()
                    }
                    .addOnFailureListener { e ->
                        Log.d("Firebase", "Logs  ${e.message}")
                    }

            }
        }
    }

    fun loginWithFacebook() {
        facebookSignInHandler()
    }

}