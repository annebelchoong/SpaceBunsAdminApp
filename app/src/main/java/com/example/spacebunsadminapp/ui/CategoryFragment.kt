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
import com.example.spacebunsadminapp.data.CategoryViewModel
import com.example.spacebunsadminapp.databinding.FragmentCategoryBinding
import com.example.spacebunsadminapp.util.CategoryAdapter
import kotlinx.coroutines.launch

class CategoryFragment : Fragment() {
    private lateinit var binding: FragmentCategoryBinding
    private val nav by lazy { findNavController() }
    private val vm: CategoryViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)

        // -----------------------------------------------------------------------------------------
        //adapter for recycle view
        val adapter = CategoryAdapter() { holder, category ->
            holder.binding.root.setOnClickListener {
                nav.navigate(R.id.categoryDetailsFragment, bundleOf("id" to category.id))
            }
        }
        binding.rv.adapter = adapter
        binding.rv.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        // -----------------------------------------------------------------------------------------

        //  Load categories data into recycler view -> launch block
        lifecycleScope.launch {
            val categories = vm.getAll()
            adapter.submitList(categories)
            binding.txtCount.text = "${categories.size} Record(s)"
        }

        // -----------------------------------------------------------------------------------------

        return binding.root
    }
}