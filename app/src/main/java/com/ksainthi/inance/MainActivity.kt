package com.ksainthi.inance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(applicationContext)
        setContentView(R.layout.activity_main)


        if (!AuthHelper.isAlreadyLogged()) {
            return this.displayLogin()
        }
        this.displayFragment(R.id.fragment_container_view, ProfileFragment())
        this.displayMenu()
    }

    private fun displayMenu() {
        this.displayFragment(R.id.fragment_menu, MenuFragment())
    }


    private fun displayFragment(containerViewId: Int, fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()

        transaction.replace(containerViewId, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun displayLogin() {
        val authActivity = Intent(this, AuthActivity::class.java)
        startActivity(authActivity)
    }
}