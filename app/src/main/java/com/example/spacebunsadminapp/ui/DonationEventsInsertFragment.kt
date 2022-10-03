package com.example.spacebunsadminapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.spacebunsadminapp.data.DonationEvent
import com.example.spacebunsadminapp.data.DonationEventViewModel
import com.example.spacebunsadminapp.databinding.FragmentDonationEventsInsertBinding
import com.example.spacebunsadminapp.util.errorDialog

class DonationEventsInsertFragment : Fragment() {
    private lateinit var binding: FragmentDonationEventsInsertBinding
    private val nav by lazy { findNavController() }
    private val vm: DonationEventViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDonationEventsInsertBinding.inflate(inflater, container, false)

        reset()
        binding.btnReset.setOnClickListener { reset() }
        binding.btnSubmit.setOnClickListener { submit() }

        return binding.root
    }

    private fun reset() {
        binding.edtDonationEventId.text.clear()
        binding.edtDonationEventName.text.clear()
        binding.edtDonationGoal.text.clear()
        binding.edtDonationEventId.requestFocus()
    }

    private fun submit() {
        val v = DonationEvent(
            donationEventId = binding.edtDonationEventId.text.toString().trim(),
            donationEventName = binding.edtDonationEventName.text.toString().trim(),
            donationGoal = binding.edtDonationGoal.text.toString().toDoubleOrNull()
                ?: 0.00,
        )

        val err = vm.validate(v)
        if (err != "") {
            errorDialog(err)
            return
        }

        // TODO: Set (insert)
        vm.set(v)

        nav.navigateUp()
    }
}

