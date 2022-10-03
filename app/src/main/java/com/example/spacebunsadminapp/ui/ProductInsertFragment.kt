package com.example.spacebunsadminapp.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.spacebunsadminapp.data.Product
import com.example.spacebunsadminapp.data.SpaceBunsViewModel
import com.example.spacebunsadminapp.databinding.FragmentProductsInsertBinding
import com.example.spacebunsadminapp.util.cropToBlob
import com.example.spacebunsadminapp.util.errorDialog

class ProductInsertFragment: Fragment() {
    private lateinit var binding: FragmentProductsInsertBinding
    private val nav by lazy { findNavController() }
    private val vm: SpaceBunsViewModel by activityViewModels()

    // TODO: Get content launcher
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            binding.imgPhoto.setImageURI(it.data?.data)  // result is path
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductsInsertBinding.inflate(inflater, container, false)

        reset()
        binding.imgPhoto.setOnClickListener { select() }
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
        binding.edtId.text.clear()
        binding.spinnerCat.setSelection(0)
        binding.edtName.text.clear()
        binding.edtDesc.text.clear()
        binding.imgPhoto.setImageDrawable(null)
        binding.edtId.requestFocus()
    }

    private fun submit() {
        val f = Product(
            id = binding.edtId.text.toString().trim(),
            cat = binding.spinnerCat.selectedItem as String,
            name = binding.edtName.text.toString().trim(),
            desc = binding.edtDesc.text.toString().trim(),
            // TODO: Photo
            photo = binding.imgPhoto.cropToBlob(300, 300)
        )

        val err = vm.validate(f)
        if (err != "") {
            errorDialog(err)
            return
        }

        // TODO: Set (insert)
        vm.set(f)

        nav.navigateUp()
    }
}