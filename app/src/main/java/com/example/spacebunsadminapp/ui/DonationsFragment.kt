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
import com.example.spacebunsadminapp.R
import com.example.spacebunsadminapp.data.DonationViewModel
import com.example.spacebunsadminapp.databinding.FragmentDonationsBinding
import com.example.spacebunsadminapp.util.DonationAdapter
import kotlinx.coroutines.launch

class DonationsFragment : Fragment() {
    private lateinit var binding: FragmentDonationsBinding
    private val nav by lazy { findNavController() }
    private val vm: DonationViewModel by activityViewModels()
    private var progr = 70

    private lateinit var adapter: DonationAdapter
    private val id by lazy { arguments?.getString("id") ?: "" }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDonationsBinding.inflate(inflater, container, false)
        updateProgressBar()

        adapter = DonationAdapter { holder, donation ->
            holder.binding.root.setOnClickListener {
                nav.navigate(
                    R.id.donationsFragment,
                    bundleOf("donationId" to donation.donationId)
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
