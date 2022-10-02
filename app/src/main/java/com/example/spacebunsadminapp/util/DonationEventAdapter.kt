package com.example.spacebunsadminapp.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.spacebunsadminapp.data.DonationEvent
import com.example.spacebunsadminapp.databinding.ItemDonationEventBinding

class DonationEventAdapter(
    val dn: (ViewHolder, DonationEvent) -> Unit = { _, _ -> }
) : ListAdapter<DonationEvent, DonationEventAdapter.ViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<DonationEvent>() {
        override fun areItemsTheSame(a: DonationEvent, b: DonationEvent) = a.donationEventId == b.donationEventId
        override fun areContentsTheSame(a: DonationEvent, b: DonationEvent) = a == b
    }

    class ViewHolder(val binding: ItemDonationEventBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DonationEventAdapter.ViewHolder {
        val binding =
            ItemDonationEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DonationEventAdapter.ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DonationEventAdapter.ViewHolder, position: Int) {
        val donation = getItem(position)

        holder.binding.txtDonationEventId.text = donation.donationEventId
        holder.binding.txtDonationEventName.text = donation.donationEventName
        holder.binding.txtDonationGoal.text = (donation.donationGoal).toString()
//        holder.binding.txtUsedCount.text = donation.usedCount.toString() + " times" // count

        dn(holder, donation)
    }
}