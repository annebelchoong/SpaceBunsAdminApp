package com.example.spacebunsadminapp.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class OrderStatusViewModel: ViewModel() {

//    private val orders = MutableLiveData<List<OrderStatus>>()

//    private val col =  Firebase.firestore.collection("orderStatus")
    fun init() = Unit

//    fun getAllll() = orders

    suspend fun getAll(): List<OrderStatus> {
        val orderStatus = ORDERSTATUS
            .get()
            .await()
            .toObjects<OrderStatus>()

        for(os in orderStatus){
            os.count = ORDERS
                .whereEqualTo("orderStatusId", os.id)
                .get()
                .await()
                .size()
        }
        return orderStatus
    }

    suspend fun get(id:String): OrderStatus? {
        return ORDERSTATUS.document(id).get().await().toObject<OrderStatus>()
    }

    suspend fun getOrders(id:String): List<Orders>{
        val orders = ORDERS
            .whereEqualTo("orderStatusId", id)
            .get()
            .await()
            .toObjects<Orders>()

        val orderStatus = get(id)

        for (o in orders){
            o.orderStatus = orderStatus!!
        }

        return orders
    }

    suspend fun getOrderId(id:String): Orders?{
        return ORDERS.document(id).get().await().toObject<Orders>()
    }

    suspend fun getOrderDetails(orderId: String): List<OrderDetails>{
        val orderD = ORDERDETAILS
            .whereEqualTo("orderId", orderId)
            .get()
            .await()
            .toObjects<OrderDetails>()

        val orders = getOrderId(orderId)

        for (o in orderD){
            o.order = orders!!
        }
        return orderD
    }

}