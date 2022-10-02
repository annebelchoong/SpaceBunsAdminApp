package com.example.spacebunsadminapp.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class OrdersViewModel: ViewModel() {

    private var o = listOf<Orders>()
    private val orders = MutableLiveData<List<Orders>>()

    private var orderStatusId =""

    private val col = Firebase.firestore.collection("orders")

    init {
//        col.addSnapshotListener { value,_ -> orders.value = value?.toObjects() }
        viewModelScope.launch {
            val orderStatus= col.get().await().toObjects<OrderStatus>()
            //Move inside snapshot listener if required if there is any changes pn categories

            ORDERS.addSnapshotListener { value, _ ->
                if (value == null) return@addSnapshotListener

                o = value.toObjects<Orders>()

                //provide category information from fruit
                for (f in o){
                    f.orderStatus = orderStatus.find{ it.id == f.orderStatusId}!! // prefer
//                    f.category = categories.find{ it.id == f.categoryId } ?: Category()
                }

                updateResult()
            }
        }


    }

    fun init() = Unit

    fun get(id:String) = orders.value?.find { it.orderId == id }

    fun getAll() = orders

//    fun getAllProcessingOrders() {
//        orders.value?.forEach { it.orderStatus == "Processing" }
//    }

//    suspend fun getAllProcessingOrders(): List<Orders> {
//        return ORDERS.get().await().toObjects<Orders>()
//    }

    suspend fun getOrderStatus(): List<OrderStatus> {
        return col.get().await().toObjects<OrderStatus>()
    }

    fun delete(id: String){
        ORDERS.document(id).delete()
    }

    fun deleteAll(){
        orders.value?.forEach { ORDERS.document(it.orderId).delete() }
    }

    fun set(o:Orders){
        ORDERS.document(o.orderId).set(o)
    }

    private fun updateResult() {
        var list = o

        list = list.filter{
            orderStatusId == "" || orderStatusId == it.orderStatusId
        }

        orders.value = list // return as a list
    }

    fun filter(orderStatusId: String) {
        this.orderStatusId = orderStatusId
        updateResult()
    }
}