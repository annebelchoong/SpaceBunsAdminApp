package com.example.spacebunsadminapp.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.spacebunsadminapp.data.OrdersHistory
import com.example.spacebunsadminapp.databinding.ItemOrderHistoryBinding


class OrderHistoryAdapter (
    val fn: (ViewHolder, OrdersHistory) -> Unit ={ _, _ ->}
    ): ListAdapter<OrdersHistory, OrderHistoryAdapter.ViewHolder>(DiffCallBack) {

    companion object DiffCallBack:DiffUtil.ItemCallback<OrdersHistory>(){
        override fun areItemsTheSame(a: OrdersHistory, b: OrdersHistory) = a.orderId == b.orderId
        override fun areContentsTheSame(a: OrdersHistory, b: OrdersHistory) = a == b
    }
    class ViewHolder(val binding: ItemOrderHistoryBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemOrderHistoryBinding.inflate(
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
