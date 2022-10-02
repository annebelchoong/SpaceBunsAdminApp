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
import com.example.spacebunsadminapp.databinding.FragmentOrdersBinding
import com.example.spacebunsadminapp.util.OrderStatusAdapter
import kotlinx.coroutines.launch


class OrdersFragment : Fragment() {

    private lateinit var binding: FragmentOrdersBinding
    private val nav by lazy { findNavController() }
    private val vm: OrderStatusViewModel by activityViewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentOrdersBinding.inflate(inflater, container, false)

        val adapter = OrderStatusAdapter{ holder, order ->
            holder.binding.root.setOnClickListener{
                nav.navigate(R.id.orderStatusDetailsFragment, bundleOf("id" to order.id))
            }
        }


        binding.rv.adapter = adapter


        lifecycleScope.launch {
            val orderStatus = vm.getAll()
            adapter.submitList(orderStatus)
            binding.txtCountOrders.text="${orderStatus.size} Record(s)"
        }

        return binding.root
    }

}