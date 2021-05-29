package com.example.projekt.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projekt.R
import com.example.projekt.adapters.RestaurantAdapter
import com.example.projekt.models.Restaurant
import com.example.projekt.viewmodels.RestaurantViewModel
import kotlinx.android.synthetic.main.costum_row.view.*
import kotlinx.android.synthetic.main.fragment_list.view.*
import kotlinx.android.synthetic.main.fragment_profile.*
import java.util.*

class ListFragment : Fragment(), RestaurantAdapter.OnItemClickListener {

    private lateinit var recycler : RecyclerView
    private lateinit var list : MutableList<Restaurant>
    private lateinit var newList : MutableList<Restaurant>

    private val mainViewModel: RestaurantViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(savedInstanceState.toString(), "List!")

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        requireActivity().findViewById<View>(R.id.bottom_nav).visibility = View.VISIBLE
        val view: View = inflater.inflate(R.layout.fragment_list, container, false)

        list = mainViewModel.restaurants.value!!
        newList = mutableListOf()
        show(list, view)

        view.search.addTextChangedListener{
            val str : String = view.search.text.toString()
            Log.d(str,"Search:$str")

            if (str.isEmpty()){
                show(list, view)
            }
            else{
                newList.clear()
                for (i in list){
                    if (i.name.toUpperCase(Locale.ROOT).contains(str.toUpperCase(Locale.ROOT))) {
                        newList.add(i)
                    }
                }
                show(newList, view)
            }
        }

        return view
    }

    private fun show(list : MutableList<Restaurant>, view : View){
        recycler = view.recyclerview
        val adapter = RestaurantAdapter(list, this@ListFragment, requireContext())
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(activity)
        recycler.setHasFixedSize(true)
    }

    override fun onItemClick(position: Int) {
        val myClickedItem : Restaurant = list[position]
        mainViewModel.clickedItem = myClickedItem
        Navigation.findNavController(requireView()).navigate(R.id.action_listFragment_to_detalisFragment)
    }

}