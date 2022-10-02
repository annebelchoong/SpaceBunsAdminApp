package com.example.spacebunsadminapp.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.spacebunsadminapp.data.OrderStatus
import com.example.spacebunsadminapp.databinding.ItemOrderBinding
import com.example.spacebunsadminapp.databinding.ItemOrderLineBinding

class OrderStatusAdapter (
    val fn: (ViewHolder, OrderStatus) -> Unit ={ _, _ ->}
): ListAdapter<OrderStatus, OrderStatusAdapter.ViewHolder>(DiffCallBack) {

    companion object DiffCallBack : DiffUtil.ItemCallback<OrderStatus>() {
        override fun areItemsTheSame(a: OrderStatus, b: OrderStatus) = a.id == b.id
        override fun areContentsTheSame(a: OrderStatus, b: OrderStatus) = a == b
    }

    class ViewHolder(val binding: ItemOrderBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemOrderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val orderStatus = getItem(position)

        holder.binding.txtOrderStatusLabels.text = orderStatus.name

        fn(holder, orderStatus)
    }
}