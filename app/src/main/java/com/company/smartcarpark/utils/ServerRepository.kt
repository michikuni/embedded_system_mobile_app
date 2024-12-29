package com.company.smartcarpark.utils

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ServerRepository {
    private val _server_url = MutableLiveData<String?>()
    val server_url: LiveData<String?> = _server_url

    val database = Firebase.database.reference
    val serverRef = database.child("server")

    fun fetchServerUrl(){
        serverRef.child("url2").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val url = snapshot.getValue(String::class.java)
                _server_url.value = url
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase error", "Failed to fetch server url ${error.message}" )
            }
        })
    }
}