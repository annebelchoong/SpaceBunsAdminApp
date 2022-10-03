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

import java.time.LocalDateTime
import java.util.*

data class Data(
    var id: String = ""
)

data class Voucher(
    @DocumentId
    var voucherId: String = "",
    var voucherCode: String = "",
    var discountPercentage: Double = 0.00,
    var usedCount: Int = 0,
)

val VOUCHERS = Firebase.firestore.collection("vouchers")

data class DonationEvent(
    @DocumentId
    var donationEventId: String = "",
    var donationEventName: String = "",
    var donationGoal: Double = 0.00,
    var donationStartDate: Date = Date() // current Date
) {
    // TODO(1): Additional field: [count] and [toString]
    @get:Exclude    // excluded from firestore
    var count: Int = 0
    override fun toString() = donationEventName     // for spinner
}

val DONATIONS_EVENT = Firebase.firestore.collection("donationEvents")

data class Donation(
    @DocumentId
    var donationId: String = "",
    var donorName: String = "",
    var donationAmount: Double = 0.00,

    var donationDate: Date = Date() // current Date
)

val DONATIONS = Firebase.firestore.collection("donations")

