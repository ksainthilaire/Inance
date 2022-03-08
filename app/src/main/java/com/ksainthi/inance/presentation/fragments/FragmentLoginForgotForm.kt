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
import com.ksainthi.inance.databinding.FragmentLoginForgotFormBinding
import com.ksainthi.inance.presentation.model.LoginIntent
import com.ksainthi.inance.presentation.viewmodels.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class FragmentLoginForgotForm : Fragment() {

    private lateinit var binding: FragmentLoginForgotFormBinding
    private val loginViewModel: LoginViewModel by viewModel()
    private val navController: NavController by lazy {
        findNavController()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login_forgot_form, container, false)

        initView()
        initViewModel()

        return binding.root
    }

    fun initView() {
        with (binding) {
            submitButton.setOnClickListener {
                val mail = binding.mail.text.toString()
                val intent = LoginIntent.RequestResetPassword(mail)
                loginViewModel.dispatchIntent(intent)
            }
        }
    }

    fun initViewModel() {
        loginViewModel.state.observe(viewLifecycleOwner) { state ->
          state.alert?.let { it: AlertDesc -> binding.alert.showMessage(it.text, it.type) }
        }
    }

}