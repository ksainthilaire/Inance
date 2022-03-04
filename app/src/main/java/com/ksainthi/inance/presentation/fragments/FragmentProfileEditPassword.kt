package com.ksainthi.inance.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.ksainthi.inance.R

class  FragmentProfileEditPassword : Fragment() {

    private lateinit var rootView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.rootView = inflater.inflate(R.layout.fragment_profile_edit_password, container, false)

        val password = this.rootView.findViewById<EditText>(R.id.password)
        val passwordConfirmation = this.rootView.findViewById<EditText>(R.id.password_confirmation)

        val submitButton = this.rootView.findViewById<Button>(R.id.submit_button)
        submitButton.setOnClickListener {

        }

        return this.rootView
    }


}