package com.example.spacebunsadminapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.spacebunsadminapp.R
import com.example.spacebunsadminapp.databinding.FragmentLoginBinding
import com.example.spacebunsadminapp.databinding.FragmentOrdersBinding
import com.example.spacebunsadminapp.util.hideKeyboard
import kotlinx.coroutines.launch


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val nav by lazy { findNavController() }
    //private val auth: AuthViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.btnLogin.setOnClickListener { login() }
        return binding.root
    }

    private fun login() {
        hideKeyboard()

        val ctx      = requireContext()
        val email    = binding.edtEmail.text.toString().trim()
        val password = binding.edtPassword.text.toString().trim()
        val remember = binding.chkRemember.isChecked

        //          Clear navigation backstack
        lifecycleScope.launch {
//            val success = auth.login(ctx, email, password, remember)
//            if (success) {
//                nav.popBackStack(R.id.dashboardFragment, false)
//                nav.navigateUp()
//            } else {
//                errorDialog("Invalid login credentials.")
//            }
        }
    }
}