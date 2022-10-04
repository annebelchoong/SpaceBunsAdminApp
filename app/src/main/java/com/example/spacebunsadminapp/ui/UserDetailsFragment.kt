package com.example.spacebunsadminapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.spacebunsadminapp.R
import com.example.spacebunsadminapp.data.UserViewModal
import com.example.spacebunsadminapp.databinding.FragmentUserBinding
import com.example.spacebunsadminapp.databinding.FragmentUserDetailsBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.DecimalFormat


class UserDetailsFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentUserDetailsBinding
    private val nav by lazy { findNavController() }
    private val vm: UserViewModal by activityViewModels()

    private val id by lazy { arguments?.getString("id", "") ?: "" }
    private val formatter = DecimalFormat("0.00")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        binding = FragmentUserDetailsBinding.inflate(inflater, container, false)

        binding.btnClose.setOnClickListener { dismiss() }

        // -----------------------------------------------------------------------------------------

        val user = vm.get(id)!!

        binding.txtId.text    = user.id
        binding.txtName.text  = user.name
        binding.txtEmail.text = user.email

        binding.txtCategory.text = user.category.name

        return binding.root
    }
}