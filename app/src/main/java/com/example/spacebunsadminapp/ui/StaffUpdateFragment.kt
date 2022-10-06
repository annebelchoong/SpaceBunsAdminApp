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
import com.example.spacebunsadminapp.databinding.FragmentStaffUpdateBinding
import com.example.spacebunsadminapp.util.cropToBlob
import com.example.spacebunsadminapp.util.errorDialog
import com.example.spacebunsadminapp.util.setImageBlob
import kotlinx.coroutines.selects.select
import java.text.SimpleDateFormat
import java.util.*

class StaffUpdateFragment : Fragment() {
    private lateinit var binding: FragmentStaffUpdateBinding
    private val nav by lazy { findNavController() }
    private val vm: StaffViewModal by activityViewModels()

    private val staffId by lazy { arguments?.getString("staffId") ?: "" }
    private val formatter = SimpleDateFormat("dd MMMM yyyy '-' hh:mm:ss a", Locale.getDefault())

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            binding.imgStaffPhotoUpdate.setImageURI(it.data?.data)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStaffUpdateBinding.inflate(inflater, container, false)

        reset()
        binding.imgStaffPhotoUpdate.setOnClickListener  { select() }
        binding.btnReset.setOnClickListener { reset() }
        binding.btnSubmit.setOnClickListener { submit() }
        binding.btnDelete.setOnClickListener { delete() }

        return binding.root
    }

    private fun select() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        launcher.launch(intent)
    }

    private fun reset() {
        // TODO: Get
        val s = vm.get(staffId)

        if (s == null) {
            nav.navigateUp()
            return
        }

        binding.txtStaffId.text = s.staffId
        binding.edtStaffName.setText(s.staffName)
        binding.edtStaffEmail.setText(s.staffEmail)
        binding.edtStaffPhone.setText(s.staffPhone)
        binding.edtSalary.setText(s.salary.toString())

        // TODO: Load photo and date
        binding.imgStaffPhotoUpdate.setImageBlob(s.staffPhoto)
        binding.txtStaffDate.text = formatter.format(s.staffDate)

        binding.edtStaffName.requestFocus()
    }

    private fun submit() {
        val s = Staff(
            staffId = binding.txtStaffId.text.toString().trim(),
            staffName = binding.edtStaffName.text.toString().trim(),
            staffEmail = binding.edtStaffEmail.text.toString().trim(),
            staffPhone = binding.edtStaffPhone.text.toString().trim(),
            salary = binding.edtSalary.text.toString().toDoubleOrNull()
                ?: 0.00,
            // TODO: Photo
            staffPhoto = binding.imgStaffPhotoUpdate.cropToBlob(300,300)
        )

        val err = vm.validate(s, false)
        if (err != "") {
            errorDialog(err)
            return
        }

        vm.set(s)

        nav.navigateUp()
    }

    private fun delete() {
        // TODO: Delete
        vm.delete(staffId)

        nav.navigateUp()
    }

//    private fun delete() {
//        vm.delete(staffId)
//
//        nav.navigateUp()
////        Navigation.findNavController(binding.root).popBackStack(R.id.vouchersFragment, false)
//    }
}