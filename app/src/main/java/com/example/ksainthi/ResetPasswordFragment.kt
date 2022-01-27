package com.example.ksainthi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText

class ResetPasswordFragment : Fragment() {

    private lateinit var rootView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.rootView = inflater.inflate(R.layout.fragment_reset_password, container, false)

        val password = this.rootView.findViewById<EditText>(R.id.password)
        val passwordConfirmation = this.rootView.findViewById<EditText>(R.id.password_confirmation)

        val submitButton = this.rootView.findViewById<Button>(R.id.submit_button)
        submitButton.setOnClickListener {

        }

        return this.rootView
    }


}