package com.example.spacebunsadminapp.data

import com.google.firebase.firestore.Blob
import com.google.firebase.firestore.DocumentId
import java.util.*

data class Product(
    @DocumentId
    var id: String = "",
    var name: String = "",
    var desc: String = "",
    var photo: Blob = Blob.fromBytes(ByteArray(0)),  // empty bytes
    var date: Date = Date() // current Date
)