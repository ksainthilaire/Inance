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
import com.ksainthi.inance.databinding.FragmentRegisterBinding
import com.ksainthi.inance.databinding.FragmentRegisterStepFourBinding
import com.ksainthi.inance.presentation.viewmodels.RegisterViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentRegisterStepFour : Fragment() {

    private lateinit var binding: FragmentRegisterStepFourBinding
    private val registerViewModel: RegisterViewModel by viewModel()
    private val navController: NavController by lazy {
        findNavController()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register_step_four, container, false)

        return binding.root
    }

}