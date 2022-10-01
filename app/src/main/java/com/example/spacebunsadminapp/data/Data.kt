package com.example.spacebunsadminapp.data

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

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
