package com.example.spacebunsadminapp.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.spacebunsadminapp.data.SpaceBunsViewModel
import com.example.spacebunsadminapp.databinding.FragmentProductsListBinding

class ProductUpdateFragment: Fragment() {
    private lateinit var binding: FragmentProductsListBinding
    private val nav by lazy { findNavController() }
    private val vm: SpaceBunsViewModel by activityViewModels()
}