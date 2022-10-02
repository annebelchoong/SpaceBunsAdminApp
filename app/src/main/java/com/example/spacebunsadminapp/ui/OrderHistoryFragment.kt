package com.example.spacebunsadminapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.spacebunsadminapp.R
import com.example.spacebunsadminapp.data.OrderHistoryViewModel
import com.example.spacebunsadminapp.databinding.FragmentOrderHistoryBinding
import com.example.spacebunsadminapp.util.OrderHistoryAdapter


class OrderHistoryFragment : Fragment() {

    private lateinit var binding: FragmentOrderHistoryBinding
    private val nav by lazy { findNavController() }
    private val vm: OrderHistoryViewModel by activityViewModels()

    private lateinit var  adapter: OrderHistoryAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentOrderHistoryBinding.inflate(inflater, container, false)

        binding.btnInsert.setOnClickListener { nav.navigate(R.id.insertOrderFragment) }

        adapter = OrderHistoryAdapter { holder, order ->
            holder.binding.root.setOnClickListener{
                nav.navigate(R.id.orderDetailsFragment, bundleOf("id" to order.orderId))
            }
        }

        binding.rvOrders.adapter = adapter
        binding.rvOrders.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        vm.getAll().observe(viewLifecycleOwner){
            adapter.submitList(it)
            binding.txtCount.text = "${it.size} order(s)"
        }
        return binding.root
    }
}