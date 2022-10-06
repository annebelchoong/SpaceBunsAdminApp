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
import com.example.spacebunsadminapp.data.Customer
import com.example.spacebunsadminapp.data.CustomerViewModel
import com.example.spacebunsadminapp.databinding.FragmentCustomerInsertBinding
import com.example.spacebunsadminapp.util.cropToBlob
import com.example.spacebunsadminapp.util.errorDialog

class CustomerInsertFragment : Fragment() {
    private lateinit var binding: FragmentCustomerInsertBinding
    private val nav by lazy { findNavController() }
    private val vm: CustomerViewModel by activityViewModels()

    // TODO: Get content launcher
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            binding.imgCusPhoto.setImageURI(it.data?.data)  // result is path
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCustomerInsertBinding.inflate(inflater, container, false)

        reset()
        binding.imgCusPhoto.setOnClickListener { select() }
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
        binding.edtCusId.text.clear()
        binding.edtCusName.text.clear()
        binding.edtCusEmailInsert.text.clear()
        binding.edtCusPassword.text.clear()
        binding.edtCusPhoneInsert.text.clear()
        binding.edtCusAddress.text.clear()
        binding.imgCusPhoto.setImageDrawable(null)

        binding.edtCusId.requestFocus()
    }

    private fun submit() {
        val c = Customer(
            cusId = binding.edtCusId.text.toString().trim(),
            cusName = binding.edtCusName.text.toString().trim(),
            cusEmail = binding.edtCusEmailInsert.text.toString().trim(),
            cusPassword = binding.edtCusPassword.text.toString().trim(),
            cusPhone = binding.edtCusPhoneInsert.text.toString().trim(),
            cusAddress = binding.edtCusAddress.text.toString().trim(),
            // TODO: Photo
            cusPhoto = binding.imgCusPhoto.cropToBlob(300,300)
        )

        val err = vm.validate(c)
        if (err != "") {
            errorDialog(err)
            return
        }

        // TODO: Set (insert)
        vm.set(c)

        nav.navigateUp()
    }
}