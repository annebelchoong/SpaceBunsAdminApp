package com.example.spacebunsadminapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.spacebunsadminapp.R
import com.example.spacebunsadminapp.data.DonationEventDetailViewModel
import com.example.spacebunsadminapp.databinding.FragmentDonationEventDetailBinding
import com.example.spacebunsadminapp.util.DonationEventDetailAdapter

class DonationEventDetailFragment : Fragment() {
    private lateinit var binding: FragmentDonationEventDetailBinding
    private val nav by lazy { findNavController() }
    private val vm: DonationEventDetailViewModel by activityViewModels()
    private var progr = 70

    private lateinit var adapter: DonationEventDetailAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDonationEventDetailBinding.inflate(inflater, container, false)
        updateProgressBar()

        adapter = DonationEventDetailAdapter { holder, donationEventDetail ->
            holder.binding.root.setOnClickListener {
                nav.navigate(
                    R.id.donationEventDetailFragment,
                    bundleOf("donationEventDetailId" to donationEventDetail.donationEventDetailId)
                )
            }
        }
        binding.rvDonations.adapter = adapter
        binding.rvDonations.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )

        // TODO: Get all
        vm.getAll().observe(viewLifecycleOwner) {
            adapter.submitList(it)
//            binding.txtDonationCount.text = "${it.size} donation(s)"
        }
        return binding.root
    }

    private fun updateProgressBar() {
        binding.pbDonationDetail.progress = progr
        binding.txtDonationDetailProgress.text = progr.toString() + ".00%"
    }
}
