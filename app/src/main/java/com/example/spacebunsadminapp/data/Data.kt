package com.example.spacebunsadminapp.data

import com.google.firebase.firestore.Blob
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

data class Category(  //when compare, it will compare what in the bracket - id, name
    @DocumentId
    var id: String = "",
    var name: String = "",
) {
    @get:Exclude
    var count: Int = 0
    override fun toString() = name //Spinner
}

data class User(
    @DocumentId
    var id: String = "",
    var name: String = "",
    var email: String = "",
    var categoryId: String = "",
) {
    @get:Exclude
    var category: Category = Category()
}                           //empty file

data class Customer (
    @DocumentId
    var cusId      : String = "",
    var cusEmail   : String = "",
    var cusPassword: String = "",
    var cusName    : String = "",
    var cusPhone   : String = "",
    var cusAddress : String = "",
    var cusPhoto   : Blob = Blob.fromBytes(ByteArray(0)),
    var date : Date = Date(),
    //var cusCount: Int = 0,
)

data class Staff(
    @DocumentId
    var staffId: String = "",
    var staffName: String = "",
    var staffEmail: String = "",
    var salary: Double = 0.00,
    var staffPhone   : String = "",
    //var staffAddress : String = "",
    var staffPhoto   : Blob = Blob.fromBytes(ByteArray(0)),
    var staffDate: Date = Date() // current Date,
)

val STAFFS = Firebase.firestore.collection("staffs")
// -------------------------------------------------------------------------------------------------

val CATEGORIES = Firebase.firestore.collection("categories")
val USERS = Firebase.firestore.collection("users")

// -------------------------------------------------------------------------------------------------

fun RESTORE_DATA() {
    val categories = listOf(
        Category("S", "Staff"),
        Category("C", "Customer"),
    )

    val users = listOf(
        User("U001", "Abu", "abu@gmail.com", "C"),
        User("U002", "Beth", "beth@gmail.com", "C"),
        User("U003", "Chris", "chris@gmail.com", "S"),
        User("U004", "Dean", "dean@gmail.com", "C"),
        User("U005", "Ellie", "ellie@gmail.com", "S"),
    )

    for (c in categories) {
        CATEGORIES.document(c.id).set(c) //set means insert or replace
    }

    for (u in users) {
        USERS.document(u.id).set(u)
    }
}
