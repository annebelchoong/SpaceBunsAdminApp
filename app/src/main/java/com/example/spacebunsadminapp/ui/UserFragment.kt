package com.example.spacebunsadminapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.spacebunsadminapp.R
import com.example.spacebunsadminapp.data.Category
import com.example.spacebunsadminapp.data.UserViewModal
import com.example.spacebunsadminapp.databinding.FragmentProductsBinding
import com.example.spacebunsadminapp.databinding.FragmentUserBinding
import com.example.spacebunsadminapp.util.UserAdapter
import kotlinx.coroutines.launch

class UserFragment : Fragment() {
    private lateinit var binding: FragmentUserBinding
    private val nav by lazy { findNavController() }
    private val vm: UserViewModal by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentUserBinding.inflate(inflater, container, false)

        // Default search, filter and sort
        vm.search("")
        vm.filter("")
        sort("id")

        val adapter = UserAdapter() { holder, user ->
            holder.binding.root.setOnClickListener {
                nav.navigate(R.id.userDetailsFragment, bundleOf("id" to user.id))
            }
        }
        binding.rv.adapter = adapter
        binding.rv.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        // TODO(16): Load fruits data into recycler view
        vm.getResult().observe(viewLifecycleOwner) {
            adapter.submitList(it)
            binding.txtCount.text = "${it.size} Record(s)"
        }

        val arrayAdapter = ArrayAdapter<Category>(requireContext(), android.R.layout.simple_spinner_item)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spn.adapter = arrayAdapter //spinner

        // TODO(18): Load categories data into spinner -> launch block
        lifecycleScope.launch {
            // TODO
            val categories = vm.getCategories()
            arrayAdapter.add(Category("", "All"))
            arrayAdapter.addAll(categories)
        }

        // -----------------------------------------------------------------------------------------

        binding.sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(name: String) = true
            override fun onQueryTextChange(name: String): Boolean {
                // TODO(19): Search by [name] -> vm.search(...)
                vm.search(name)
                return true
            }
        })

        // -----------------------------------------------------------------------------------------

        binding.spn.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) = Unit
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // TODO(20): Filter by [categoryId] -> vm.filter(...)
                val category = binding.spn.selectedItem as Category
                vm.filter(category.id)
            }
        }

        // -----------------------------------------------------------------------------------------

        binding.btnId.setOnClickListener { sort("id") }
        binding.btnName.setOnClickListener { sort("name") }
        binding.btnEmail.setOnClickListener { sort("email") }

        // -----------------------------------------------------------------------------------------

        return binding.root
    }

    private fun sort(field: String) {
        // TODO(26): Sort by [field] -> vm.sort(...)
        val reverse = vm.sort(field) // TODO

        // TODO(27): Remove icon -> all buttons
        binding.btnId.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        binding.btnName.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        binding.btnEmail.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)

        // TODO(28): Set icon -> specific button
        val res = if (reverse) R.drawable.ic_down else R.drawable.ic_up

        when (field) {
            "id"    -> binding.btnId.setCompoundDrawablesWithIntrinsicBounds(0, 0, res, 0)
            "name"  -> binding.btnName.setCompoundDrawablesWithIntrinsicBounds(0, 0, res, 0)
            "email" -> binding.btnEmail.setCompoundDrawablesWithIntrinsicBounds(0, 0, res, 0)
        }
    }
}