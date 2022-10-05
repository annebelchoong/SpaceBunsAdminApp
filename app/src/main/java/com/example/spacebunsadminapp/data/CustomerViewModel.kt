package com.example.spacebunsadminapp.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase

class CustomerViewModel : ViewModel() {
    //use live data for recycle view
    private val customers = MutableLiveData<List<Customer>>()

    // TODO: Initialization
    private val col = Firebase.firestore.collection("customers")

    //init code will not run until we call it
    init {
        col.addSnapshotListener { value, _ -> customers.value = value?.toObjects() } //_ means not using it, remove it
    }

    // ---------------------------------------------------------------------------------------------
    fun init() = Unit // void
    fun get(id: String) = customers.value?.find { it.cusId == id }
    fun getAll() = customers //live data

    fun delete(id: String) {
        col.document(id).delete()
    }

    fun deleteAll() {
        customers.value?.forEach { col.document(it.cusId).delete() }
    }

    fun set(c: Customer) {
        col.document(c.cusId).set(c)
    }

    //----------------------------------------------------------------------------------------------
    private fun idExists(id: String) = customers.value?.any { it.cusId == id } ?: false
    private fun emailExists(email: String) = customers.value?.any { it.cusEmail == email } ?: false

    fun validate(c: Customer, insert: Boolean = true): String {
        val regexId = Regex("""^[A-Z]\d{3}$""")
        val emailRegex = Regex("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")
        var e = ""

        if (insert) {
            e += if (c.cusId == "") "- Id is required.\n"
            else if (!c.cusId.matches(regexId)) "- Id format is invalid (format: X999).\n"
            else if (idExists(c.cusId)) "- Id is duplicated.\n"
            else ""
        }

        e += if (c.cusEmail == "") "- Email is required.\n"
        else if (!c.cusEmail.matches(emailRegex)) "- Email is too short (at least 3 letters).\n"
        else if (emailExists(c.cusEmail)) "- Email is duplicated.\n"
        else ""

        e += if (c.cusName == "") "- Name is required.\n"
        else if (c.cusName.length < 3) "- Name is too short (at least 3 letters).\n"
        else ""

        e += if (c.cusPhone == "") "- Phone no is required.\n"
        else if (c.cusPhone.length < 11) "- Phone no is invalid.\n"
        else ""

        e += if (c.cusAddress == "") "- Address is required.\n"
        else if (c.cusAddress.length < 50) "- Address is invalid.\n"
        else ""

        e += if (c.cusPhoto.toBytes().isEmpty()) "- Photo is required.\n"
        else ""

        return e
    }

    fun getCustomerAttributes(): List<String> {
//        return CUSTOMERS.get().await().toObjects<Customer>()
        return listOf("ID", "Email", "Name", "Phone", "Address", "Photo")
    }
}