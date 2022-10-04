package com.example.spacebunsadminapp.data

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.tasks.await
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects

class CategoryViewModel : ViewModel() {
    // Dummy function
    fun init() = Unit

    suspend fun getAll(): List<Category> {
        val categories = CATEGORIES
            .get()
            .await()//await for task - has to put it inside suspend/launch
            .toObjects<Category>() //toObjects query snapshot

        for (c in categories) { //not live data
            c.count = USERS
                .whereEqualTo("categoryId", c.id)
                .get()
                .await()
                .size()
        }
        return categories
    }

    // Return a specific category
    suspend fun get(id: String): Category? {  //search for 1 record, no count
        return CATEGORIES
            .document(id)
            .get()
            .await()
            .toObject<Category>()
    }

    // Return all fruits under a specific category
    //           Populate [category] field
    suspend fun getFruits(id: String): List<User> {
        val users = USERS
            .whereEqualTo("categoryId", id)
            .get()
            .await()
            .toObjects<User>()

        val category = get(id)

        for (u in users) {
            u.category = category!! //!! will give run time error if not null
        }

        return users
    }

}