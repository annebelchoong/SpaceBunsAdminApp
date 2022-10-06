package com.example.spacebunsadminapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.spacebunsadminapp.data.OrderStatusViewModel
import com.example.spacebunsadminapp.data.Orders
import com.example.spacebunsadminapp.data.OrdersViewModel
import com.example.spacebunsadminapp.databinding.FragmentOrderDetailsBinding
import com.example.spacebunsadminapp.util.OrderDetailsAdapter
import kotlinx.coroutines.launch
import java.text.DecimalFormat

class OrderDetailsFragment:Fragment() {
    private lateinit var binding: FragmentOrderDetailsBinding
    private lateinit var binding2: FragmentOrderDetailsBinding
    private val nav by lazy { findNavController() }
    private val vmO: OrderStatusViewModel by activityViewModels()
    private val vm: OrdersViewModel by activityViewModels()

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
            binding.edtTotal.text = "RM ${"%.2f".format(orders?.totalPrice)}"

            when(orders.orderStatusId){
                "D" -> binding.txtOrderStatus.text = "Delivered"
                "C" -> binding.txtOrderStatus.text = "Cancelled"
            }
        }


        val adapter = OrderDetailsAdapter()
        binding.rvOrderDetails.adapter = adapter

        lifecycleScope.launch{
            val orderD = vmO.getOrderDetails(id)
            adapter.submitList(orderD)
//            binding.txtCount.text = "${orderD.size} Orders(s)"

        }

        return binding.root
    }

}