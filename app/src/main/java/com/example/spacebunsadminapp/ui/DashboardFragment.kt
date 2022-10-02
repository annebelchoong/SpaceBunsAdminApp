package com.example.spacebunsadminapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.spacebunsadminapp.R
import com.example.spacebunsadminapp.databinding.FragmentDashboardBinding

class Dashboard : Fragment() {
    private lateinit var binding: FragmentDashboardBinding
    private val nav by lazy { findNavController() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)

        binding.btnVouchersPage.setOnClickListener { nav.navigate(R.id.vouchersFragment) }
        binding.btnDonationsPage.setOnClickListener { nav.navigate(R.id.donationsFragment) }

        return binding.root
    }
}