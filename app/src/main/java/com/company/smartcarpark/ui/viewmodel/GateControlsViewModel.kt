package com.company.smartcarpark.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class GateControlsViewModel : ViewModel() {
    private val _door1State = MutableLiveData<Boolean>()
    private val _door2State = MutableLiveData<Boolean>()
    private val _checking_status = MutableLiveData<String?>()

    val database = Firebase.database.reference
    val statusRef = database.child("status")
    fun fetchStatus(){

        statusRef.child("door1").child("isOpen").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val isOpen = snapshot.getValue(Boolean::class.java) ?: false
                _door1State.value=isOpen
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase error", "Failed to fetch gate 1 status: ${error.message}")
            }
        })
        statusRef.child("door2").child("isOpen").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val isOpen = snapshot.getValue(Boolean::class.java) ?: false
                _door2State.value=isOpen
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase error", "Failed to fetch gate 2 status: ${error.message}")
            }
        })
        statusRef.child("checking").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val checkingStatus = snapshot.getValue(String::class.java)
                _checking_status.value = checkingStatus
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase error", "Failed to fetch checking status: ${error.message}")
            }
        })
    }

    val door1State: LiveData<Boolean> = _door1State
    val door2State: LiveData<Boolean> = _door2State
    val checking_status: LiveData<String?> = _checking_status


    fun toggleDoor1() {
        // Kiểm tra giá trị null trước khi thay đổi
        val currentState = _door1State.value ?: false  // Nếu giá trị là null, sử dụng giá trị mặc định là false
        _door1State.value = !currentState
        statusRef.child("door1").child("isOpen").setValue(_door1State.value)
    }

    fun toggleDoor2() {
        // Kiểm tra giá trị null trước khi thay đổi
        val currentState = _door2State.value ?: false  // Nếu giá trị là null, sử dụng giá trị mặc định là false
        _door2State.value = !currentState
        statusRef.child("door2").child("isOpen").setValue(_door2State.value)
    }

    fun checkingStatus(){
        if(_checking_status.value == "false"){
            _checking_status.value = "true"
            statusRef.child("checking").setValue(_checking_status.value)
        }
        else if (_checking_status.value == "true"){
            _checking_status.value = "false"
            statusRef.child("checking").setValue(_checking_status.value)
        }
    }

}
