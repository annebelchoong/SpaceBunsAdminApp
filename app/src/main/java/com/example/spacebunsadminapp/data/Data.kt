package com.example.spacebunsadminapp.data

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

data class Orders(
    @DocumentId
    var orderId: String = "",
//    var customerId: String = "",
    var date: Date = Date(),
    var address: String = "",
    var orderStatusId: String = "",
    var paymentMethod: String = "",
    var subtotal: Double = 0.00,
    var totalPrice: Double = 0.00,
    var voucherId: String = "",
    val deliveryFee: Double = 3.00,

) {
    @get:Exclude
    var count : Int = 0
    var orderStatus: OrderStatus = OrderStatus()
}

data class OrderStatus(
    @DocumentId
    var id:  String ="",
    var name: String = "",
){
    @get:Exclude
    var count: Int = 0
    override fun toString() = name
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
val ORDERSTATUS = Firebase.firestore.collection("orderStatus")