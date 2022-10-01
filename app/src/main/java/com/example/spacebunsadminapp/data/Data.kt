package com.example.spacebunsadminapp.data

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime
import java.util.*

data class Orders(
    @DocumentId
    var orderId: String = "",
    var customerId: String = "",
    var dateTime: LocalDateTime,
    var address: String = "",
    var orderStatus: String = "",
    var paymentMethod: String = "",
    var totalPrice: Double = 0.00,
    var discount: Double = 0.00,

) {
    @get:Exclude
    var count : Int = 0
}

data class OrderDetails(
    @DocumentId
    var id: String = "",
    var orderId: String = "",
    var productId: String = "",
    var productName: String = "",
    var quantity: String = "",
    var price: String = "",
){
    @get:Exclude
    var totalPrice: Double = 0.00
}

val ORDERS = Firebase.firestore.collection("orders")