package com.example.spacebunsadminapp.data

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

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
