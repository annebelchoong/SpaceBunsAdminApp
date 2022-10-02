package com.example.spacebunsadminapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.spacebunsadminapp.R
import com.example.spacebunsadminapp.data.CompleteOrdersViewModel
import com.example.spacebunsadminapp.data.Orders
import com.example.spacebunsadminapp.databinding.FragmentOrderDetailsBinding
import com.example.spacebunsadminapp.util.CompleteOrdersAdapter
import com.example.spacebunsadminapp.util.OrderDetailsAdapter
import java.text.DecimalFormat

class OrderDetailsFragment:Fragment() {
    private lateinit var binding: FragmentOrderDetailsBinding
    private val nav by lazy { findNavController() }
    private val vm: CompleteOrdersViewModel by activityViewModels()

    private lateinit var adapter: OrderDetailsAdapter // for products
    private val id by lazy {arguments?.getString("id","")?: ""}
    private val formatter = DecimalFormat("0.00")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentOrderDetailsBinding.inflate(inflater, container, false)


        val orders = vm.get(id)!!

        binding.edtOrderId.text = orders.orderId
        binding.edtDate.text = orders.date.toString()
        binding.edtAddress.text = orders.address
        binding.edtPaymentMethod.text = orders.paymentMethod
        binding.edtTotal.text = "RM " + formatter.format(orders.totalPrice)
//        when(orders.orderStatus){
//            "Processing" -> binding.spnNewStatus.selectedItemPosition == 0
//            "Delivered" -> binding.spnNewStatus.selectedItemPosition == 1
//            "Cancelled" -> binding.spnNewStatus.selectedItemPosition == 2
//        }

        binding.btnUpdateStatus.setOnClickListener { update() }

        return binding.root
    }

    private fun update() {
        val o = Orders(
            orderStatus = binding.spnNewStatus.selectedItem as String,
            orderId = binding.edtOrderId.text.toString().trim(),
            totalPrice = binding.edtTotal.text.toString().toDoubleOrNull() ?: 0.0,
            address = binding.edtAddress.text.toString().trim(),
            paymentMethod = binding.edtPaymentMethod.text.toString().trim()
        )

        vm.set(o)

        nav.navigateUp()

    }
}