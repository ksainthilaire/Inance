package com.ksainthi.inance.data.network

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.ksainthi.inance.presentation.activities.AuthHelper.googleSignIn
import org.koin.java.KoinJavaComponent.inject

class GoogleAuthService {

    val googleSignInOptions: GoogleSignInOptions by inject(GoogleSignInOptions::class.java)


}