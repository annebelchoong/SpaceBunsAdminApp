package com.example.spacebunsadminapp.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.spacebunsadminapp.data.Donation
import com.example.spacebunsadminapp.databinding.ItemDonationBinding

class DonationAdapter(
    val dn: (ViewHolder, Donation) -> Unit = { _, _ -> }
) : ListAdapter<Donation, DonationAdapter.ViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Donation>() {
        override fun areItemsTheSame(a: Donation, b: Donation) = a.donationId == b.donationId
        override fun areContentsTheSame(a: Donation, b: Donation) = a == b
    }

    class ViewHolder(val binding: ItemDonationBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DonationAdapter.ViewHolder {
        val binding =
            ItemDonationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DonationAdapter.ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DonationAdapter.ViewHolder, position: Int) {
        val donation = getItem(position)

        holder.binding.txtDonationId.text = donation.donationId
        holder.binding.txtDonorName.text = donation.donorName
        holder.binding.txtDonationAmount.text = (donation.donationAmount).toString()
//        holder.binding.txtUsedCount.text = donation.usedCount.toString() + " times" // count

        dn(holder, donation)
    }
}