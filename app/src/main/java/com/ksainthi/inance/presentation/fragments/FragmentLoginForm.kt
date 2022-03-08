package com.ksainthi.inance.presentation.fragments



import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.ksainthi.inance.R
import com.ksainthi.inance.components.Alert
import com.ksainthi.inance.components.AlertDesc
import com.ksainthi.inance.databinding.FragmentLoginFormBinding
import com.ksainthi.inance.presentation.model.LoginIntent
import com.ksainthi.inance.presentation.model.LoginState
import com.ksainthi.inance.presentation.viewmodels.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentLoginForm : Fragment() {


    private lateinit var binding: FragmentLoginFormBinding
    private val loginViewModel: LoginViewModel by viewModel()
    private val navController: NavController by lazy {
        findNavController()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login_form, container, false)

        initViewModel()
        initView()

        return binding.root
    }

    fun initViewModel() {
        loginViewModel.state.observe(viewLifecycleOwner) {
            updateView(it)
        }
    }

    fun initView() {
        with(binding) {
            loginButton.setOnClickListener {
                val mail = binding.mail.text.toString()
                val password = binding.password.text.toString()
                val intent = LoginIntent.Login(mail, password)

                loginViewModel.dispatchIntent(intent)
            }

            forgotButton.setOnClickListener {
                val action = FragmentLoginFormDirections.actionFragmentLoginFormToFragmentLoginForgotForm2()
                navController.navigate(action)
            }
        }
    }

    private fun createGradientDrawable(color: Int): GradientDrawable {
        val shape = GradientDrawable()
        shape.setStroke(3, color)
        shape.cornerRadius = 15f

        return shape
    }



    private fun updateViewFields(it: LoginState) {
        with(binding) {
            if (it.isMailError)  mail.background = createGradientDrawable(Color.RED)
            if (it.isPasswordError)  password.background = createGradientDrawable(Color.RED)
        }
    }


    private fun updateView(state: LoginState) {
        state.alert?.let { it: AlertDesc -> binding.alert.showMessage(it.text, it.type) }
        updateViewFields(state)
    }

    private fun getColor(id: Int): Int {
        val context = requireContext()
        return ContextCompat.getColor(context, id)
    }
}
