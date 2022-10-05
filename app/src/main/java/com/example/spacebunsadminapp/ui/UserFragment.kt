package com.example.spacebunsadminapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.spacebunsadminapp.R
import com.example.spacebunsadminapp.databinding.FragmentUserBinding

class UserFragment : Fragment() {
    private lateinit var binding: FragmentUserBinding
    private val nav by lazy { findNavController() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentUserBinding.inflate(inflater, container, false)

        binding.btnStaff.setOnClickListener { staff() }
        binding.btnStaffInsert.setOnClickListener { insertStaff() }
        binding.btnStaffList.setOnClickListener { staffList() }
        binding.btnStaffUpdate.setOnClickListener { updateStaff() }

        binding.btnCustomer.setOnClickListener { customer() }
        binding.btnCusInsert.setOnClickListener { insertCus() }
        binding.btnCusList.setOnClickListener { cusList() }
        binding.btnCusUpdate.setOnClickListener { updateCus() }


        return binding.root

        }

    private fun updateCus() {
        nav.navigate(R.id.customerUpdateFragment)
    }

    private fun cusList() {
        nav.navigate(R.id.customerListFragment)
    }

    private fun insertCus() {
        nav.navigate(R.id.customerInsertFragment)
    }

    private fun updateStaff() {
        nav.navigate(R.id.staffUpdateFragment)
    }

    private fun staffList() {
        nav.navigate(R.id.staffListFragment)
    }

    private fun insertStaff() {
        nav.navigate(R.id.staffInsertFragment)
    }

    private fun customer() {
        nav.navigate(R.id.customerFragment)
    }

    private fun staff() {
        nav.navigate(R.id.staffFragment)
    }


}