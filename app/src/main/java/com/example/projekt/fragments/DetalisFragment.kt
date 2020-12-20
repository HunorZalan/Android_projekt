package com.example.projekt.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.projekt.R
import com.example.projekt.viewmodels.RestaurantViewModel
import kotlinx.android.synthetic.main.fragment_detalis.view.*
import kotlinx.android.synthetic.main.fragment_list.view.*
import kotlinx.android.synthetic.main.fragment_profile.*
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.costum_row.view.*
import kotlinx.android.synthetic.main.fragment_detalis.view.price

class DetalisFragment : Fragment() {

    private val mainViewModel: RestaurantViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(savedInstanceState.toString(), "Error Details!")

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view : View = inflater.inflate(R.layout.fragment_detalis, container, false)

        if (mainViewModel.clickedItem.fav){
            view.btn_fav.setImageResource(R.drawable.ic_baseline_favorite)
        }
        else{
            view.btn_fav.setImageResource(R.drawable.ic_baseline_favorite_border)
        }

        view.name.text = mainViewModel.clickedItem.name
        Glide.with(requireContext())
            .load(mainViewModel.clickedItem.image_url)
            .into(view.img)
        view.address.text = mainViewModel.clickedItem.address
        view.price.text = mainViewModel.clickedItem.price.toString()
        view.city.text = mainViewModel.clickedItem.city
        view.state.text = mainViewModel.clickedItem.state
        view.area.text = mainViewModel.clickedItem.area
        view.postal.text = mainViewModel.clickedItem.postal_code
        view.country.text = mainViewModel.clickedItem.country
        view.phone.text = mainViewModel.clickedItem.phone
        view.reserve.text = mainViewModel.clickedItem.reserve_url
        view.mobile.text = mainViewModel.clickedItem.mobile_reserve_url

        view.btn_fav.setOnClickListener{
            if (!mainViewModel.clickedItem.fav){
                view.btn_fav.setImageResource(R.drawable.ic_baseline_favorite)
                mainViewModel.clickedItem.fav = true
                for (i in 0 until mainViewModel.restaurants.value!!.size){
                    if (mainViewModel.clickedItem.id == mainViewModel.restaurants.value!![i].id){
                        mainViewModel.restaurants.value!![i].fav = true
                    }
                }
            }
            else{
                view.btn_fav.setImageResource(R.drawable.ic_baseline_favorite_border)
                mainViewModel.clickedItem.fav = false
                for (i in 0 until mainViewModel.restaurants.value!!.size){
                    if (mainViewModel.clickedItem.id == mainViewModel.restaurants.value!![i].id){
                        mainViewModel.restaurants.value!![i].fav = false
                    }
                }
            }
        }

        return view
    }

}