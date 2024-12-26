package com.company.smartcarpark.data.model

data class Vehicle(
    val licensePlate: String = "",
    val entryTime: String = "",
    var exitTime: String = "",
    val imageSrc: String = ""
)
