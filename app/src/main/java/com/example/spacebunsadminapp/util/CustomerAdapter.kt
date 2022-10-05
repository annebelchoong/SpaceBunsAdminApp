package com.example.spacebunsadminapp.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.spacebunsadminapp.data.Customer
import com.example.spacebunsadminapp.data.Staff
import com.example.spacebunsadminapp.databinding.ItemCustomerBinding

class CustomerAdapter (
    val fn: (ViewHolder, Customer) -> Unit = { _, _ -> }
) : ListAdapter<Customer, CustomerAdapter.ViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Customer>() {
        override fun areItemsTheSame(a: Customer, b: Customer) = a.cusId == b.cusId
        override fun areContentsTheSame(a: Customer, b: Customer) = a == b
    }

    class ViewHolder(val binding: ItemCustomerBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCustomerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val customer = getItem(position)

        holder.binding.txtCusIdItem.text = customer.cusId
        holder.binding.txtCusNameItem.text = customer.cusName
        holder.binding.txtCusEmailItem.text = customer.cusEmail
        holder.binding.txtCusPasswordItem.text = customer.cusPassword
        holder.binding.txtCusPhoneItem.text = customer.cusPhone
        holder.binding.txtCusAddressItem.text = customer.cusAddress
        holder.binding.imgCusPhotoItem.setImageBlob(customer.cusPhoto)
        //holder.binding.imgPhoto.setImageBlob(friend.photo)
        //holder.binding.txtSalary.text = (customer.salary).toString()
        //holder.binding.txtCount.text = staff.Count.toString() + " times" // count

        fn(holder, customer)
    }
}