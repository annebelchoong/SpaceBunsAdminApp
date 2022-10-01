package com.example.spacebunsadminapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.spacebunsadminapp.R
import com.example.spacebunsadminapp.data.SpaceBunsViewModel
import com.example.spacebunsadminapp.databinding.FragmentProductsListBinding
import com.example.spacebunsadminapp.util.ProductAdapter


class ProductListFragment : Fragment() {

    private lateinit var binding: FragmentProductsListBinding
    private val nav by lazy { findNavController() }
    private val vm: SpaceBunsViewModel by activityViewModels()

    private lateinit var adapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductsListBinding.inflate(inflater, container, false)

        binding.btnInsert.setOnClickListener { nav.navigate(R.id.productInsertFragment) }

        adapter = ProductAdapter { holder, product ->

            // Item click -> navigate to UpdateFragment (id)
            holder.binding.root.setOnClickListener {
                nav.navigate(R.id.productUpdateFragment, bundleOf("id" to product.id))
            }
            // Delete button click -> delete record
            holder.binding.btnDelete.setOnClickListener {
                delete(product.id)
            }
        }
        binding.rv.adapter = adapter
        binding.rv.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        // TODO: Get all
        vm.getAll().observe(viewLifecycleOwner) {
            adapter.submitList(it)
            binding.txtCount.text = "${it.size} records(s)"
        }

        return binding.root
    }

    private fun delete(id: String) {
        // TODO: Delete
        vm.delete(id)
    }
}