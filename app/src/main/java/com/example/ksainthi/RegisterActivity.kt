package com.example.ksainthi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.GoogleAuthProvider


class RegisterActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth;

    private lateinit var gso: GoogleSignInOptions
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var googleSignIn: ActivityResultLauncher<Intent>

    companion object {
        private const val CLIENT_ID = "133201850085-bl4gk067qv4cfs73m4avjlbj7hanoe80.apps.googleusercontent.com"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        firebaseAuth = FirebaseAuth.getInstance()

        this.gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(CLIENT_ID)
            .requestEmail()
            .build();
        this.mGoogleSignInClient = GoogleSignIn.getClient(this, this.gso);
        this.googleSignIn = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

                    if (result.resultCode == RESULT_OK) {


                        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                        val taskResult: GoogleSignInAccount = task.getResult()
                        this.firebaseAuthWithGoogleAccount(taskResult)
                  }

            }



        val apple_button    = findViewById<ImageButton>(R.id.apple_button)
        val facebook_button = findViewById<ImageButton>(R.id.facebook_button)
        val google_button   = findViewById<ImageButton>(R.id.google_button)
        val submit_button   = findViewById<Button>(R.id.submit_button)

        apple_button.setOnClickListener {  this.onAppleSubmit() }
        facebook_button.setOnClickListener { this.onFacebookSubmit() }
        google_button.setOnClickListener { this.onGoogleSubmit() }

        val usernameInput   = findViewById<EditText>(R.id.username)
        val mailInput       = findViewById<EditText>(R.id.mail)
        val passwordInput   = findViewById<EditText>(R.id.password)

        submit_button.setOnClickListener {
            val usernameValue = usernameInput.text.toString()
            val mailValue     = mailInput.text.toString()
            val passwordInput  = passwordInput.text.toString()

            this.onError("Hello World")
            this.firebaseRegister(mailValue, passwordInput)
        }
    }

    private fun firebaseRegister(mail: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(mail, password)
            .addOnCompleteListener { authResult ->
                if (authResult.isSuccessful()) {
                    Log.d("Firebase", "Authentification réussi ! ")
                } else {
                    Log.d("Firebase", "ok test ${mail} ${password}")
                    Log.d("Firebase error_code", (authResult.getException().toString()))
                }
            }

    }


    private fun firebaseAuthWithGoogleAccount(account: GoogleSignInAccount) {
            val idToken = account.idToken
            this.onError("Le token est " + idToken)

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

    private fun onError(text: String) {
        Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT)
            .show()
    }

    private fun onSubmit(username: String, mail: String, password: String) {

    }

    private fun onGoogleSubmit() {
        val signInIntent = mGoogleSignInClient.getSignInIntent()
        googleSignIn.launch(signInIntent)
    }

    private fun onFacebookSubmit() {

    }

    private fun onAppleSubmit() {

    }
}