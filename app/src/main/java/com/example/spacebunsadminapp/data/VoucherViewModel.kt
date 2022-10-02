package com.example.spacebunsadminapp.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class VoucherViewModel : ViewModel() {

    private var fruits = listOf<Voucher>() // Original data
    private val vouchers = MutableLiveData<List<Voucher>>() // Search + filter + sort result

    private var voucherCode = ""       // Search
    private var voucherId = "" // Filter
    private var field = ""      // Sort
    private var reverse = false // Sort


    private val col = Firebase.firestore.collection("vouchers")

    init {
        col.addSnapshotListener { value, _ -> vouchers.value = value?.toObjects() }
    }

    fun init() = Unit // dummy

    fun get(id: String) = vouchers.value?.find { it.voucherId == id }

    fun getAll() = vouchers // TODO

    fun delete(id: String) {
        col.document(id).delete()
    }

    fun deleteAll() {
        vouchers.value?.forEach { col.document(it.voucherId).delete() }
    }

    fun set(v: Voucher) {
        col.document(v.voucherId).set(v)
    }

    //----------------------------------------------------------------------------------------------

    private fun idExists(id: String) = vouchers.value?.any { it.voucherId == id } ?: false
    private fun codeExists(code: String) = vouchers.value?.any { it.voucherCode == code } ?: false

    fun validate(v: Voucher, insert: Boolean = true): String {
        val regexId = Regex("""^[A-Z]\d{3}$""")
        var e = ""

        if (insert) {
            e += if (v.voucherId == "") "- Id is required.\n"
            else if (!v.voucherId.matches(regexId)) "- Id format is invalid (format: X999).\n"
            else if (idExists(v.voucherId)) "- Id is duplicated.\n"
            else ""
        }

        e += if (v.voucherCode == "") "- VoucherCode is required.\n"
        else if (v.voucherCode.length < 3) "- VoucherCode is too short (at least 3 letters).\n"
        else ""

//        e += if (v.discountPercentage < 0.01 || v.discountPercentage > 1.00) "- DiscountPercentage must be within 0.01 to 1.00 only.\n"
        e += if (v.discountPercentage < 1 || v.discountPercentage > 100) "- DiscountPercentage must be within 1 to 100 only.\n"
        else if (v.discountPercentage == 0.00) "- DiscountPercentage cannot be zero.\n"
        else ""

        return e
    }

    fun getVoucherAttributes(): List<String> {
//        return VOUCHERS.get().await().toObjects<Voucher>()
        return listOf("ID", "%", "Used")
    }
}

