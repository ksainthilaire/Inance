package com.ksainthi.inance.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.ksainthi.inance.R
import com.ksainthi.inance.databinding.FragmentRegisterStepTwoBinding
import com.ksainthi.inance.presentation.viewmodels.RegisterViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentRegisterStepTwo : Fragment() {

    private lateinit var binding: FragmentRegisterStepTwoBinding
    private val registerViewModel: RegisterViewModel by viewModel()
    private val navController: NavController by lazy {
        findNavController()
    }
    private lateinit var rootView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {



        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register_step_two, container, false)

        initView()

        return binding.root
    }

    fun initView() {
        with (binding) {

            sendCodeButton.setOnClickListener {
                val number = binding.numberPhone.text.toString()
            }

            submitButton.setOnClickListener {
                val digits = getDigits()
            }
        }
    }

    private fun concatenate(vararg string: String?): String {
        return string.joinToString("")
    }

    private fun getDigits(): String {
        val firstDigit = binding.firstDigit.text.toString()
        val secondDigit = binding.secondDigit.text.toString()
        val thirdDigit = binding.thirdDigit.text.toString()
        val fourthDigit = binding.fourthDigit.text.toString()

        return this.concatenate(firstDigit, secondDigit, thirdDigit, fourthDigit)
    }
}