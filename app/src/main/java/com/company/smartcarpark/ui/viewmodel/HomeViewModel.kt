package com.company.smartcarpark.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.company.smartcarpark.data.model.Vehicle
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeViewModel : ViewModel() {
    private val _vehicles_data_latest = MutableLiveData<List<Vehicle>>()
    val vehicles_data_latest: LiveData<List<Vehicle>> get() = _vehicles_data_latest


    fun fetchVehiclesData(){
        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference("vehicle_last_action/infor")

        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val vehiclesLatest = snapshot.getValue(Vehicle::class.java)
                if (vehiclesLatest !=  null){
                    _vehicles_data_latest.value = listOf(vehiclesLatest)
                }
                else {
                    _vehicles_data_latest.value = emptyList()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase error", "Failed to fetch vehicles: ${error.message}")
            }
        })
    }
}
