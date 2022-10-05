package com.example.spacebunsadminapp.util

import android.view.LayoutInflater
import androidx.recyclerview.widget.DiffUtil
import com.example.spacebunsadminapp.data.Staff

class StaffAdapter {
    val fn: (ViewHolder, Staff) -> Unit = { _, _ -> }
    ) : ListAdapter<Staff, StaffAdapter.ViewHolder>(DiffCallback) {

        companion object DiffCallback : DiffUtil.ItemCallback<Staff>() {
            override fun areItemsTheSame(a: Staff, b: Staff) = a.staffId == b.staffId
            override fun areContentsTheSame(a: Staff, b: Staff) = a == b
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

            fn(holder, voucher
}