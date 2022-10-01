package com.example.spacebunsadminapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.spacebunsadminapp.R
import com.example.spacebunsadminapp.data.OrderViewModel
import com.example.spacebunsadminapp.databinding.FragmentOrdersBinding
import com.example.spacebunsadminapp.util.OrderAdapter

class OrdersFragment : Fragment() {

    private lateinit var binding: FragmentOrdersBinding
    private val nav by lazy { findNavController() }
    private val vm:OrderViewModel by activityViewModels()

    private lateinit var  adapter: OrderAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentOrdersBinding.inflate(inflater, container, false)
        return binding.root

    }

}