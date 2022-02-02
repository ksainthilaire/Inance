package com.ksainthi.inance

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val parentActivity = activity as AuthActivity
        val rootView = inflater.inflate(R.layout.fragment_login, container, false)

        val mail = rootView.findViewById<EditText>(R.id.mail)
        val password = rootView.findViewById<EditText>(R.id.password)

        val loginButton = rootView.findViewById<Button>(R.id.login_button)
        loginButton.setOnClickListener {
            AuthHelper.loginWithCredentials(
                mail.text.toString(),
                password.text.toString()
            )
        }

        return rootView
    }

}