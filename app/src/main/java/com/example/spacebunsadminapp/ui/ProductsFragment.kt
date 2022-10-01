package com.example.spacebunsadminapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.spacebunsadminapp.R
import com.example.spacebunsadminapp.data.Product
import com.example.spacebunsadminapp.databinding.FragmentProductsBinding
import com.example.spacebunsadminapp.databinding.FragmentProductsListBinding
import com.example.spacebunsadminapp.databinding.FragmentProductsInsertBinding
import com.example.spacebunsadminapp.databinding.FragmentProductsUpdateBinding
import com.example.spacebunsadminapp.databinding.FragmentProductItemBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase

class ProductsFragment : Fragment() {

    private lateinit var binding: FragmentProductsBinding
    private val nav by lazy { findNavController() }
    private val col = "Products"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentProductsBinding.inflate(inflater, container, false)

        binding.btnProduct.setOnClickListener { nav.navigate(R.id.productFragment) }
        binding.btnRead.setOnClickListener { read() }

        return binding.root
    }

    private fun read() {
        Firebase.firestore
            .collection(col)
//            .document("A001") // to read one document
            .get()
            .addOnSuccessListener {
                val list = it.toObjects<Product>()
                val text = list.map { f -> "${f.id} ${f.name} ${f.desc}" }
//                val text = list.map { f -> "ID: ${f.id} \nName: ${f.name} \nAge: ${f.age} years old \n" }
                binding.txtResult.text = text.joinToString("\n")    // join with \n
                toast("Read from database")
                read()
            }
    }

    private fun toast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

}