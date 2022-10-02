package com.example.spacebunsadminapp.data

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
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

data class Donation(
    @DocumentId
    var donationId: String = "",
    var donorName: String = "",
    var donationAmount: Double = 0.00,
//    var donationDate: LocalDateTime = LocalDateTime.now(),
    var donationDate: Date = Date() // current Date
)