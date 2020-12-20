package com.example.projekt.models

data class Restaurant(
    var id : Int,
    var name : String,
    var address : String,
    var city : String,
    var state : String,
    var area : String,
    var postal_code : String,
    var country : String,
    var phone : String,
    var lat : String,
    var lng : String,
    var price : Double,
    var reserve_url : String,
    var mobile_reserve_url : String,
    var image_url : String,
    var fav : Boolean = false
)
