package com.example.ksainthi

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

        val google_button = rootView.findViewById<Button>(R.id.google_button)

        google_button.setOnClickListener {
            parentActivity.launchGoogleSignIn()
        }


        val register_button = rootView.findViewById<Button>(R.id.register_button)
        register_button.setOnClickListener {
            parentActivity.loadFragment(AuthActivity.REGISTER_FRAGMENT)
        }

        val submit_button = rootView.findViewById<TextView>(R.id.login_button)
        submit_button.setOnClickListener {
           parentActivity.loadFragment(AuthActivity.LOGIN_FRAGMENT)
        }

        return rootView
    }

}