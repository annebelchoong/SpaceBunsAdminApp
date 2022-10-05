package com.example.spacebunsadminapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCustomerInsertBinding.inflate(inflater, container, false)

        reset()
        binding.btnReset.setOnClickListener { reset() }
        binding.btnSubmit.setOnClickListener { submit() }

        return binding.root
    }

    private fun reset() {
        binding.edtCusName.text.clear()
        binding.edtCusEmail.text.clear()
        binding.edtCusPassword.text.clear()
        binding.edtCusPhone.text.clear()
        binding.edtCusAddress.text.clear()
        binding.imgCusPhoto.setImageDrawable(null)

        binding.edtCusName.requestFocus()
    }

    private fun submit() {
        val c = Customer(
            cusId = binding.txtCusId.text.toString().trim(),
            cusName = binding.edtCusName.text.toString().trim(),
            cusEmail = binding.edtCusEmail.text.toString().trim(),
            cusPassword = binding.edtCusPassword.text.toString().trim(),
            cusPhone = binding.edtCusPhone.text.toString().trim(),
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