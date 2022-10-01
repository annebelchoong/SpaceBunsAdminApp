package com.example.spacebunsadminapp.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase

class OrderViewModel: ViewModel() {

    private val orders = MutableLiveData<List<Orders>>()

    private val col = Firebase.firestore.collection("orders")

    init {
        col.addSnapshotListener { value,_ -> orders.value = value?.toObjects() }
    }

    fun init() = Unit

    fun get(id:String) = orders.value?.find { it.orderId == id }

    fun getAll() = orders

    fun delete(id: String){
        col.document(id).delete()
    }

    fun deleteAll(){
        orders.value?.forEach { col.document(it.orderId).delete() }
    }

    fun set(o:Orders){
        col.document(o.orderId).set(o)
    }
}