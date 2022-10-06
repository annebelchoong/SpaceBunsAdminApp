package com.example.spacebunsadminapp.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.spacebunsadminapp.R
import com.example.spacebunsadminapp.data.Staff
import com.example.spacebunsadminapp.data.StaffViewModal
import com.example.spacebunsadminapp.databinding.FragmentStaffInsertBinding
import com.example.spacebunsadminapp.util.cropToBlob
import com.example.spacebunsadminapp.util.errorDialog

class StaffInsertFragment : Fragment() {
    private lateinit var binding: FragmentStaffInsertBinding
    private val nav by lazy { findNavController() }
    private val vm: StaffViewModal by activityViewModels()

    // TODO: Get content launcher
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            binding.imgStaffPhotoInsert.setImageURI(it.data?.data)  // result is path
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStaffInsertBinding.inflate(inflater, container, false)

        reset()
        binding.imgStaffPhotoInsert.setOnClickListener { select() }
        binding.btnReset.setOnClickListener { reset() }
        binding.btnSubmit.setOnClickListener { submit() }

        return binding.root
    }

    private fun select() {
        // TODO: Select file
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        launcher.launch(intent)
    }

    private fun reset() {
        binding.edtStaffIdInsert.text.clear()
        binding.edtStaffNameInsert.text.clear()
        binding.edtEmailInsert.text.clear()
        binding.edtStaffPhoneInsert.text.clear()
        binding.edtSalaryInsert.text.clear()
        binding.imgStaffPhotoInsert.setImageDrawable(null)
        binding.edtStaffIdInsert.requestFocus()
    }

    private fun submit() {
        val s = Staff(
            staffId = binding.edtStaffIdInsert.text.toString().trim(),
            staffName = binding.edtStaffNameInsert.text.toString().trim(),
            staffEmail = binding.edtEmailInsert.text.toString().trim(),
            staffPhone = binding.edtStaffPhoneInsert.text.toString().trim(),
            salary = binding.edtSalaryInsert.text.toString().toDoubleOrNull()
                ?: 0.00,
            // TODO: Photo
            staffPhoto = binding.imgStaffPhotoInsert.cropToBlob(300, 300)
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