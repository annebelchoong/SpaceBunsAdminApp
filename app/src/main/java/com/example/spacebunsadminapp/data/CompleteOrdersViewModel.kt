package com.example.spacebunsadminapp.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase

class CompleteOrdersViewModel: ViewModel() {

    private val orders = MutableLiveData<List<Orders>>()

//    private val col = Firebase.firestore.collection("orders")
//    private val col = Firebase.firestore.collection("usersTest").document("U001").collection("orders")

    init {
        ORDERS.addSnapshotListener { value,_ -> orders.value = value?.toObjects() }
    }

    fun init() = Unit

    fun get(id:String) = orders.value?.find { it.orderId == id }

    fun getAll() = orders

    fun delete(id: String){
        ORDERS.document(id).delete()
    }

    fun deleteAll(){
        orders.value?.forEach { ORDERS.document(it.orderId).delete() }
    }

    fun set(o:Orders){
        ORDERS.document(o.orderId).set(o)
    }
}