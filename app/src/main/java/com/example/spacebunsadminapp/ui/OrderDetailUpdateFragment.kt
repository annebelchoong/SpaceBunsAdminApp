package com.example.spacebunsadminapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.spacebunsadminapp.R
import com.example.spacebunsadminapp.data.OrderStatusViewModel
import com.example.spacebunsadminapp.data.Orders
import com.example.spacebunsadminapp.data.OrdersViewModel
import com.example.spacebunsadminapp.databinding.FragmentOrderDetailUpdateBinding
import com.example.spacebunsadminapp.databinding.FragmentOrderDetailsBinding
import com.example.spacebunsadminapp.util.OrderDetailsAdapter
import kotlinx.coroutines.launch
import java.text.DecimalFormat

class OrderDetailUpdateFragment : Fragment() {
    private lateinit var binding: FragmentOrderDetailUpdateBinding
    private val nav by lazy { findNavController() }
    private val vm: OrdersViewModel by activityViewModels()
    private val vmO: OrderStatusViewModel by activityViewModels()

    private val id by lazy {arguments?.getString("orderId","")?: ""}
    private val formatter = DecimalFormat("0.00")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentOrderDetailUpdateBinding.inflate(inflater, container, false)

        lifecycleScope.launch {
            val orders = vm.get(id)!!
//        if (orders != null) {
            binding.edtOrderId2.text = "${orders.orderId}"
            binding.edtDate2.text = "${orders?.date}"
            binding.edtAddress2.text = "${orders?.address}"
            binding.edtPaymentMethod2.text = "${orders?.paymentMethod}"
            binding.edtTotal2.text = "RM ${"%.2f".format(orders?.totalPrice)}"
//        }

        }

//        val orders = vm.get(id)!!
//        }

//        when(orders.orderStatus){
//            "Processing" -> binding.spnNewStatus.selectedItemPosition == 0
//            "Delivered" -> binding.spnNewStatus.selectedItemPosition == 1
//            "Cancelled" -> binding.spnNewStatus.selectedItemPosition == 2
//        }

        binding.btnUpdateStatus2.setOnClickListener { update() }

        val adapter = OrderDetailsAdapter()
        binding.rvOrderDetails2.adapter = adapter

        lifecycleScope.launch{
            val orderD = vmO.getOrderDetails(id)
            adapter.submitList(orderD)

        }


        return binding.root
    }

    private fun update() {
        val o = Orders()

        when(binding.spnNewStatus2.selectedItem as String){
            "Processing" -> o.orderStatusId = "P"
            "Delivered" -> o.orderStatusId = "D"
            "Cancelled" -> o.orderStatusId = "C"
        }

        o.orderId = binding.edtOrderId2.text.toString().trim()
        o.totalPrice = binding.edtTotal2.text.toString().toDoubleOrNull() ?: 0.0
        o.address = binding.edtAddress2.text.toString().trim()
        o.paymentMethod = binding.edtPaymentMethod2.text.toString().trim()

        vm.set(o)

        nav.navigateUp()

    }
}