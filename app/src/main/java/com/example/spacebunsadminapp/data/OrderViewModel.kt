package com.example.spacebunsadminapp.data

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.tasks.await

class OrderViewModel: ViewModel() {

    fun init() = Unit

    suspend fun getAll(): List<Orders> {
        val orders = ORDERS
            .get()
            .await()
            .toObjects<Orders>()

        for(o in orders) {
            o.count = ORDERS
                .whereEqualTo("orderId", o.orderId)
                .get()
                .await()
                .size()
        }

        return orders
    }
}