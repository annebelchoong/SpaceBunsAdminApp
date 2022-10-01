package com.example.spacebunsadminapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.spacebunsadminapp.databinding.FragmentVouchersBinding

class VouchersFragment : Fragment() {
    private lateinit var binding: FragmentVouchersBinding
    private val nav by lazy { findNavController() }
    private var progr = 70

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVouchersBinding.inflate(inflater, container, false)
        updateVoucherUsage();
        return binding.root
    }

    private fun updateVoucherUsage() {
//        binding.pbVoucher.progress = progr
//        binding.txtProgress.text = "$progr.00%"
    }
}