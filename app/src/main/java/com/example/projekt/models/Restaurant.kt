package com.example.projekt.models

import com.google.gson.annotations.SerializedName

data class Restaurant (
    @field:SerializedName("id") val idd: Long,
    @field:SerializedName("name") val name: String,
    @field:SerializedName("address") val address: String,
    @field:SerializedName("city") val city: String,
    @field:SerializedName("state") val state: String,
    @field:SerializedName("area") val area: String,
    @field:SerializedName("postal_code") val postal_code: Number,
    @field:SerializedName("country") val country: String,
    @field:SerializedName("phone") val phone: String,
    @field:SerializedName("lat") val lat: Number,
    @field:SerializedName("lng") val lng: Number,
    @field:SerializedName("price") val price: Number,
    @field:SerializedName("image_url") var image: String,
    @field:SerializedName("reserve_url") val reserve_url: String,
    @field:SerializedName("mobile_reserve_url") val mobile_reserve_url: String,
    var favorite: Boolean = false,
    var hasImage: Boolean = false,
    var insertedInDb: Boolean = false
)

object Supplier {
    val restaurants = ArrayList<Restaurant>()
    var databaseFavorites = ArrayList<Restaurant>()
}
