package com.ksainthi.inance.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.facebook.FacebookSdk
import com.google.firebase.FirebaseApp
import com.ksainthi.inance.R


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FacebookSdk.sdkInitialize(applicationContext)
        FirebaseApp.initializeApp(applicationContext)
        setContentView(R.layout.activity_main)
    }
}