package com.example.projekt.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.projekt.R
import kotlinx.android.synthetic.main.fragment_splash.view.*

class SplashFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(savedInstanceState.toString(), "Error Splash!")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        requireActivity().findViewById<View>(R.id.bottom_nav).visibility = View.GONE
        var view : View = inflater.inflate(R.layout.fragment_splash, container, false)

        view.img.alpha = 0f
        view.img.animate().setDuration(7000).alpha(1f).withEndAction{
            Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_listFragment)
        }


        return view
    }

}