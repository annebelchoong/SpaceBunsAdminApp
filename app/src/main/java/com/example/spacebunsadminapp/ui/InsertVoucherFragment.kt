package com.example.spacebunsadminapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.spacebunsadminapp.data.Voucher
import com.example.spacebunsadminapp.data.VoucherViewModel
import com.example.spacebunsadminapp.databinding.FragmentInsertVoucherBinding
import com.example.spacebunsadminapp.util.errorDialog

class InsertVoucherFragment : Fragment() {
    private lateinit var binding: FragmentInsertVoucherBinding
    private val nav by lazy { findNavController() }
    private val vm: VoucherViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInsertVoucherBinding.inflate(inflater, container, false)

        reset()
        binding.btnReset.setOnClickListener { reset() }
        binding.btnSubmit.setOnClickListener { submit() }

        return binding.root
    }

    private fun reset() {
        binding.edtVoucherId.text.clear()
        binding.edtVoucherCode.text.clear()
        binding.edtDiscountPercentage.text.clear()
        binding.edtVoucherId.requestFocus()
    }

    private fun submit() {
        val v = Voucher(
            voucherId = binding.edtVoucherId.text.toString().trim(),
            voucherCode = binding.edtVoucherCode.text.toString().trim().uppercase(),
            discountPercentage = binding.edtDiscountPercentage.text.toString().toDoubleOrNull()
                ?: 0.00,
        )

        val err = vm.validate(v)
        if (err != "") {
            errorDialog(err)
            return
        }

        // TODO: Set (insert)
        vm.set(v)

        nav.navigateUp()
    }
}

