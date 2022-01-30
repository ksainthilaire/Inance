package com.ksainthi.inance

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton


class MenuFragment : Fragment() {

    private lateinit var currentTab: Fragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_menu, container, false)

        val createButton = rootView.findViewById<ImageButton>(R.id.create_button)

        createButton.setOnClickListener { }

        val homeTab = rootView.findViewById<ImageButton>(R.id.home_tab)
        val statsTab = rootView.findViewById<ImageButton>(R.id.stats_tab)
        val walletTab = rootView.findViewById<ImageButton>(R.id.wallet_tab)
        val profileTab = rootView.findViewById<ImageButton>(R.id.profile_tab)

        homeTab.setOnClickListener { }
        statsTab.setOnClickListener { }
        walletTab.setOnClickListener { }
        profileTab.setOnClickListener { }


        //this.activeTab()

        return rootView
    }

    private fun  activeTab() {
        when (this.currentTab) {
            RegisterFragment() -> {

            }
        }
    }

}