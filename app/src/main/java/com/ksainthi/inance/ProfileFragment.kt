package com.ksainthi.inance

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {

    private lateinit var rootView: View
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val parentActivity = (activity as MainActivity)


        val acct: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(parentActivity)
        if (acct != null) {
            val personName = acct.getDisplayName()


            Toast.makeText(context, "Nom" + personName, Toast.LENGTH_SHORT).show()
        }

        Toast.makeText(context, "OK", Toast.LENGTH_SHORT).show()




        this.rootView = inflater.inflate(R.layout.fragment_profile, container, false)


        val backButton = this.rootView.findViewById<ImageButton>(R.id.back_button)
        backButton.setOnClickListener { this.onBack() }

        val uploadButton = this.rootView.findViewById<ImageButton>(R.id.upload_button)
        uploadButton.setOnClickListener { this.onUpload() }

        val saveButton = this.rootView.findViewById<Button>(R.id.save_button)
        saveButton.setOnClickListener { this.onSave() }


        val changePasswordItem = this.rootView.findViewById<LinearLayout>(R.id.change_password_item)
        changePasswordItem.setOnClickListener { }

        val removeAccountItem = this.rootView.findViewById<LinearLayout>(R.id.remove_account_item)
        removeAccountItem.setOnClickListener {
            firebaseAuth.currentUser?.delete()
                ?.addOnSuccessListener {
                    Toast.makeText(context, "delete().addOnSuccessListener()", Toast.LENGTH_LONG)
                    parentActivity.displayLogin()
                }?.addOnFailureListener {
                    Toast.makeText(context, "delete().addOnFailureListener()", Toast.LENGTH_LONG)
                }
        }

        val logoutItem = this.rootView.findViewById<LinearLayout>(R.id.logout_item)
        logoutItem.setOnClickListener {
            firebaseAuth.signOut()
            parentActivity.displayLogin()
        }

        val notificationSwitch = this.rootView.findViewById<Switch>(R.id.notification_switch)
        notificationSwitch.setOnClickListener { }

        return this.rootView
    }

    private fun onBack() {

    }

    private fun onUpload() {

    }

    private fun onSave() {

    }

}