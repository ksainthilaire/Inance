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
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

import com.google.firebase.auth.FirebaseAuth


class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth;

    private lateinit var gso: GoogleSignInOptions
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var googleSignIn: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val loginManager    = LoginManager.getInstance()
        val callbackManager = CallbackManager.Factory.create()


        auth = FirebaseAuth.getInstance()

        this.gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build();
        this.mGoogleSignInClient = GoogleSignIn.getClient(this, this.gso);
        this.googleSignIn = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

                    if (result.resultCode == RESULT_OK) {
                        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                        val taskResult = task.getResult()
                        val account = taskResult.getAccount()
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
            this.onSubmit(usernameValue, mailValue, passwordInput)
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