package com.example.spacebunsadminapp.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.spacebunsadminapp.data.DonationEvent
import com.example.spacebunsadminapp.data.DonationEventViewModel
import com.example.spacebunsadminapp.databinding.FragmentDonationEventsUpdateBinding
import com.example.spacebunsadminapp.util.cropToBlob
import com.example.spacebunsadminapp.util.errorDialog
import com.example.spacebunsadminapp.util.setImageBlob
import java.text.SimpleDateFormat
import java.util.*

class DonationEventsUpdateFragment : Fragment() {
    private lateinit var binding: FragmentDonationEventsUpdateBinding
    private val nav by lazy { findNavController() }
    private val vm: DonationEventViewModel by activityViewModels()
    private val bundle = Bundle()

    private val donationEventId by lazy { arguments?.getString("donationEventId") ?: "" }
//    private val donationEventId = bundle.getString("donationEventId", "")


    private val formatter = SimpleDateFormat("dd MMMM yyyy '-' hh:mm:ss a", Locale.getDefault())

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                binding.imgPhoto.setImageURI(it.data?.data)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDonationEventsUpdateBinding.inflate(inflater, container, false)

        reset()
        binding.imgPhoto.setOnClickListener { select() }
        binding.btnReset.setOnClickListener { reset() }
        binding.btnSubmit.setOnClickListener { submit() }
        binding.btnDelete.setOnClickListener { delete() }

        return binding.root
    }

    private fun select() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        launcher.launch(intent)
    }

    private fun reset() {
        // TODO: Get
        val dE = vm.getDonations(donationEventId)

        if (dE == null) {
            nav.navigateUp()
            return
        }

        binding.txtId.text = dE.donationEventId
        binding.edtName.setText(dE.donationEventName)
        binding.edtAge.setText(dE.donationGoal.toString())

        // TODO: Load photo and date
        binding.imgPhoto.setImageBlob(dE.donationEventPhoto)
        binding.txtDate.text = formatter.format(dE.donationStartDate)

        binding.edtName.requestFocus()
    }

    private fun submit() {
        val dE = DonationEvent(
            donationEventId = binding.txtId.text.toString().trim(),
            donationEventName = binding.edtName.text.toString().trim(),
            donationGoal = binding.edtAge.text.toString().toDoubleOrNull() ?: 0.00,
            // TODO: Photo
            donationEventPhoto = binding.imgPhoto.cropToBlob(300, 300)
        )

        val err = vm.validate(dE, false) // prevent duplicated ID when update
        if (err != "") {
            errorDialog(err)
            return
        }

        // TODO: Set (update)
        vm.set(dE)

        nav.navigateUp()
    }

    private fun delete() {
        // TODO: Delete
        vm.delete(donationEventId)

        nav.navigateUp()
    }

}
