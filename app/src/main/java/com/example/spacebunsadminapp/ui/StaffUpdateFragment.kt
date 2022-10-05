package com.example.spacebunsadminapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.spacebunsadminapp.R
import com.example.spacebunsadminapp.data.StaffViewModal
import com.example.spacebunsadminapp.databinding.FragmentStaffUpdateBinding

class StaffUpdateFragment : Fragment() {
    private lateinit var binding: FragmentStaffUpdateBinding
    private val nav by lazy { findNavController() }
    private val vm: StaffViewModal by activityViewModels()
    private val voucherId by lazy { arguments?.getString("voucherId") ?: "" }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStaffUpdateBinding.inflate(inflater, container, false)

        reset()
        binding.btnReset.setOnClickListener { reset() }
        binding.btnSubmit.setOnClickListener { submit() }
        binding.btnDelete.setOnClickListener { delete() }

        return binding.root
    }

    private fun reset() {
        // TODO: Get
        val v = vm.get(voucherId)

        if (v == null) {
            nav.navigateUp()
            return
        }

        binding.txtVoucherIdFixed.text = v.voucherId
        binding.edtVoucherCode.setText(v.voucherCode)
        binding.edtDiscountPercentage.setText(v.discountPercentage.toString())

        binding.edtVoucherCode.requestFocus()
    }

    private fun submit() {
        val v = Voucher(
            voucherId = binding.txtVoucherIdFixed.text.toString().trim(),
            voucherCode = binding.edtVoucherCode.text.toString().trim().uppercase(),
            discountPercentage = binding.edtDiscountPercentage.text.toString().toDoubleOrNull()
                ?: 0.00,
        )

        val err = vm.validate(v, false)
        if (err != "") {
            errorDialog(err)
            return
        }

        vm.set(v)

        nav.navigateUp()
    }

//    private fun delete() {
//        // TODO: Delete
//        vm.delete(voucherId)
//
//        nav.navigateUp()
//    }

    private fun delete() {
        vm.delete(voucherId)

        nav.navigateUp()
//        Navigation.findNavController(binding.root).popBackStack(R.id.vouchersFragment, false)
    }
}