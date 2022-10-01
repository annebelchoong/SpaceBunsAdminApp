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

//    private var progr = 70

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
//        arrayAdapter.add("All")
        arrayAdapter.addAll(vouchers)

        // TODO: Get all
        vm.getAll().observe(viewLifecycleOwner) {
            adapter.submitList(it)
            binding.txtVoucherCount.text = "${it.size} voucher(s)"
        }

//        updateVoucherUsage();
        return binding.root
    }

//    private fun sort(field: String) {
//        // TODO(26): Sort by [field] -> vm.sort(...)
//        val reverse = vm.sort(field)
//
//        // TODO(27): Remove icon -> all buttons
//        binding.btnId.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
//        binding.btnName.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
//        binding.btnPrice.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
//
//        // TODO(28): Set icon -> specific button
//        val res = if (reverse) R.drawable.ic_down else R.drawable.ic_up
//
//        when (field) {
//            "id" -> binding.btnId.setCompoundDrawablesWithIntrinsicBounds(0, 0, res, 0)
//            "name" -> binding.btnName.setCompoundDrawablesWithIntrinsicBounds(0, 0, res, 0)
//            "price" -> binding.btnPrice.setCompoundDrawablesWithIntrinsicBounds(0, 0, res, 0)
//        }
//    }


//    private fun updateVoucherUsage() {
//        binding.pbVoucher.progress = progr
//        binding.txtProgress.text = "$progr.00%"
//    }
}