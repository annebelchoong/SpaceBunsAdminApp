package com.example.spacebunsadminapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.spacebunsadminapp.data.CompleteOrdersViewModel
import com.example.spacebunsadminapp.data.Orders
import com.example.spacebunsadminapp.databinding.FragmentInsertOrderBinding


class InsertOrderFragment : Fragment() {

    private lateinit var binding: FragmentInsertOrderBinding
    private val nav by lazy { findNavController() }
    private val vm: CompleteOrdersViewModel by activityViewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentInsertOrderBinding.inflate(inflater, container, false)

        binding.btnReset.setOnClickListener { reset() }
        binding.btnNewInsert.setOnClickListener { insert() }
        return binding.root
    }

    // for checkout purpose
    private fun insert() {
        val o = Orders(
            orderId = binding.edtNewOrderId.text.toString().trim(),
//            customerId = binding.edtNewCustomerId.text.toString().trim(),
            address = binding.edtNewAddress.text.toString().trim(),
//            orderStatus = binding.spnStatus.selectedItem as String,
            paymentMethod = binding.spnPaymentMethod.selectedItem as String,
            totalPrice = binding.edtTotalPrice.text.toString().toDoubleOrNull() ?: 0.00
        )

        vm.set(o)

        nav.navigateUp()
    }

    private fun reset(){

    }
}