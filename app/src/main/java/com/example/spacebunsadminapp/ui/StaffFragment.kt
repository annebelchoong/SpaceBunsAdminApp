package com.example.spacebunsadminapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.spacebunsadminapp.R
import com.example.spacebunsadminapp.data.StaffViewModal
import com.example.spacebunsadminapp.databinding.FragmentStaffBinding
import com.example.spacebunsadminapp.util.StaffAdapter

class StaffFragment : Fragment() {
    private lateinit var binding: FragmentStaffBinding
    private val nav by lazy { findNavController() }
    private val vm: StaffViewModal by activityViewModels()

    private lateinit var adapter: StaffAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStaffBinding.inflate(inflater, container, false)

        binding.fabtnAddVoucher.setOnClickListener { nav.navigate(R.id.staffInsertFragment) }

        adapter = VoucherAdapter { holder, staff ->
            holder.binding.root.setOnClickListener {
                nav.navigate(R.id.userUpdateFragment, bundleOf("staffId" to staff.staffId))
            }
        }
        binding.rvVouchers.adapter = adapter
        binding.rvVouchers.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )

        // -----------------------------------------------------------------------------------------

        val arrayAdapter =
            ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spnVoucherAttributes.adapter = arrayAdapter

        val vouchers = vm.getStaffAttributes()
        arrayAdapter.add("All")
        arrayAdapter.addAll(staffs)

        // TODO: Get all
        vm.getAll().observe(viewLifecycleOwner) {
            adapter.submitList(it)
            binding.txtCount.text = "${it.size} staff(s)"
        }

        return binding.root
    }
}