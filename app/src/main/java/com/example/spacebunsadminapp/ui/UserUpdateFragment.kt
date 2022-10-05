package com.example.spacebunsadminapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.spacebunsadminapp.R
import com.example.spacebunsadminapp.data.User
import com.example.spacebunsadminapp.data.UserViewModal
import com.example.spacebunsadminapp.databinding.FragmentUpdateProfileBinding
import com.example.spacebunsadminapp.databinding.FragmentUserUpdateBinding
import com.example.spacebunsadminapp.util.errorDialog

class UserUpdateFragment : Fragment() {
    private lateinit var binding: FragmentUserUpdateBinding
    private val nav by lazy { findNavController() }
    private val vm: UserViewModal by activityViewModels()
    private val userId by lazy { arguments?.getString("userId") ?: "" }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserUpdateBinding.inflate(inflater, container, false)

        reset()
        binding.btnReset.setOnClickListener { reset() }
        binding.btnSubmit.setOnClickListener { submit() }
        //binding.btnDelete.setOnClickListener { delete() }

        return binding.root
    }

    private fun reset() {
        // TODO: Get
        val u = vm.get(userId)

        if (u == null) {
            nav.navigateUp()
            return
        }

        //binding.txtUserId.text = u.userId
        binding.edtUsername.setText(u.name)
        binding.edtEmail.setText(u.email)

        binding.edtUsername.requestFocus()
    }

    private fun submit() {
        val u = User(
            //userId = binding.txtUserId.text.toString().trim(),
            name = binding.edtUsername.text.toString().trim(),
            email = binding.edtEmail.text.toString()
        )

//        val err = vm.validate(u, false)
//        if (err != "") {
//            errorDialog(err)
//            return
//        }
//        vm.set(u)
//        nav.navigateUp()
//    }
//
//    private fun delete() {
//        vm.delete(userId)
//
//        nav.navigateUp()
////        Navigation.findNavController(binding.root).popBackStack(R.id.vouchersFragment, false)
//    }
}