package com.example.projekt.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projekt.R
import com.example.projekt.adapters.RestaurantAdapter
import com.example.projekt.models.Restaurant
import com.example.projekt.viewmodels.RestaurantViewModel
import kotlinx.android.synthetic.main.fragment_list.view.*
import java.util.*

class ListFragment : Fragment(), RestaurantAdapter.OnItemClickListener {

    private lateinit var recycler : RecyclerView
    private val mainViewModel: RestaurantViewModel by activityViewModels()
    private lateinit var list : MutableList<Restaurant>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(savedInstanceState.toString(), "Error List!")

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        requireActivity().findViewById<View>(R.id.bottom_nav).visibility = View.VISIBLE
        var view: View = inflater.inflate(R.layout.fragment_list, container, false)

        list = mainViewModel.restaurants.value!!

        recycler = view.recyclerview
        var adapter = RestaurantAdapter(list, this@ListFragment)
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(activity)
        recycler.setHasFixedSize(true)


        return view
    }

    override fun onItemClick(position: Int) {
        var myclickedItem : Restaurant = list[position]
        mainViewModel.clickedItem = myclickedItem
        Navigation.findNavController(requireView()).navigate(R.id.action_listFragment_to_detalisFragment)
    }

}