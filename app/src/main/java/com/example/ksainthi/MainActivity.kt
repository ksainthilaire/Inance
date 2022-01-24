package com.example.ksainthi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.firebase.FirebaseApp


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(applicationContext)
        setContentView(R.layout.activity_main)

        val loginPage = Intent(this, LoginActivity::class.java)
        val registerPage = Intent(this, RegisterActivity::class.java)


        val register_button = findViewById<TextView>(R.id.register_button)
        register_button.setOnClickListener {
            this.changePage(registerPage)
        }

        val submit_button = findViewById<TextView>(R.id.login_button)
        submit_button.setOnClickListener {
           this.changePage(loginPage)
        }
    }

    private fun changePage(page: Intent) {
        startActivity(page)
    }
}