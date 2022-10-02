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
import androidx.recyclerview.widget.ListAdapter
import com.example.spacebunsadminapp.R
import com.example.spacebunsadminapp.data.NewOrdersViewModel
import com.example.spacebunsadminapp.databinding.FragmentNewOrderBinding
import com.example.spacebunsadminapp.util.NewOrdersAdapter
import kotlinx.coroutines.launch

class NewOrderFragment : Fragment() {

    private lateinit var binding: FragmentNewOrderBinding
    private val nav by lazy { findNavController() }
    private val vm: NewOrdersViewModel by activityViewModels()

    private lateinit var adapter: NewOrdersAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentNewOrderBinding.inflate(inflater, container, false)

        adapter = NewOrdersAdapter{holder, order ->
            holder.binding.root.setOnClickListener {
                nav.navigate(R.id.orderDetailsFragment, bundleOf("id" to order.orderId))
            }
        }

        binding.rvNewOrders.adapter = adapter
        binding.rvNewOrders.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        vm.getAll().observe(viewLifecycleOwner){
            adapter.submitList(it)
            binding.txtCountNewOrders.text = "${it.size} order(s)"
        }


        return binding.root
    }
}