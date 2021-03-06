package com.ksainthi.inance.presentation.fragments

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
        initViewModel()

        return binding.root
    }

    fun initView() {
        with (binding) {

            sendCodeButton.setOnClickListener {
                val number = binding.numberPhone.getValue()
                Log.d("TAG", "Le numéro de téléphone est ${number}")
            }

            submitButton.setOnClickListener {
                val code = binding.code.getValue()
                Log.d("TAG", "Le code est ${code}")
            }
        }
    }
    fun initViewModel() {
        registerViewModel.state.observe(viewLifecycleOwner) {
            it.alert?.let { state  : AlertDesc -> binding.alert.showMessage(state.text, state.type) }
        }
    }


}