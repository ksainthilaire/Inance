package com.ksainthi.inance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class AuthActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var gso: GoogleSignInOptions
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var googleSignIn: ActivityResultLauncher<Intent>

    private var currentFragment: Int = REGISTER_FRAGMENT


    companion object {

        const val REGISTER_FRAGMENT = 0
        const val LOGIN_FRAGMENT    = 1
        const val HOME_FRAGMENT     = 2
        const val WELCOME_FRAGMENT  = 3

        private const val CLIENT_ID =
            "133201850085-bl4gk067qv4cfs73m4avjlbj7hanoe80.apps.googleusercontent.com"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        val params = getIntent().getExtras()

        val fragmentId = params?.getInt("fragmentToDisplay")


        this.gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(CLIENT_ID)
            .requestEmail()
            .build()
        this.mGoogleSignInClient = GoogleSignIn.getClient(this, this.gso)
        this.googleSignIn = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result -> this.authWithGoogle(result)
        }

        firebaseAuth = FirebaseAuth.getInstance()

        this.checkIfUserAlreadyLogged()
        this.loadFragment(fragmentId)
    }

    override fun onBackPressed() {

        Log.d("DEBUG", "count" + getSupportFragmentManager().getBackStackEntryCount().toString())

       if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
           getSupportFragmentManager().popBackStack()
       }
    }

    fun loadFragment(fragmentId: Int?) {
        var fragment: Fragment = HomeFragment()
        Log.d("DEBUG", "count" + getSupportFragmentManager().getBackStackEntryCount().toString())

        when (fragmentId) {
            HOME_FRAGMENT -> {
                fragment = HomeFragment()
            }
            LOGIN_FRAGMENT -> {
                fragment = LoginFragment()
            }
            REGISTER_FRAGMENT -> {
                fragment = RegisterFragment()
            }
            WELCOME_FRAGMENT -> {
                fragment = WelcomeFragment()
            }
        }


        val transaction = supportFragmentManager.beginTransaction()

        transaction.replace(R.id.fragment_container_view, fragment)
    //    transaction.disallowAddToBackStack()
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun checkIfUserAlreadyLogged() {
        if (firebaseAuth.currentUser != null) {
            // L'utilisateur est déjà connecté
        }
    }

  fun registerWithCredentials(mail: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(mail, password)
            .addOnCompleteListener { authResult ->
                if (authResult.isSuccessful()) {
                    // Authentification réussi
                } else {
                    // Erreur d'authentification
                }
            }

    }

    fun launchGoogleSignIn() {
        val signInIntent = mGoogleSignInClient.getSignInIntent()
        googleSignIn.launch(signInIntent)
    }

    private fun authWithGoogle(result: ActivityResult) {
        if (result.resultCode == RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            val taskResult = task.getResult()
            this.firebaseAuthWithGoogleAccount(taskResult)
        }
    }

    private fun firebaseAuthWithGoogleAccount(account: GoogleSignInAccount) {

        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnSuccessListener { authResult ->
                Log.d("Firebase", "Ok success")

                if (authResult.additionalUserInfo!!.isNewUser) {
                    Log.d("Firebas", "Nouvel utilisateur")
                }
            }
            .addOnFailureListener { e ->
                Log.d("Firebase", "Logs  ${e.message}")
            }

    }


    fun loginWithCredentials(mail: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(mail, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful()) {
                    val user = firebaseAuth.currentUser
                    // L'utilisateur viens de s'authentifier
                } else {

                }
            }
 


    }
}