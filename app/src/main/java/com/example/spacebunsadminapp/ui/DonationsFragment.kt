package com.example.spacebunsadminapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.spacebunsadminapp.databinding.FragmentDonationsBinding

class DonationsFragment : Fragment() {
    private lateinit var binding: FragmentDonationsBinding
    private val nav by lazy { findNavController() }
    private var progr = 70

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDonationsBinding.inflate(inflater, container, false)
        updateProgressBar();
        return binding.root
    }

    private fun updateProgressBar() {
        binding.pbDonations.progress = progr
        binding.txtDonationProgress.text = progr.toString() + ".00%"
    }
}