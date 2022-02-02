package com.ksainthi.inance

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.ContentValues
import android.content.Intent
import android.util.Log

import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore

private val CLIENT_ID =
    "133201850085-bl4gk067qv4cfs73m4avjlbj7hanoe80.apps.googleusercontent.com"

object AuthHelper {

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()


    var googleSignInOptions: GoogleSignInOptions? = null
    var googleSignInClient: GoogleSignInClient? = null
    var googleSignIn: ActivityResultLauncher<Intent>? = null

    private lateinit var onSuccess: () -> Unit
    private lateinit var onError: () -> Unit

    fun configureGoogleSignIn(activity: Activity, googleSignIn: ActivityResultLauncher<Intent>,
        onSuccess: () -> Unit, onError: () -> Unit) {
        this.onSuccess = onSuccess
        this.onError   = onError

        this.googleSignInOptions =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(CLIENT_ID)
                .requestEmail()
                .requestProfile()
                .build()

        this.googleSignIn = googleSignIn
        this.googleSignInClient = GoogleSignIn.getClient(activity, this.googleSignInOptions!!)

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
                    this.onSuccess()
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
                    this.onSuccess()
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
                        this.onSuccess()
                    }
                    .addOnFailureListener { e ->
                        Log.d("Firebase", "Logs  ${e.message}")
                    }

            }
        }
    }

}