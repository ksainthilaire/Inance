package com.ksainthi.inance

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Switch

class ProfileFragment : Fragment() {

    private lateinit var rootView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        removeAccountItem.setOnClickListener {}

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