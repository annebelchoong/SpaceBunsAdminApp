package com.example.spacebunsadminapp.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.spacebunsadminapp.data.DonationEvent
import com.example.spacebunsadminapp.data.DonationEventViewModel
import com.example.spacebunsadminapp.databinding.FragmentDonationEventsInsertBinding
import com.example.spacebunsadminapp.util.cropToBlob
import com.example.spacebunsadminapp.util.errorDialog

class DonationEventsInsertFragment : Fragment() {
    private lateinit var binding: FragmentDonationEventsInsertBinding
    private val nav by lazy { findNavController() }
    private val vm: DonationEventViewModel by activityViewModels()

    // TODO: Get content launcher
    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                binding.imgDonationEventPhoto.setImageURI(it.data?.data)  // result is path
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDonationEventsInsertBinding.inflate(inflater, container, false)

        reset()
        binding.imgDonationEventPhoto.setOnClickListener { select() }
        binding.btnReset.setOnClickListener { reset() }
        binding.btnSubmit.setOnClickListener { submit() }

        return binding.root
    }

    private fun reset() {
        binding.edtDonationEventId.text.clear()
        binding.edtDonationEventName.text.clear()
        binding.edtDonationGoal.text.clear()
        binding.imgDonationEventPhoto.setImageDrawable(null)
        binding.edtDonationEventId.requestFocus()
    }

    private fun select() {
        // TODO: Select file
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        launcher.launch(intent)
    }

    private fun submit() {
        val v = DonationEvent(
            donationEventId = binding.edtDonationEventId.text.toString().uppercase().trim(),
            donationEventName = binding.edtDonationEventName.text.toString().trim(),
            donationGoal = binding.edtDonationGoal.text.toString().toDoubleOrNull() ?: 0.00,
            donationEventPhoto = binding.imgDonationEventPhoto.cropToBlob(300, 300)
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

