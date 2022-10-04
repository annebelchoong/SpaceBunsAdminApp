package com.example.spacebunsadminapp.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects

class UserViewModal: ViewModel() {

    private var users = listOf<User>() // Original/actual data
    private val result = MutableLiveData<List<User>>() // live data - Search + filter + sort result

    private var name = ""       // Search
    private var categoryId = "" // Filter
    private var field = ""      // Sort
    private var reverse = false // Sort

    init { //init only run 1 time
        // Read all fruits -> launch block
        //           Populate [category] field
        viewModelScope.launch {
            val categories = CATEGORIES.get().await().toObjects<Category>()
            //move inside snapshot listener if required

            USERS.addSnapshotListener { value, _ ->
                if (value == null) return@addSnapshotListener //run everytime has changes

                users = value.toObjects<User>()

                for (u in users) {
                    u.category = categories.find { it.id == u.categoryId } !! //not null or // ?: Category()
                }

                updateResult()
            }
        }
    }
    //  Get search + filter + sort result
    private fun updateResult() {
        var list = users
        // Search + filter
        list = list.filter {
            it.name.contains(name, true) &&
                    (categoryId == "" || categoryId == it.categoryId)
        }
        // Sort
        list = when (field) {
            "id"     -> list.sortedBy { it.id }
            "name"  -> list.sortedBy { it.name }
            "email" -> list.sortedBy { it.email }
            else    -> list
        }
        if (reverse) list = list.reversed()
        result.value = list //result is live data
    }
    // ---------------------------------------------------------------------------------------------
    // Dummy function
    fun init() = Unit

    fun getResult() = result // Live data

    // Return all categories
    suspend fun getCategories(): List<Category> {
        return CATEGORIES.get().await().toObjects<Category>()
    }

    //  Set [name] -> update result  //vm.search("")
    fun search(name: String) {
        this.name = name
        updateResult()
    }

    // Set [categoryId] -> update result  //vm.filter("")
    fun filter(categoryId: String) {
        this.categoryId = categoryId
        updateResult()
    }

    // Set [field] and [reverse] -> update result   //vm.sort("name")
    fun sort(field: String): Boolean {
        reverse = if (this.field == field) !reverse else false

        this.field = field
        updateResult()
        return reverse
    }
    // ---------------------------------------------------------------------------------------------

    fun get(id: String): User? {
        return users.find { it.id == id }
    }
}