package com.example.projekt.fragments

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.projekt.R
import com.example.projekt.viewmodels.RestaurantViewModel
import kotlinx.android.synthetic.main.fragment_detalis.view.*
import kotlinx.android.synthetic.main.fragment_list.view.*
import kotlinx.android.synthetic.main.fragment_profile.*
import com.bumptech.glide.Glide
import com.example.projekt.models.Restaurant
import kotlinx.android.synthetic.main.costum_row.view.*
import kotlinx.android.synthetic.main.fragment_detalis.*
import kotlinx.android.synthetic.main.fragment_detalis.view.price
import kotlinx.android.synthetic.main.fragment_edit.*
import kotlinx.android.synthetic.main.fragment_edit.view.*

class DetalisFragment : Fragment() {

    private val mainViewModel: RestaurantViewModel by activityViewModels()
    private lateinit var image : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(savedInstanceState.toString(), "Details!")

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
        view.btn_loc.setOnClickListener{
            findNavController().navigate(R.id.action_detalisFragment_to_mapsFragment)
        }

        view.change_img.setOnClickListener{
            // Check runtime permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (context?.checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    // permission denied
                    val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    // show popup to request runtime permission
                    requestPermissions(permissions, DetalisFragment.PERMISSION_CODE)
                }
                else {
                    // Permission already grannted
                    pickImageFromGallery()
                }
            }
            else {
                // system OS is < Mashmallow
                pickImageFromGallery()
            }
        }

        return view
    }

    private fun pickImageFromGallery() {
        // Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMG_PICK_CODE)
    }

    companion object {
        private const val IMG_PICK_CODE = 1000 // img pick code
        private const val PERMISSION_CODE = 1001 // Permission code
    }

    // Handle permission request result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission from popup granted
                    pickImageFromGallery()
                }
                else {
                    Toast.makeText(context, "Permission denied!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Handle image pick result
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMG_PICK_CODE) {
            image = data?.data.toString()
            Glide.with(requireContext())
                .load(data?.data)
                .into(img)
            mainViewModel.clickedItem.image_url = image
            for (i in 0 until mainViewModel.restaurants.value!!.size){
                if (mainViewModel.clickedItem.id == mainViewModel.restaurants.value!![i].id){
                    mainViewModel.restaurants.value!![i].image_url = image
                }
            }
        }
    }

}