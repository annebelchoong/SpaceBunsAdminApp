package com.example.spacebunsadminapp.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase

class StaffViewModal : ViewModel() {

    private val staffs = MutableLiveData<List<Staff>>() // Search + filter + sort result

    private val col = Firebase.firestore.collection("staffs")

    init {
        col.addSnapshotListener { value, _ -> staffs.value = value?.toObjects() }
    }

    fun init() = Unit // dummy

    fun get(id: String) = staffs.value?.find { it.staffId == id }

    fun getAll() = staffs // TODO

    fun delete(id: String) {
        col.document(id).delete()
    }

    fun deleteAll() {
        staffs.value?.forEach { col.document(it.staffId).delete() }
    }

    fun set(s: Staff) {
        col.document(s.staffId).set(s)
    }

    //----------------------------------------------------------------------------------------------

    private fun idExists(id: String) = staffs.value?.any { it.staffId == id } ?: false
    private fun emailExists(email: String) = staffs.value?.any { it.staffEmail == email } ?: false

    fun validate(s: Staff, insert: Boolean = true): String {
        val regexId = Regex("""^[A-Z]\d{3}$""")
        val emailRegex = Regex("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")
        var e = ""

        if (insert) {
            e += if (s.staffId == "") "- Id is required.\n"
            else if (!s.staffId.matches(regexId)) "- Id format is invalid (format: X999).\n"
            else if (idExists(s.staffId)) "- Id is duplicated.\n"
            else ""
        }

        e += if (s.staffEmail == "") "- Email is required.\n"
        else if (!s.staffEmail.matches(emailRegex)) "- Email is too short (at least 3 letters).\n"
        //else if (emailExists(s.staffEmail)) "- Email is duplicated.\n"
        else ""

        e += if (s.staffName == "") "- Name is required.\n"
        else if (s.staffName.length < 3) "- Name is too short (at least 3 letters).\n"
        else ""

//        e += if (v.discountPercentage < 0.01 || v.discountPercentage > 1.00) "- DiscountPercentage must be within 0.01 to 1.00 only.\n"
        e += if (s.salary < 1 ) "- salary must be more than 1.\n"
        else if (s.salary == 0.00) "- salary cannot be zero.\n"
        else ""

        e += if (s.staffPhone == "") "- Phone no is required.\n"
        else if (s.staffPhone.length < 10) "- Phone no is invalid.\n"
        else ""

//        e += if (s.staffAddress == "") "- Address is required.\n"
//        else if (s.staffAddress.length < 50) "- Address is invalid.\n"
//        else ""

        e += if (s.staffPhoto.toBytes().isEmpty()) "- Photo is required.\n"
        else ""

        return e
    }

//    fun getStaffAttributes(): List<String> {
////        return STAFFS.get().await().toObjects<Staff>()
//        return listOf("ID", "Email", "Name", "Salary")
//    }
}