package com.example.spacebunsadminapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.spacebunsadminapp.data.DonationEventViewModel
import com.example.spacebunsadminapp.databinding.FragmentDonationsBinding
import com.example.spacebunsadminapp.util.DonationAdapter
import kotlinx.coroutines.launch

class DonationsFragment : Fragment() {
    private lateinit var binding: FragmentDonationsBinding
    private val nav by lazy { findNavController() }
    private val vm: DonationEventViewModel by activityViewModels()
    private var progr = 70

    //    private lateinit var adapter: DonationAdapter
    private val id by lazy { arguments?.getString("donationEventId", "") ?: "" }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDonationsBinding.inflate(inflater, container, false)
        updateProgressBar()

        val adapter = DonationAdapter()
        binding.rvDonations.adapter = adapter
        binding.rvDonations.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )

        lifecycleScope.launch {
            val donationEvents = vm.get(id)!!
            binding.txtTitle.text = "${donationEvents.donationEventName}"

            val donations = vm.getDonations(id)
            adapter.submitList(donations)
            binding.txtUsedCountFIxed.text = "${donations.size} Donation(s)"
        }
        return binding.root
    }

    private fun updateProgressBar() {
        binding.pbDonationDetail.progress = progr
        binding.txtDonationDetailProgress.text = progr.toString() + ".00%"
    }
}
