package com.example.projekt.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projekt.R
import com.example.projekt.adapters.RestaurantAdapter
import com.example.projekt.models.Restaurant
import com.example.projekt.models.User
import com.example.projekt.viewmodels.RestaurantViewModel
import com.example.projekt.viewmodels.UserViewModel
import kotlinx.android.synthetic.main.fragment_list.view.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import kotlinx.android.synthetic.main.fragment_register.view.*


class ProfileFragment : Fragment(), RestaurantAdapter.OnItemClickListener {

    private val mainViewModel: RestaurantViewModel by activityViewModels()
    private lateinit var  mUserViewModel : UserViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var recycler : RecyclerView
    private var list : MutableList<Restaurant> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(savedInstanceState.toString(), "Profile!")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view : View = inflater.inflate(R.layout.fragment_profile, container, false)

        // UserViewModel
        mUserViewModel = ViewModelProvider(this).get((UserViewModel::class.java))
        mUserViewModel.readAllData.observe(viewLifecycleOwner, { user ->
            // only have 1 user
            /*view.name_prof.text = user[0].username
            view.address_prof.text = user[0].address
            view.phone_prof.text = user[0].phone
            view.email_prof.text = user[0].email*/
            Glide.with(requireContext())
                .load(user[0].img)
                .into(img_prof)
        })

        sharedPreferences = context?.getSharedPreferences("init", Context.MODE_PRIVATE)!!
        view.name_prof.text = sharedPreferences.getString("username", "")
        view.address_prof.text = sharedPreferences.getString("address", "")
        view.phone_prof.text = sharedPreferences.getString("phone", "")
        view.email_prof.text = sharedPreferences.getString("email", "")

        for (i in 0 until mainViewModel.restaurants.value!!.size){
            if (mainViewModel.restaurants.value!![i].fav){
                list.add(mainViewModel.restaurants.value!![i])
            }
        }

        recycler = view.recyclerview_fav
        val adapter = RestaurantAdapter(list, this@ProfileFragment, requireContext())
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(activity)
        recycler.setHasFixedSize(true)

        view.log_out.setOnClickListener{
            val edit = requireContext().getSharedPreferences("init", Context.MODE_PRIVATE)
            edit.edit().clear().apply()

            //mUserViewModel.deleteAllUsers()
            mUserViewModel.getUser().observe(viewLifecycleOwner) {
                mUserViewModel.deleteUser(User(it, view.name_prof.text.toString(), view.address_prof.text.toString(), view.phone_prof.text.toString(), view.email_prof.text.toString(), img_prof.toString()))
            }
            requireActivity().findViewById<View>(R.id.bottom_nav).visibility = View.GONE
            findNavController().navigate(R.id.action_profileFragment_to_splashFragment)
        }
        view.edit.setOnClickListener{
            findNavController().navigate(R.id.action_profileFragment_to_editFragment)
        }

        return view
    }

    override fun onItemClick(position: Int) {
        val myClickedItem : Restaurant = list[position]
        mainViewModel.clickedItem = myClickedItem
        Navigation.findNavController(requireView()).navigate(R.id.action_profileFragment_to_detalisFragment)
    }

}