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
        override fun areItemsTheSame(a: Donation, b: Donation) =
            a.donationId == b.donationId

        override fun areContentsTheSame(a: Donation, b: Donation) = a == b
    }

    class ViewHolder(val binding: ItemDonationBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            ItemDonationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val donationDetail = getItem(position)

        holder.binding.txtDonationId.text = donationDetail.donationId
        holder.binding.txtDonorName.text = donationDetail.donorName
        holder.binding.txtDonationAmount.text = (donationDetail.donationAmount).toString()
//        holder.binding.txtUsedCount.text = donation.usedCount.toString() + " times" // count

        dn(holder, donationDetail)
    }
}