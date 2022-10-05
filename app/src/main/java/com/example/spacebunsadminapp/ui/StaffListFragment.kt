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
import com.example.spacebunsadminapp.data.StaffViewModal
import com.example.spacebunsadminapp.databinding.FragmentStaffListBinding
import com.example.spacebunsadminapp.util.StaffAdapter

class StaffListFragment : Fragment() {
    private lateinit var binding: FragmentStaffListBinding
    private val nav by lazy { findNavController() }
    private val vm: StaffViewModal by activityViewModels()

    private lateinit var adapter: StaffAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        binding = FragmentStaffListBinding.inflate(inflater, container, false)

        binding.btnInsert.setOnClickListener { nav.navigate(R.id.staffInsertFragment) }
        binding.btnDeleteAll.setOnClickListener { deleteAll() }

        adapter = StaffAdapter { holder, staff ->
            // Item click -> navigate to UpdateFragment (id)
            holder.binding.root.setOnClickListener {
                nav.navigate(R.id.staffUpdateFragment, bundleOf("id" to staff.staffId))
            }
            // Delete button click -> delete record
            holder.binding.btnDelete.setOnClickListener {
                delete(staff.staffId)
            }
        }
        binding.rv.adapter = adapter
        binding.rv.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        // TODO: Get all
        vm.getAll().observe(viewLifecycleOwner) {
            adapter.submitList(it)
            binding.txtCount.text = "${it.size} record(s)"
        }

        return binding.root
    }

    private fun deleteAll() {
        // TODO: Delete all
        vm.deleteAll()
    }

    private fun delete(id: String) {
        // TODO: Delete
        vm.delete(id)
    }
}