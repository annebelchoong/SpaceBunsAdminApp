package com.example.spacebunsadminapp.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase

class DonationEventViewModel : ViewModel() {
    private val donations = MutableLiveData<List<DonationEvent>>() // Search + filter + sort result

    private val col = Firebase.firestore.collection("donationEvents")

    init {
        col.addSnapshotListener { value, _ -> donations.value = value?.toObjects() }
    }

    fun init() = Unit // dummy

    fun get(id: String) = donations.value?.find { it.donationEventId == id }

    fun getAll() = donations // TODO

    fun delete(id: String) {
        col.document(id).delete()
    }

    fun deleteAll() {
        donations.value?.forEach { col.document(it.donationEventId).delete() }
    }

    fun set(d: DonationEvent) {
        col.document(d.donationEventId).set(d)
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

        return e
    }

    fun getDonationAttributes(): List<String> {
//        return VOUCHERS.get().await().toObjects<Voucher>()
        return listOf("ID", "Name", "Goal", "Date")
    }
}