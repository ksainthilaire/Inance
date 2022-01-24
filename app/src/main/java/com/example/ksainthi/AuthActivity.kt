package com.example.ksainthi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class AuthActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth;

    private lateinit var gso: GoogleSignInOptions
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var googleSignIn: ActivityResultLauncher<Intent>


    companion object {
        private const val CLIENT_ID =
            "133201850085-bl4gk067qv4cfs73m4avjlbj7hanoe80.apps.googleusercontent.com"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        this.gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(RegisterActivity.CLIENT_ID)
            .requestEmail()
            .build();
        this.mGoogleSignInClient = GoogleSignIn.getClient(this, this.gso);


        firebaseAuth = FirebaseAuth.getInstance()

        this.checkIfUserAlreadyLogged()

        findViewById<ImageButton>(R.id.fingerprint_button)
            .setOnClickListener {
                this.loginWithFingerprint()
            }
    }

    private fun checkIfUserAlreadyLogged() {
        if (firebaseAuth.currentUser != null) {
            // L'utilisateur est déjà connecté
        }
    }

    private fun registerWithCredentials(mail: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(mail, password)
            .addOnCompleteListener { authResult ->
                if (authResult.isSuccessful()) {
                    // Authentification réussi
                } else {
                    // Erreur d'authentification
                }
            }

    }

    private fun authWithGoogle(result: ActivityResult) {
        if (result.resultCode == RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data);
            val taskResult = task.getResult()
            this.firebaseAuthWithGoogleAccount(taskResult)
        }
    }

    private fun firebaseAuthWithGoogleAccount(account: GoogleSignInClient) {

        val idToken = account.idToken


        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnSuccessListener { authResult ->
                Log.d("Firebase", "Ok success")

                if (authResult.additionalUserInfo!!.isNewUser) {
                    Log.d("Firebas", "Nouvel utilisateur")
                }
            }
            .addOnFailureListener { e ->
                Log.d("Firebase", "Logs  ${e.message}")
            }

    }


    private fun loginWithCredentials(mail: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(mail, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccesfull) {
                    val user = firebaseAuth.currentUser
                    // L'utilisateur viens de s'authentifier
                } else {

                }
            }


        private fun loginWithFingerPrint() {

        }
    }