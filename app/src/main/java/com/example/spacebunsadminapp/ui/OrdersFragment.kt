package com.example.spacebunsadminapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.spacebunsadminapp.R
import com.example.spacebunsadminapp.databinding.FragmentOrdersBinding


class OrdersFragment : Fragment() {

    private lateinit var binding: FragmentOrdersBinding
    private val nav by lazy { findNavController() }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentOrdersBinding.inflate(inflater, container, false)

        binding.vCardNewOrder.setOnClickListener{ nav.navigate(R.id.newOrderFragment) }
        binding.vCardCompletedOrders.setOnClickListener{ nav.navigate(R.id.completeOrdersFragment) }
        binding.vCardCancelOrder.setOnClickListener { nav.navigate(R.id.cancelledOrdersFragment) }
        return binding.root
    }

}