package com.example.spacebunsadminapp.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.spacebunsadminapp.data.Staff
import com.example.spacebunsadminapp.databinding.ItemStaffBinding

class StaffAdapter (
        val fn: (ViewHolder, Staff) -> Unit = { _, _ -> }
    ) : ListAdapter<Staff, StaffAdapter.ViewHolder>(DiffCallback) {

        companion object DiffCallback : DiffUtil.ItemCallback<Staff>() {
            override fun areItemsTheSame(a: Staff, b: Staff) = a.staffId == b.staffId
            override fun areContentsTheSame(a: Staff, b: Staff) = a == b
        }

        class ViewHolder(val binding: ItemStaffBinding) : RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding = ItemStaffBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val staff = getItem(position)

            holder.binding.txtStaffIdItem.text = staff.staffId
            holder.binding.txtStaffNameItem.text = staff.staffName
            holder.binding.txtStaffEmailItem.text = staff.staffEmail
            holder.binding.txtStaffPhoneItem.text = staff.staffPhone
            holder.binding.txtSalaryItem.text = "( RM" + String.format("%.2f",staff.salary) + " )"
            holder.binding.imgStaffPhoto.setImageBlob(staff.staffPhoto)

            fn(holder, staff)
        }

    }