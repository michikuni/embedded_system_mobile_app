package com.company.smartcarpark.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.company.smartcarpark.data.model.GateStatus
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class GateControlsViewModel : ViewModel() {
    fun fetchStatusGate(){
        val database = FirebaseDatabase.getInstance()
        val statusRef = database.getReference("status")
        statusRef.child("door1").child("isOpen").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val isOpen = snapshot.getValue(GateStatus::class.java)
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase error", "Failed to fetch vehicles: ${error.message}")
            }
        })
        statusRef.child("door2").child("isOpen").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val isOpen = snapshot.getValue(GateStatus::class.java)
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase error", "Failed to fetch vehicles: ${error.message}")
            }
        })
    }
    private val _door1State = MutableLiveData<Boolean>().apply { value = false } // False = Closed
    val door1State: LiveData<Boolean> = _door1State

    private val _door2State = MutableLiveData<Boolean>().apply { value = true } // True = Open
    val door2State: LiveData<Boolean> = _door2State

    fun toggleDoor1() {
        _door1State.value = !_door1State.value!!
    }

    fun toggleDoor2() {
        _door2State.value = !_door2State.value!!
    }
}
