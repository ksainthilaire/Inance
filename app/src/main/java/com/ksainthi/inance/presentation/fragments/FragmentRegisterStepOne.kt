package com.ksainthi.inance.presentation.fragments

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.ksainthi.inance.R
import com.ksainthi.inance.components.AlertDesc
import com.ksainthi.inance.databinding.FragmentRegisterStepOneBinding
import com.ksainthi.inance.presentation.model.RegisterIntent
import com.ksainthi.inance.presentation.model.RegisterState
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

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_register_step_one, container, false)

        initView()
        initViewModel()

        return binding.root
    }

    fun goNextStep() {
        val action = FragmentRegisterStepOneDirections.actionFragmentRegisterStepOneToFragmentRegisterStepTwo()
        navController.navigate(action)
    }

    fun initView() {

        with(binding) {
            submitButton.setOnClickListener {

                goNextStep()

                val fullName = binding.fullName.getValue()
                val mail = binding.mail.getValue()
                val password = binding.password.getValue()
                val rePassword = binding.rePassword.getValue()

                val intent = RegisterIntent.Register(fullName, mail, password, rePassword)
                registerViewModel.dispatchIntent(intent)
            }
        }

    }

    private fun createGradientDrawable(color: Int = Color.GREEN): GradientDrawable {
        val shape = GradientDrawable()
        shape.setStroke(3, color)
        shape.cornerRadius = 15f

        return shape
    }


    fun handleViewErrors(state: RegisterState) {

        val redGradientDrawable = createGradientDrawable(Color.RED)

        with(binding) {
            if (state.fullName.isError) binding.fullName.showError("Erreur")
            if (state.password.isError) binding.password.showError("Erreur")
            if (state.rePassword.isError) binding.rePassword.showError("Erreur")
            if (state.mail.isError) binding.fullName.showError("Erreur")
        }
    }


    fun initViewModel() {
        registerViewModel.state.observe(viewLifecycleOwner) {
            handleViewErrors(it)
        }
    }

}