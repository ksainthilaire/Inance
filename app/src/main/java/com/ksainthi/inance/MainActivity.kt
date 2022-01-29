package com.ksainthi.inance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.FirebaseApp


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(applicationContext)
        setContentView(R.layout.activity_main)
        this.showFragment()
    //this.displayAuthWithFragment(AuthActivity.HOME_FRAGMENT)

        /*
        val register_button = findViewById<Button>(R.id.register_button)
        register_button.setOnClickListener {
            this.displayAuthWithFragment(AuthActivity.REGISTER_FRAGMENT)
        }

        val submit_button = findViewById<TextView>(R.id.login_button)
        submit_button.setOnClickListener {
           this.displayAuthWithFragment(AuthActivity.LOGIN_FRAGMENT)
        }*/
    }

    private fun showFragment () {
        val transaction = supportFragmentManager.beginTransaction()

        transaction.replace(R.id.fragment_container_view, ProfileFragment())
        //    transaction.disallowAddToBackStack()
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