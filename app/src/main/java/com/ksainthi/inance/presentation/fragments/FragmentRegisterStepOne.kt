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
import com.ksainthi.inance.databinding.FragmentRegisterStepOneBinding
import com.ksainthi.inance.presentation.model.RegisterIntent
import com.ksainthi.inance.presentation.viewmodels.RegisterViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentRegisterStepOne : Fragment() {

    private lateinit var binding: FragmentRegisterStepOneBinding
    private val registerViewModel: RegisterViewModel by viewModel()
    private val navController: NavController by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register_step_one, container, false)

        initView()
        initViewModel()

        return binding.root
    }

    fun initView() {

        with(binding) {
            submitButton.setOnClickListener {

                val fullName = binding.fullName.text.toString()
                val mail = binding.mail.text.toString()
                val password = binding.password.text.toString()
                val passwordConfirmation = binding.passwordConfirmation.text.toString()

                val intent = RegisterIntent.Register(fullName, mail, password, passwordConfirmation)
                registerViewModel.dispatchIntent(intent)
            }
        }

    }

    fun initViewModel() {
        registerViewModel.state.observe(viewLifecycleOwner) {

        }
    }

}