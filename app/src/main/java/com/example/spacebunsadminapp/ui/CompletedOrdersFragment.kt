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
import com.example.spacebunsadminapp.data.CompleteOrdersViewModel
import com.example.spacebunsadminapp.databinding.FragmentCompleteOrdersBinding
import com.example.spacebunsadminapp.util.CompleteOrdersAdapter


class CompletedOrdersFragment : Fragment() {

    private lateinit var binding: FragmentCompleteOrdersBinding
    private val nav by lazy { findNavController() }
    private val vm: CompleteOrdersViewModel by activityViewModels()

    private lateinit var  adapter: CompleteOrdersAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCompleteOrdersBinding.inflate(inflater, container, false)

        binding.btnInsert.setOnClickListener { nav.navigate(R.id.insertOrderFragment) }

        adapter = CompleteOrdersAdapter { holder, order ->
            holder.binding.root.setOnClickListener{
                nav.navigate(R.id.orderDetailsFragment, bundleOf("id" to order.orderId))
            }
        }

        binding.rvNewOrders.adapter = adapter
        binding.rvNewOrders.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        vm.getAll().observe(viewLifecycleOwner){
            adapter.submitList(it)
            binding.txtCount.text = "${it.size} order(s)"
        }
        return binding.root
    }
}