package com.example.ksainthi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MobileVerificationFragment : Fragment() {

    private lateinit var rootView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        this.rootView = inflater.inflate(R.layout.fragment_mobile_verification, container, false)



        val numberPhone = this.rootView.findViewById<EditText>(R.id.number_phone)
        val sendCodeButton = rootView.findViewById<TextView>(R.id.send_code_button)
        sendCodeButton.setOnClickListener {

        }

        val submit_button = rootView.findViewById<Button>(R.id.submit_button)
        submit_button.setOnClickListener {
            val digits = this.getDigits()
        }

        return rootView
    }

    private fun concatenate(vararg string: String?): String {
        return string.joinToString("")
    }

    private fun getDigits(): String {
        val firstDigit = this.rootView.findViewById<EditText>(R.id.first_digit).text.toString()
        val secondDigit = this.rootView.findViewById<EditText>(R.id.second_digit).text.toString()
        val thirdDigit = this.rootView.findViewById<EditText>(R.id.third_digit).text.toString()
        val fourthDigit = this.rootView.findViewById<EditText>(R.id.fourth_digit).text.toString()

        return this.concatenate(firstDigit, secondDigit, thirdDigit, fourthDigit)
    }
}