package com.example.spacebunsadminapp.data

import com.google.firebase.firestore.Blob
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

// need have one more is the order type - delivery/pickup
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
    var count: Int = 0
    var orderStatus: OrderStatus = OrderStatus()
}

data class OrderStatus(
    @DocumentId
    var id: String = "",
    var name: String = "",
) {
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
    var quantity: Int = 0,
    var price: Double = 0.00,
    var photo: Blob = Blob.fromBytes(ByteArray(0)),
) {
    @get:Exclude
    var totalPrice: Double = 0.00
    var order: Orders = Orders()
}

val ORDERDETAILS = Firebase.firestore.collection("orderDetails")
val ORDERS = Firebase.firestore.collection("orders")
val ORDERSTATUS = Firebase.firestore.collection("orderStatus")

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
    var donationEventPhoto: Blob = Blob.fromBytes(ByteArray(0)),
    var donationStartDate: Date = Date() // current Date
) {
    // TODO(1): Additional field: [count] and [toString]
    @get:Exclude    // excluded from firestore
    var count: Int = 0
    override fun toString() = donationEventName     // for spinner
}

val DONATION_EVENTS = Firebase.firestore.collection("donationEvents")

data class Donation(
    @DocumentId
    var donationId: String = "",
    var donorName: String = "",
    var donationAmount: Double = 0.00,
    var donationEventId: String = "",
    var donationDate: Date = Date() // current Date
) {
    // TODO(2): Additional field: [dEvent]

    @get: Exclude
    var donationEvent: DonationEvent = DonationEvent()
}

val DONATIONS = Firebase.firestore.collection("donations")


data class Product(
    @DocumentId
    var id: String = "",
    var cat: String = "",
    var name: String = "",
    var desc: String = "",
    var price: Double = 0.00,
    var photo: Blob = Blob.fromBytes(ByteArray(0)),  // empty bytes
    var date: Date = Date() // current Date
)

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
