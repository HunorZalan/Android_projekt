package com.example.projekt.models

data class Restaurant(
    val id : Int,
    val name : String,
    val address : String,
    val city : String,
    val state : String,
    val area : String,
    val postal_code : String,
    val country : String,
    val phone : String,
    val lat : String,
    val lng : String,
    val price : Double,
    val reserve_url : String,
    val mobile_reserve_url : String,
    val image_url : String,
    var fav : Boolean = false
)
