package com.example.spacebunsadminapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.spacebunsadminapp.R
import com.example.spacebunsadminapp.data.Category
import com.example.spacebunsadminapp.data.UserViewModal
import com.example.spacebunsadminapp.databinding.FragmentProductsBinding
import com.example.spacebunsadminapp.databinding.FragmentStaffInsertBinding
import com.example.spacebunsadminapp.databinding.FragmentUserBinding
import com.example.spacebunsadminapp.util.UserAdapter
import kotlinx.coroutines.launch

class UserFragment : Fragment() {
    private lateinit var binding: FragmentUserBinding
    private val nav by lazy { findNavController() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentUserBinding.inflate(inflater, container, false)

        binding.btnStaff.setOnClickListener { staff() }
        binding.btnCustomer.setOnClickListener { customer() }

        return binding.root

        }

    private fun customer() {
        nav.navigate(R.id.customerFragment)
    }

    private fun staff() {
        nav.navigate(R.id.staffFragment)
    }


}