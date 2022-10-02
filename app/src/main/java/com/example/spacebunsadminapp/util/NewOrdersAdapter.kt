package com.example.spacebunsadminapp.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.spacebunsadminapp.data.Orders
import com.example.spacebunsadminapp.databinding.ItemNewOrdersBinding

class NewOrdersAdapter (
    val fn: (ViewHolder, Orders) -> Unit ={ _, _ ->}
): ListAdapter<Orders, NewOrdersAdapter.ViewHolder>(DiffCallBack) {

    companion object DiffCallBack:DiffUtil.ItemCallback<Orders>(){
        override fun areItemsTheSame(a: Orders, b: Orders) = a.orderId == b.orderId
        override fun areContentsTheSame(a: Orders, b: Orders) = a == b
    }
    class ViewHolder(val binding: ItemNewOrdersBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNewOrdersBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = getItem(position)

        holder.binding.txtId.text = order.orderId
        holder.binding.txtTotal.text = order.totalPrice.toString()

//        holder.binding.txtCount.text = "${order.count} Order(s)"

        fn(holder,order)
    }

}