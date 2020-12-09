package com.example.projekt.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.projekt.R

class ListFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(savedInstanceState.toString(), "Error List!")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        requireActivity().findViewById<View>(R.id.bottom_nav).visibility = View.VISIBLE
        var view: View = inflater.inflate(R.layout.fragment_list, container, false)

        return view
    }

}