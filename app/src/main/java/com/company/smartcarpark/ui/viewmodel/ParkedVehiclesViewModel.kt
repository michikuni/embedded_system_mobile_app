package com.company.smartcarpark.ui.viewmodel

import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.company.smartcarpark.data.model.Vehicle
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.Date
import java.util.Locale

class ParkedVehiclesViewModel : ViewModel() {
    private val _all_vehicles_data = MutableLiveData<List<Vehicle>>()
    val all_vehicles_data: LiveData<List<Vehicle>> get() = _all_vehicles_data

    init {
        fetchAllVehiclesData()
    }

    private fun fetchAllVehiclesData(){
        val database = Firebase.database.reference
        val ref = database.child("vehicles")
        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val vehiclesList = mutableListOf<Vehicle>()

                for (vehicleSnapshot in snapshot.children){
                    val vehicle = vehicleSnapshot.getValue(Vehicle::class.java)
                    vehicle?.let {
                        vehiclesList.add(it)
                    }
                }
                _all_vehicles_data.value = vehiclesList
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase parked vehicles", "Error: ${error.message}")
            }
        })
    }
}
