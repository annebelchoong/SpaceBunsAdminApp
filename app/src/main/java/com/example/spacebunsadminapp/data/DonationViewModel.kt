package com.example.spacebunsadminapp.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase

class DonationViewModel : ViewModel() {
    private val donations = MutableLiveData<List<Donation>>() // Search + filter + sort result

    private val col = Firebase.firestore.collection("donations")

    init {
        col.addSnapshotListener { value, _ -> donations.value = value?.toObjects() }
    }

    fun init() = Unit // dummy

    fun get(id: String) = donations.value?.find { it.donationId == id }

    fun getAll() = donations // TODO

    fun delete(id: String) {
        col.document(id).delete()
    }

    fun deleteAll() {
        donations.value?.forEach { col.document(it.donationId).delete() }
    }

    fun set(d: Donation) {
        col.document(d.donationId).set(d)
    }

    //----------------------------------------------------------------------------------------------

    private fun idExists(id: String) = donations.value?.any { it.donationId == id } ?: false
//    private fun codeExists(code: String) = donations.value?.any { it.voucherCode == code } ?: false

    fun validate(d: Donation, insert: Boolean = true): String {
        val regexId = Regex("""^[A-Z]\d{3}$""")
        var e = ""

        if (insert) {
            e += if (d.donationId == "") "- Id is required.\n"
            else if (!d.donationId.matches(regexId)) "- Id format is invalid (format: X999).\n"
            else if (idExists(d.donationId)) "- Id is duplicated.\n"
            else ""
        }

//        e += if (d.donationAmount == "") "- VoucherCode is required.\n"
//        else if (d.donationAmount.length < 3) "- VoucherCode is too short (at least 3 letters).\n"
//        else ""

//        e += if (d.donationDate < 1 || d.donationDate > 100) "- DiscountPercentage must be within 1 to 100 only.\n"
//        else if (d.donationDate == 0.00) "- DiscountPercentage cannot be zero.\n"
//        else ""

        return e
    }

    fun getDonationAttributes(): List<String> {
//        return VOUCHERS.get().await().toObjects<Voucher>()
        return listOf("ID", "Amount", "Date")
    }
}