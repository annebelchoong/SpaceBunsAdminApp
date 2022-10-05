package com.example.spacebunsadminapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.spacebunsadminapp.R
import com.example.spacebunsadminapp.data.Staff
import com.example.spacebunsadminapp.data.StaffViewModal
import com.example.spacebunsadminapp.databinding.FragmentStaffInsertBinding
import com.example.spacebunsadminapp.util.errorDialog

class StaffInsertFragment : Fragment() {
    private lateinit var binding: FragmentStaffInsertBinding
    private val nav by lazy { findNavController() }
    private val vm: StaffViewModal by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStaffInsertBinding.inflate(inflater, container, false)

        reset()
        binding.btnReset.setOnClickListener { reset() }
        binding.btnSubmit.setOnClickListener { submit() }

        return binding.root
    }

    private fun reset() {
        binding.edtStaffIdInsert.text.clear()
        binding.edtStaffNameInsert.text.clear()
        binding.edtEmailInsert.text.clear()
        binding.edtSalaryInsert.text.clear()
        binding.edtStaffIdInsert.requestFocus()
    }

    private fun submit() {
        val s = Staff(
            staffId = binding.edtStaffIdInsert.text.toString().trim(),
            staffName = binding.edtStaffNameInsert.text.toString().trim(),
            staffEmail = binding.edtEmailInsert.text.toString().trim(),
            salary = binding.edtSalaryInsert.text.toString().toDoubleOrNull()
                ?: 0.00,
        )

        val err = vm.validate(s)
        if (err != "") {
            errorDialog(err)
            return
        }

        // TODO: Set (insert)
        vm.set(s)

        nav.navigateUp()
    }
}