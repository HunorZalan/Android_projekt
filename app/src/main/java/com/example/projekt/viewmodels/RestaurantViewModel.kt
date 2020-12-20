package com.example.projekt.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projekt.models.Restaurant
import okhttp3.*
import org.json.JSONArray
import java.io.IOException

class RestaurantViewModel : ViewModel() {
    var restaurants : MutableLiveData<MutableList<Restaurant>> = MutableLiveData()
    lateinit var clickedItem : Restaurant

    fun getAllRestaurantsFromDropBox() {
        val client = OkHttpClient()
        val request =
            Request.Builder().url("https://www.dropbox.com/s/wjhpe7ho2n9b5sy/restaurants.json?dl=1")
                .build()
        client.newCall(request).enqueue(object: okhttp3.Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.d("Hello", "onFailure:${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                parseResponse(response.body!!.string())
                Log.d("Hello", "onResponse:${response.body}")
            }
        })
    }

    fun parseResponse(response: String) {
        val restaurantList = ArrayList<Restaurant>()
        val jsonArray = JSONArray(response)
        val restaurantSize = jsonArray.length()

        (0 until restaurantSize).forEach { index ->
            val jsonObject = jsonArray.getJSONObject(index)
            val apiRestaurant = Restaurant(
                jsonObject.getString("id").toInt(),
                jsonObject.getString("name"),
                jsonObject.getString("address"),
                jsonObject.getString("city"),
                jsonObject.getString("state"),
                jsonObject.getString("area"),
                jsonObject.getString("postal_code"),
                jsonObject.getString("country"),
                jsonObject.getString("phone"),
                jsonObject.getString("lat"),
                jsonObject.getString("lng"),
                jsonObject.getString("price").toDouble(),
                jsonObject.getString("reserve_url"),
                jsonObject.getString("mobile_reserve_url"),
                jsonObject.getString("image_url")
            )
            restaurantList.add(apiRestaurant)
        }
        restaurants.postValue(restaurantList)
    }
}