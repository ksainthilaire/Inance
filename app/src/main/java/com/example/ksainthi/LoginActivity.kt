package com.example.ksainthi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import android.widget.Button
import android.widget.ImageButton
import android.widget.EditText
import android.widget.TextView
import android.content.Intent
import android.util.Log
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        firebaseAuth = FirebaseAuth.getInstance()

       val fingerprintButton = findViewById<ImageButton>(R.id.fingerprint_button)

        fingerprintButton.setOnClickListener {
            this.onFingerprint()
        }

        val forgotButton = findViewById<TextView>(R.id.forgot_button)

        forgotButton.setOnClickListener {
            this.onForgot()
        }

        val mailInput = findViewById<EditText>(R.id.mail)
        val passwordInput = findViewById<EditText>(R.id.password)
        val loginButton = findViewById<Button>(R.id.login_button)

        loginButton.setOnClickListener {
            this.createLogin()
            this.onSubmit(mailInput.text.toString(), passwordInput.text.toString())
        }


        this.createToast("Hello World")
    }

    private fun isAlreadyLogged() {
        if (firebaseAuth.currentUser != null) {
            // Already logged
        }
    }

    private fun firebaseLogin(mail: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(mail, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                } else {
                    Log.d("firebaseLogin()", "failed")
                }
            }
    }

    private fun createLogin() {
        val intent = Intent(this, WelcomeActivity::class.java)
        startActivity(intent    )
    }

    private fun createToast(text: String) {
        Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT).show()
    }

    private fun onForgot() {
      // val intent = Intent(this, ForgotActivity::class.java)
    // changeActivity(intent)
    }

    private fun onSubmit(mail: String, password: String) {

    }

    private fun onFingerprint() {

    }
}