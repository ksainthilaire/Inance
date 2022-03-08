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
import com.ksainthi.inance.components.AlertDesc
import com.ksainthi.inance.databinding.FragmentHomeBinding
import com.ksainthi.inance.presentation.model.HomeIntent
import com.ksainthi.inance.presentation.viewmodels.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentHome : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModel()
    private val navController: NavController by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)


        initViewModel()
        initView()

        return binding.root
    }

    fun initViewModel() {
        homeViewModel.state.observe(viewLifecycleOwner) {
            it.alert?.let { it: AlertDesc -> binding.alert.showMessage(it.text, it.type) }
        }
    }



    fun initView() {


        with(binding) {

            googleButton.setOnClickListener {
                homeViewModel.dispatchIntent(HomeIntent.SignInWithGoogle)
            }

            facebookButton.setOnClickListener {
                homeViewModel.dispatchIntent(HomeIntent.SignInWithFacebook)
            }

            registerButton.setOnClickListener {
                val action = FragmentHomeDirections.actionHomeFragmentToFragmentRegisterStepOne()
                navController.navigate(action)
            }

            loginButton.setOnClickListener {
                val action = FragmentHomeDirections.actionHomeFragmentToFragmentLoginForm()
                navController.navigate(action)
            }
        }
    }


}