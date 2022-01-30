package com.ksainthi.inance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.firebase.FirebaseApp


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(applicationContext)
        setContentView(R.layout.activity_main)

        this.displayFragment(R.id.fragment_container_view, ProfileFragment())
        this.displayMenu()
    //this.displayAuthWithFragment(AuthActivity.HOME_FRAGMENT)
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

    private fun displayAuthWithFragment(fragment: Int) {
        val authActivity = Intent(this, AuthActivity::class.java)
        val bundle = Bundle()

        bundle.putInt("fragmentToDisplay", fragment)
        authActivity.putExtras(bundle)
        startActivity(authActivity)
    }
}