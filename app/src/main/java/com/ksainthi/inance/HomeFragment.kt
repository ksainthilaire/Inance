package com.ksainthi.inance

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)
        val parentActivity: AuthActivity = (activity as AuthActivity)

        val googleButton = rootView.findViewById<Button>(R.id.google_button)

        googleButton.setOnClickListener {
            AuthHelper.loginWithGoogle()
        }

        val facebookButton = rootView.findViewById<Button>(R.id.facebook_button)
        facebookButton.setOnClickListener {
            AuthHelper.loginWithFacebook()
        }

        val registerButton = rootView.findViewById<Button>(R.id.register_button)
        registerButton.setOnClickListener {
            parentActivity.loadFragment(parentActivity.REGISTER_FRAGMENT)
        }

        val submitButton = rootView.findViewById<TextView>(R.id.login_button)
        submitButton.setOnClickListener {
           parentActivity.loadFragment(parentActivity.LOGIN_FRAGMENT)
        }

        return rootView
    }

}