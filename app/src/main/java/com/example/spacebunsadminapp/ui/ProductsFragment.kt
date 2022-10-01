package com.example.spacebunsadminapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.spacebunsadminapp.R
import com.example.spacebunsadminapp.databinding.FragmentProductsBinding
import com.example.spacebunsadminapp.databinding.FragmentProductsListBinding
import com.example.spacebunsadminapp.databinding.FragmentProductsInsertBinding
import com.example.spacebunsadminapp.databinding.FragmentProductsUpdateBinding
import com.example.spacebunsadminapp.databinding.FragmentProductItemBinding

class ProductsFragment : Fragment() {

    private lateinit var binding: FragmentProductsBinding
    private val nav by lazy { findNavController() }
    private val col = "Products"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentProductsBinding.inflate(inflater, container, false)



        return binding.root
    }
}