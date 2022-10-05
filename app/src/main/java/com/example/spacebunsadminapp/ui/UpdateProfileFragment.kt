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
import com.example.spacebunsadminapp.data.Customer
import com.example.spacebunsadminapp.data.CustomerViewModel
import com.example.spacebunsadminapp.data.Staff
import com.example.spacebunsadminapp.data.StaffViewModal
import com.example.spacebunsadminapp.databinding.FragmentUpdateProfileBinding
import com.example.spacebunsadminapp.util.cropToBlob
import com.example.spacebunsadminapp.util.errorDialog
import java.text.SimpleDateFormat
import java.util.*


class UpdateProfileFragment : Fragment() {
    private lateinit var binding: FragmentUpdateProfileBinding
    private val nav by lazy { findNavController() }
    private val vm: StaffViewModal by activityViewModels()
    private val staffId by lazy { arguments?.getString("staffId") ?: "" }
    private val formatter = SimpleDateFormat("dd MMMM yyyy '-' hh:mm:ss a", Locale.getDefault())

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            binding.imgUser.setImageURI(it.data?.data)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        binding = FragmentUpdateProfileBinding.inflate(inflater, container, false)

        reset()
        binding.imgUser.setOnClickListener  { select() }
        binding.btnReset.setOnClickListener  { reset()  }
        binding.btnSave.setOnClickListener { submit() }

        return binding.root
    }

    private fun select() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        launcher.launch(intent)
    }

    private fun reset() {
        val s = vm.get(staffId)

        if (s == null) {
            nav.navigateUp()
            return
        }



        //binding.imgUser.setImageBlob(c.photo)
        binding.edtName.requestFocus()

        binding.edtName.setText(s.staffName)
        binding.edtPhone.setText(s.staffPhone)
        binding.edtAddress.setText(s.staffAddress)
    }

    private fun submit() {
        val s = Staff(
            staffName = binding.edtName.text.toString().trim(),
            staffPhone = binding.edtPhone.text.toString(),
            staffAddress = binding.edtAddress.text.toString().trim(),
            staffPhoto = binding.imgUser.cropToBlob(300,300)
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
        vm.delete(staffId)
        nav.navigateUp()
    }
}