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
import com.ksainthi.inance.databinding.FragmentHomeBinding
import com.ksainthi.inance.presentation.model.HomeIntent
import com.ksainthi.inance.presentation.viewmodels.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.ksainthi.inance.presentation.model.Alert
import com.ksainthi.inance.presentation.model.HomeState

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
            updateView(it)
        }
    }

    fun getAlertBackground(alert: Alert): Int {
        println(alert)
        Log.d("wtf", alert.toString())
        return when (alert) {
            is Alert.Error -> R.drawable.alert_error
            is Alert.Success -> R.drawable.alert_success
            is Alert.Info -> R.drawable.alert_info
        }
    }

    fun updateView(it: HomeState) {
        with(binding) {
            if (it.alert != null) {
                alert.setBackgroundResource(getAlertBackground(it.alert))
                alert.visibility = View.VISIBLE
                alertText.text = it.alert?.reason
            } else alert.visibility = View.GONE
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
                val action = FragmentHomeDirections.actionHomeFragmentToRegisterFragment()
                navController.navigate(action)
            }

            loginButton.setOnClickListener {
                val action = FragmentHomeDirections.actionHomeFragmentToLoginFragment()
                navController.navigate(action)
            }
        }
    }


}