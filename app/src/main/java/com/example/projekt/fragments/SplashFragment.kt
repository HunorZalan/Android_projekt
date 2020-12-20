package com.example.projekt.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.example.projekt.R
import com.example.projekt.viewmodels.RestaurantViewModel
import kotlinx.android.synthetic.main.fragment_splash.view.*
import java.util.prefs.AbstractPreferences

private  lateinit var sharedPreferences: SharedPreferences

class SplashFragment : Fragment() {

    private val mainViewModel: RestaurantViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(savedInstanceState.toString(), "Splash!")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        requireActivity().findViewById<View>(R.id.bottom_nav).visibility = View.GONE
        var view : View = inflater.inflate(R.layout.fragment_splash, container, false)

        /*view.img.alpha = 0f
        view.img.animate().setDuration(7000).alpha(1f).withEndAction{*/
        mainViewModel.restaurants.observe(viewLifecycleOwner, Observer {
            sharedPreferences = context?.getSharedPreferences("init", Context.MODE_PRIVATE)!!
            val init = sharedPreferences.all
            Log.d("Hello", mainViewModel.restaurants.value.toString())
            if (init.isEmpty()){
                Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_registerFragment)
            }
            else{
                Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_listFragment)
            }
        })
        mainViewModel.getAllRestaurantsFromDropBox()

        return view
    }

}