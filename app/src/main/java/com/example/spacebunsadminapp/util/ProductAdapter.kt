package com.example.spacebunsadminapp.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.spacebunsadminapp.data.Product
import com.example.spacebunsadminapp.databinding.FragmentProductItemBinding


class ProductAdapter (
    val fn: (ViewHolder, Product) -> Unit = { _, _ -> }
) : ListAdapter<Product, ProductAdapter.ViewHolder>(DiffCallback){

    companion object DiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(a: Product, b: Product)    = a.id == b.id
        override fun areContentsTheSame(a: Product, b: Product) = a == b
    }

    class ViewHolder(val binding: FragmentProductItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = getItem(position)

        holder.binding.txtId.text   = product.id
        holder.binding.txtCat.text = ("--> " + product.cat + " <--")
        holder.binding.txtName.text = product.name
        holder.binding.txtDesc.text  = product.desc

        // TODO: Load photo blob (use extension method)
//        holder.binding.imgPhoto.setImageDrawable(null) // default doesn't work
        holder.binding.imgPhoto.setImageBlob(product.photo)

        fn(holder, product)
    }

}


