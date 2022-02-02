package com.ksainthi.inance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment


class AuthActivity : AppCompatActivity() {


    val REGISTER_FRAGMENT = 0
    val LOGIN_FRAGMENT = 1
    val HOME_FRAGMENT = 2
    val WELCOME_FRAGMENT = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        val googleSignIn = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            AuthHelper.loginWithGoogleHandler(result)
        }
        AuthHelper.configureGoogleSignIn(this, googleSignIn,
            onSuccess = {
                startMainActivity()
            },
            onError = {

            })

        this.loadFragment(null)
    }

    override fun onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack()
        }
    }

    fun loadFragment(fragmentId: Int?) {
        var fragment: Fragment = HomeFragment()

        when (fragmentId) {
            HOME_FRAGMENT -> {
                fragment = HomeFragment()
            }
            LOGIN_FRAGMENT -> {
                fragment = LoginFragment()
            }
            REGISTER_FRAGMENT -> {
                fragment = RegisterFragment()
            }
            WELCOME_FRAGMENT -> {
                fragment = WelcomeFragment()
            }
        }

        val transaction = supportFragmentManager.beginTransaction()

        transaction.replace(R.id.fragment_container_view, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun startMainActivity() {
        val mainActivity = Intent(this, MainActivity::class.java)
        startActivity(mainActivity)
    }

}