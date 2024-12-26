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

class LoginViewModel : ViewModel(){
    private val _login_data = MutableLiveData<List<Admin>>()
    val login_data: LiveData<List<Admin>> get() = _login_data

    fun fetchLoginData(){
        val database = Firebase.database.reference
        val ref = database.child("admin")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val adminList = mutableListOf<Admin>()
                for (adminSnapshot in snapshot.children){
                    val admin = adminSnapshot.getValue(Admin::class.java)
                    if (admin != null){
                        adminList.add(admin)
                    }
                }
                _login_data.value = adminList
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase error", "Failed to fetch vehicles: ${error.message}")
            }
        })
    }
}