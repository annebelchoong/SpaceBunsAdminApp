package com.example.spacebunsadminapp.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.spacebunsadminapp.data.OrderDetails
import com.example.spacebunsadminapp.data.Orders
import com.example.spacebunsadminapp.databinding.ItemOrderDetailBinding

class OrderDetailsAdapter (
    val fn: (ViewHolder, OrderDetails) -> Unit ={ _, _ ->}
): ListAdapter<OrderDetails, OrderDetailsAdapter.ViewHolder>(DiffCallBack) {

    companion object DiffCallBack: DiffUtil.ItemCallback<OrderDetails>(){
        override fun areItemsTheSame(a: OrderDetails, b: OrderDetails) = a.orderId == b.orderId
        override fun areContentsTheSame(a: OrderDetails, b: OrderDetails) = a == b
    }
    class ViewHolder(val binding: ItemOrderDetailBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemOrderDetailBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val orderD = getItem(position)

        holder.binding.txtProductName.text = orderD.productName
        holder.binding.txtProductId.text = orderD.productId
        holder.binding.txtQuantity.text = "x ${orderD.quantity}"
        orderD.totalPrice = orderD.price * orderD.quantity
        holder.binding.txtSubtotal.text = "RM ${"%.2f".format(orderD.totalPrice)}"
        holder.binding.imageView.setImageBlob(orderD.photo)

        fn(holder,orderD)
    }

}
