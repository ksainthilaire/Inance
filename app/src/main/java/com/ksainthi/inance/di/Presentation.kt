package com.ksainthi.inance.di

import com.ksainthi.inance.presentation.viewmodels.HomeViewModel
import com.ksainthi.inance.presentation.viewmodels.LoginViewModel
import com.ksainthi.inance.presentation.viewmodels.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    viewModel { HomeViewModel() }
    viewModel { RegisterViewModel() }
    viewModel { LoginViewModel() }


}