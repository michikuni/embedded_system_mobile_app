package com.company.smartcarpark.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.company.smartcarpark.data.model.Admin
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AdminViewModel : ViewModel(){
    private val _admin_data = MutableLiveData<List<Admin>>()
    val admin_data: LiveData<List<Admin>> get() = _admin_data
    init {
        fetchAdminData()
    }
    fun fetchAdminData(){
        val database = Firebase.database.reference
        val ref = database.child("admin")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val adminList = mutableListOf<Admin>()
                for (adminSnapshot in snapshot.children){
                    val admin = adminSnapshot.getValue(Admin::class.java)
                    admin?.let {
                        adminList.add(admin)
                    }
                }
                _admin_data.value = adminList
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase admin", "Error: ${error.message}")
            }
        })
    }
}