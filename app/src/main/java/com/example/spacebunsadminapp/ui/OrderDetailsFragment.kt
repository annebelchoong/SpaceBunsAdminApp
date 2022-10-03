package com.example.spacebunsadminapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.spacebunsadminapp.data.Orders
import com.example.spacebunsadminapp.data.OrdersViewModel
import com.example.spacebunsadminapp.databinding.FragmentOrderDetailsBinding
import com.example.spacebunsadminapp.util.OrderDetailsAdapter
import kotlinx.coroutines.launch
import java.text.DecimalFormat

class OrderDetailsFragment:Fragment() {
    private lateinit var binding: FragmentOrderDetailsBinding
    private val nav by lazy { findNavController() }
    private val vm:OrdersViewModel by activityViewModels()

    private lateinit var adapter: OrderDetailsAdapter // for products
    private val id by lazy {arguments?.getString("orderId","")?: ""}
    private val formatter = DecimalFormat("0.00")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentOrderDetailsBinding.inflate(inflater, container, false)

        lifecycleScope.launch {
            val orders = vm.get(id)!!
//        if (orders != null) {
            binding.edtOrderId.text = "${orders.orderId}"
            binding.edtDate.text = "${orders?.date}"
            binding.edtAddress.text = "${orders?.address}"
            binding.edtPaymentMethod.text = "${orders?.paymentMethod}"
            binding.edtTotal.text = "RM ${orders?.totalPrice}"
            when(orders.orderStatusId){
                "D" -> binding.txtOrderStatus.text = "Delivered"
                "C" -> binding.txtOrderStatus.text = "Cancelled"
            }
//        }

        }

//        val orders = vm.get(id)!!
//        }

//        when(orders.orderStatus){
//            "Processing" -> binding.spnNewStatus.selectedItemPosition == 0
//            "Delivered" -> binding.spnNewStatus.selectedItemPosition == 1
//            "Cancelled" -> binding.spnNewStatus.selectedItemPosition == 2
//        }

        return binding.root
    }

}