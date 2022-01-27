package com.example.ksainthi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton

class RegisterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val parentActivity = activity as AuthActivity

        val rootView = inflater.inflate(R.layout.fragment_register, container, false)


        val username = rootView.findViewById<EditText>(R.id.username)
        val mail = rootView.findViewById<EditText>(R.id.mail)
        val password = rootView.findViewById<EditText>(R.id.password)
        val passwordConfirmation = rootView.findViewById<EditText>(R.id.password_confirmation)
        val submit  = rootView.findViewById<Button>(R.id.submit_button)

        submit.setOnClickListener {
            parentActivity.registerWithCredentials(
                mail.text.toString(),
                password.text.toString())
        }

        return rootView
    }

}