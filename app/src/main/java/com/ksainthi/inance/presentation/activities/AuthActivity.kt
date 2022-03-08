package com.ksainthi.inance.presentation.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.ksainthi.inance.presentation.MainActivity
import com.ksainthi.inance.presentation.fragments.FragmentHome
import com.ksainthi.inance.presentation.fragments.FragmentRegisterStepOne
import com.ksainthi.inance.presentation.fragments.FragmentRegisterStepFour


class AuthActivity : AppCompatActivity() {


    val REGISTER_FRAGMENT = 0
    val LOGIN_FRAGMENT = 1
    val HOME_FRAGMENT = 2
    val WELCOME_FRAGMENT = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val googleSignIn =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                AuthHelper.loginWithGoogleHandler(result)
            }
        AuthHelper.initCallbacks(
            onSuccess = { startMainActivity() },
            onError = {}
        )
        AuthHelper.configureGoogleSignIn(this, googleSignIn)
        AuthHelper.configureFacebookSignIn(this)


    }

    override fun onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack()
        }
    }

    fun startMainActivity() {
        val mainActivity = Intent(this, MainActivity::class.java)
        startActivity(mainActivity)
    }

}