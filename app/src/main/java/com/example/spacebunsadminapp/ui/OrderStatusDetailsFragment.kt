package com.example.spacebunsadminapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.spacebunsadminapp.R
import com.example.spacebunsadminapp.data.OrderStatusViewModel
import com.example.spacebunsadminapp.databinding.FragmentOrderStatusDetailsBinding
import com.example.spacebunsadminapp.databinding.FragmentOrdersBinding
import com.example.spacebunsadminapp.util.OrdersAdapter
import kotlinx.coroutines.launch

class OrderStatusDetailsFragment : Fragment() {
    private lateinit var binding: FragmentOrderStatusDetailsBinding
    private val nav by lazy { findNavController() }
    private val vm: OrderStatusViewModel by activityViewModels()

    private val id by lazy { arguments?.getString("id") ?: "" }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentOrderStatusDetailsBinding.inflate(inflater, container, false)

        val adapter = OrdersAdapter{holder, orders ->
            holder.binding.root.setOnClickListener {
                if (orders.orderStatusId == "P"){
                    nav.navigate(R.id.orderDetailUpdateFragment, bundleOf("orderId" to orders.orderId))
                } else{
                    nav.navigate(R.id.orderDetailsFragment, bundleOf("orderId" to orders.orderId))

                }
            }
        }
        binding.rvNewOrders.adapter = adapter
        binding.rvNewOrders.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        lifecycleScope.launch {
            val orderStatus = vm.get(id)!!
            binding.lblTitle.text = "${orderStatus.name}"

            val fruits = vm.getOrders(id)
            adapter.submitList(fruits)
            binding.txtCount2.text = "${fruits.size} Orders(s)"
        }

        return binding.root
    }
}