package com.example.spacebunsadminapp.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.spacebunsadminapp.data.Voucher
import com.example.spacebunsadminapp.databinding.ItemVoucherBinding


class VoucherAdapter(
    val vn: (ViewHolder, Voucher) -> Unit = { _, _ -> }
) : ListAdapter<Voucher, VoucherAdapter.ViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Voucher>() {
        override fun areItemsTheSame(a: Voucher, b: Voucher) = a.voucherId == b.voucherId
        override fun areContentsTheSame(a: Voucher, b: Voucher) = a == b
    }

    class ViewHolder(val binding: ItemVoucherBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemVoucherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val voucher = getItem(position)

        holder.binding.txtVoucherId.text = voucher.voucherId
        holder.binding.txtVoucherCode.text = voucher.voucherCode
        holder.binding.txtDiscountPercentage.text = (voucher.discountPercentage).toString()
        holder.binding.txtUsedCount.text = voucher.usedCount.toString() + " times" // count

        vn(holder, voucher)
    }

}

