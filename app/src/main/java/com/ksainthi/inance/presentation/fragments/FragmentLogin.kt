package com.ksainthi.inance.presentation.fragments

import LoginAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.ksainthi.inance.R
import com.ksainthi.inance.databinding.FragmentLoginBinding
import com.ksainthi.inance.presentation.viewmodels.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentLogin : Fragment() {


    private lateinit var binding: FragmentLoginBinding
    private val loginViewModel: LoginViewModel by viewModel()
    private val navController: NavController by lazy {
        findNavController()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)


       initView()

        return binding.root
    }


    fun initView() {

        val adapter = LoginAdapter(this)

        with(binding) {
            pager.adapter = adapter
            pager.isUserInputEnabled = false
        }
    }
    /*
    fun initView() {
        with (binding) {
            loginButton.setOnClickListener {
                val mail = mail.text.toString()
                val password = password.text.toString()

                val intent = LoginIntent.Login(mail, password)
                loginViewModel.dispatchIntent(intent)
            }
        }
    }

    fun initViewModel() {
        loginViewModel.state.observe(viewLifecycleOwner) {

        }
    }*/
}