package com.ksainthi.inance.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.SwitchCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.ksainthi.inance.presentation.MainActivity
import com.ksainthi.inance.R

class FragmentProfile : Fragment() {

    private lateinit var rootView: View

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

        }

        val logoutItem = this.rootView.findViewById<LinearLayout>(R.id.logout_item)
        logoutItem.setOnClickListener {

        }

        val notificationSwitch = this.rootView.findViewById<SwitchCompat>(R.id.notification_switch)
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