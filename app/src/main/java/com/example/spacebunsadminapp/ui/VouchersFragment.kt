package com.example.spacebunsadminapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.spacebunsadminapp.R
import com.example.spacebunsadminapp.data.VoucherViewModel
import com.example.spacebunsadminapp.databinding.FragmentVouchersBinding
import com.example.spacebunsadminapp.util.VoucherAdapter
import kotlinx.coroutines.launch

class VouchersFragment : Fragment() {
    private lateinit var binding: FragmentVouchersBinding
    private val nav by lazy { findNavController() }
    private val vm: VoucherViewModel by activityViewModels()

    private lateinit var adapter: VoucherAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVouchersBinding.inflate(inflater, container, false)

        binding.fabtnAddVoucher.setOnClickListener { nav.navigate(R.id.insertVoucherFragment) }

        adapter = VoucherAdapter { holder, voucher ->
            holder.binding.root.setOnClickListener {
                nav.navigate(R.id.updateVoucherFragment, bundleOf("voucherId" to voucher.voucherId))
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

        val vouchers = vm.getVoucherAttributes()
        arrayAdapter.add("All")
        arrayAdapter.addAll(vouchers)

        // TODO: Get all
        vm.getAll().observe(viewLifecycleOwner) {
            adapter.submitList(it)
            binding.txtVoucherCount.text = "${it.size} voucher(s)"
        }

        return binding.root
    }

}