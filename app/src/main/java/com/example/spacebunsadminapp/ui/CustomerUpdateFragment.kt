package com.example.spacebunsadminapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.spacebunsadminapp.R
import com.example.spacebunsadminapp.data.Customer
import com.example.spacebunsadminapp.data.CustomerViewModel
import com.example.spacebunsadminapp.data.Staff
import com.example.spacebunsadminapp.data.StaffViewModal
import com.example.spacebunsadminapp.databinding.FragmentCustomerUpdateBinding
import com.example.spacebunsadminapp.databinding.FragmentStaffUpdateBinding
import com.example.spacebunsadminapp.util.cropToBlob
import com.example.spacebunsadminapp.util.errorDialog
import com.example.spacebunsadminapp.util.setImageBlob
import java.text.SimpleDateFormat
import java.util.*

class CustomerUpdateFragment : Fragment() {
    private lateinit var binding: FragmentCustomerUpdateBinding
    private val nav by lazy { findNavController() }
    private val vm: CustomerViewModel by activityViewModels()
    private val cusId by lazy { arguments?.getString("cusId") ?: "" }
    private val formatter = SimpleDateFormat("dd MMMM yyyy '-' hh:mm:ss a", Locale.getDefault())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCustomerUpdateBinding.inflate(inflater, container, false)

        reset()
        binding.btnReset.setOnClickListener { reset() }
        binding.btnSubmit.setOnClickListener { submit() }
        binding.btnDelete.setOnClickListener { delete() }

        return binding.root
    }

    private fun reset() {
        // TODO: Get
        val c = vm.get(cusId)

        if (c == null) {
            nav.navigateUp()
            return
        }

        binding.txtCusId.text = c.cusId
        binding.edtCusName.setText(c.cusName)
        binding.edtCusEmail.setText(c.cusEmail)
        binding.edtCusPassword.setText(c.cusPassword)
        binding.edtCusPhone.setText(c.cusPhone)
        binding.edtCusAddress.setText(c.cusAddress)
        // TODO: Load photo and date
        binding.imgCusPhoto.setImageBlob(c.cusPhoto)
        binding.txtCusDate.text = formatter.format(c.date)

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

        val err = vm.validate(c, false)
        if (err != "") {
            errorDialog(err)
            return
        }
        vm.set(c)
        nav.navigateUp()
    }
//    private fun delete() {
//        // TODO: Delete
//        vm.delete(voucherId)
//
//        nav.navigateUp()
//    }

    private fun delete() {
        vm.delete(cusId)
        nav.navigateUp()
//        Navigation.findNavController(binding.root).popBackStack(R.id.vouchersFragment, false)
    }
}