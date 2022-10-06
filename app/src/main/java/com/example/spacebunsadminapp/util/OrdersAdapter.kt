package com.example.spacebunsadminapp.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.spacebunsadminapp.data.Orders
import com.example.spacebunsadminapp.databinding.*


class OrdersAdapter (
    val fn: (ViewHolder, Orders) -> Unit ={ _, _ ->}
    ): ListAdapter<Orders, OrdersAdapter.ViewHolder>(DiffCallBack) {

    companion object DiffCallBack:DiffUtil.ItemCallback<Orders>(){
        override fun areItemsTheSame(a: Orders, b: Orders) = a.orderId == b.orderId
        override fun areContentsTheSame(a: Orders, b: Orders) = a == b
    }
    class ViewHolder(val binding: ItemOrderLineBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemOrderLineBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = getItem(position)

        holder.binding.txtId.text = order.orderId
        holder.binding.txtTotal.text = "${"%.2f".format(order.totalPrice)}"

//        holder.binding.txtCount.text = "${order.count} Order(s)"

        fn(holder,order)
    }

    }
