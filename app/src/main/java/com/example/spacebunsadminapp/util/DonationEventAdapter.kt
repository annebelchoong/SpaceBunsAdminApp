package com.example.spacebunsadminapp.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.spacebunsadminapp.data.DonationEvent
import com.example.spacebunsadminapp.databinding.ItemDonationEventBinding

class DonationEventAdapter(
    val fn: (ViewHolder, DonationEvent) -> Unit = { _, _ -> }
) : ListAdapter<DonationEvent, DonationEventAdapter.ViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<DonationEvent>() {
        override fun areItemsTheSame(a: DonationEvent, b: DonationEvent) =
            a.donationEventId == b.donationEventId

        override fun areContentsTheSame(a: DonationEvent, b: DonationEvent) = a == b
    }

    class ViewHolder(val binding: ItemDonationEventBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            ItemDonationEventBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val donationEvent = getItem(position)

        holder.binding.txtDonationEventId.text = donationEvent.donationEventId
        holder.binding.txtDonationEventName.text = donationEvent.donationEventName
        holder.binding.txtDonationGoal.text = (donationEvent.donationGoal).toString()

        // TODO: Load photo blob (use extension method)
        holder.binding.imgDonationEvent.setImageBlob(donationEvent.donationEventPhoto)

        // TODO(9): Display [count] field
        holder.binding.txtDonationCount.text = "${donationEvent.count} Donation(s)"

        fn(holder, donationEvent)
    }
}