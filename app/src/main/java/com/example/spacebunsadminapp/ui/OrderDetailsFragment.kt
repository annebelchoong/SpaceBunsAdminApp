package com.example.spacebunsadminapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.spacebunsadminapp.databinding.FragmentOrderDetailsBinding

class OrderDetailsFragment:Fragment() {
    private lateinit var binding: FragmentOrderDetailsBinding
    private val nav by lazy { findNavController() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentOrderDetailsBinding.inflate(inflater, container, false)

        binding.btnUpdateStatus.setOnClickListener {  }

        return binding.root
    }
}