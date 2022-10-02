package com.example.spacebunsadminapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.spacebunsadminapp.R
import com.example.spacebunsadminapp.data.DonationViewModel
import com.example.spacebunsadminapp.data.VoucherViewModel
import com.example.spacebunsadminapp.databinding.FragmentDonationsBinding
import com.example.spacebunsadminapp.util.DonationAdapter
import com.example.spacebunsadminapp.util.VoucherAdapter

class DonationsFragment : Fragment() {
    private lateinit var binding: FragmentDonationsBinding
    private val nav by lazy { findNavController() }
    private val dm: DonationViewModel by activityViewModels()
    private var progr = 70

    private lateinit var adapter: DonationAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDonationsBinding.inflate(inflater, container, false)
        updateProgressBar();

        binding.pbDonations.setOnClickListener { nav.navigate(R.id.insertVoucherFragment) }

        adapter = DonationAdapter { holder, donation ->
            holder.binding.root.setOnClickListener {
                nav.navigate(
                    R.id.updateVoucherFragment,
                    bundleOf("voucherId" to donation.donationId)
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

        // -----------------------------------------------------------------------------------------

        val arrayAdapter =
            ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spnDonationAttributes.adapter = arrayAdapter

        val donations = dm.getDonationAttributes()
        arrayAdapter.add("All")
        arrayAdapter.addAll(donations)

        // TODO: Get all
        dm.getAll().observe(viewLifecycleOwner) {
            adapter.submitList(it)
            binding.txtDonationCount.text = "${it.size} donation(s)"
        }

        return binding.root
    }


    private fun updateProgressBar() {
        binding.pbDonations.progress = progr
        binding.txtDonationProgress.text = progr.toString() + ".00%"
    }
}