package com.ksainthi.inance.di

import android.content.res.Resources
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.ksainthi.inance.R
import com.ksainthi.inance.data.network.GoogleAuthService
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val networkModule = module {

    single<Resources> {
        androidContext().resources
    }

    single<GoogleSignInOptions> {

        val resources: Resources by inject()
        val googleClientId = resources.getString(R.string.google_client_id)

        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(googleClientId)
            .requestEmail()
            .requestProfile()
            .build()
    }

    single<FirebaseAuth> {
        FirebaseAuth.getInstance()
    }

    single<GoogleAuthService> {
        GoogleAuthService()
    }


}