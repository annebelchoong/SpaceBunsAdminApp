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
import com.example.spacebunsadminapp.data.OrderViewModel
import com.example.spacebunsadminapp.databinding.FragmentOrdersBinding
import com.example.spacebunsadminapp.util.OrderAdapter
import kotlinx.coroutines.launch

class OrdersFragment : Fragment() {

    private lateinit var binding: FragmentOrdersBinding
    private val nav by lazy { findNavController() }
    private val vm: OrderViewModel by activityViewModels()

    private lateinit var  adapter: OrderAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentOrdersBinding.inflate(inflater, container, false)

//        binding.btnInsert.setOnClickListener { nav.navigate(R.id.) }

        adapter = OrderAdapter {holder, order ->
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