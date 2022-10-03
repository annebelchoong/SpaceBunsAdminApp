package com.example.spacebunsadminapp.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class DonationEventViewModel : ViewModel() {
    private val donations = MutableLiveData<List<DonationEvent>>() // Search + filter + sort result

    private val col = Firebase.firestore.collection("donationEvents")

    init {
        col.addSnapshotListener { value, _ -> donations.value = value?.toObjects() }
    }

    fun init() = Unit // dummy
//    fun getAll() = donations // TODO

    // TODO(7): Return all categories
    //          Populate [count] field
    suspend fun getAll(): List<DonationEvent> {  // make function a suspend if use await()
        val donationEvents = DONATIONEVENTS
            .get()
            .await()
            .toObjects<DonationEvent>()

        for (e in donationEvents) {
            e.count = DONATIONS
                .whereEqualTo("donationEventId", e.donationEventId)
                .get()
                .await()
                .size() // return the number of records
        }

        return donationEvents
    }

//    fun get(id: String) = donations.value?.find { it.donationEventId == id }

    // TODO(10): Return a specific category
    suspend fun get(id: String): DonationEvent? {
        return DONATIONEVENTS
            .document(id)
            .get()
            .await()
            .toObject<DonationEvent>()
    }

    // TODO(11): Return all fruits under a specific category
    //           Populate [category] field
    suspend fun getDonations(id: String): List<Donation> {
        val donations = DONATIONS
            .whereEqualTo("donationEventId", id)
            .get()
            .await()
            .toObjects<Donation>() // return fruits record

        val donationEvent = get(id)
        for (d in donations) {
            d.donationEvent = donationEvent!! // give runtime error if return is null
        }

        return donations
    }

    fun delete(id: String) {
        DONATIONEVENTS.document(id).delete()
    }

//    fun deleteAll() {
//        donations.value?.forEach { _root_ide_package_.com.example.spacebunsadminapp.data.DONATIONEVENTS.document(it.donationEventId).delete() }
//    }

    fun set(d: DonationEvent) {
        DONATIONEVENTS.document(d.donationEventId).set(d)
    }

    //----------------------------------------------------------------------------------------------

    private fun idExists(id: String) = donations.value?.any { it.donationEventId == id } ?: false
//    private fun codeExists(code: String) = donations.value?.any { it.voucherCode == code } ?: false

    fun validate(d: DonationEvent, insert: Boolean = true): String {
        val regexId = Regex("""^[A-Z][A-Z]\d{2}$""")
        var e = ""

        if (insert) {
            e += if (d.donationEventId == "") "- Id is required.\n"
            else if (!d.donationEventId.matches(regexId)) "- Id format is invalid (format: XX99).\n"
            else if (idExists(d.donationEventId)) "- Id is duplicated.\n"
            else ""
        }

        e += if (d.donationEventName == "") "- Event Name is required.\n"
        else if (d.donationEventName.length < 3) "- Event Name is too short (at least 3 letters).\n"
        else ""

        e += if (d.donationGoal < 1) "- Donation Goal must be more than one.\n"
        else if (d.donationGoal == 0.00) "- Donation Goal cannot be zero.\n"
        else ""

        e += if (d.donationEventPhoto.toBytes().isEmpty()) "- Event Photo is required.\n"
        else ""

        return e
    }

    fun getDonationAttributes(): List<String> {
//        return VOUCHERS.get().await().toObjects<Voucher>()
        return listOf("ID", "Name", "Goal", "Date")
    }
}