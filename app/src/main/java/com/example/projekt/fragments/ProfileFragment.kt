package com.example.projekt.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.projekt.R
import com.example.projekt.models.User
import com.example.projekt.viewmodels.UserViewModel
import kotlinx.android.synthetic.main.fragment_list.view.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import kotlinx.android.synthetic.main.fragment_register.view.*

private  lateinit var  mUserViewModel : UserViewModel
private  lateinit var sharedPreferences: SharedPreferences

class ProfileFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(savedInstanceState.toString(), "Error Profile!")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view : View = inflater.inflate(R.layout.fragment_profile, container, false)

        // UserViewModel
        mUserViewModel = ViewModelProvider(this).get((UserViewModel::class.java))
        mUserViewModel.readAllData.observe(viewLifecycleOwner, Observer { user ->
            // only have 1 user
            /*view.name_prof.text = user[0].username
            view.address_prof.text = user[0].address
            view.phone_prof.text = user[0].phone
            view.email_prof.text = user[0].email*/
            Glide.with(requireContext())
                .load(user[0].img.toString())
                .into(img_prof)
        })

        sharedPreferences = context?.getSharedPreferences("init", Context.MODE_PRIVATE)!!
        view.name_prof.text = sharedPreferences.getString("username", "")
        view.address_prof.text = sharedPreferences.getString("address", "")
        view.phone_prof.text = sharedPreferences.getString("phone", "")
        view.email_prof.text = sharedPreferences.getString("email", "")

        view.log_out.setOnClickListener{
            val edit = requireContext().getSharedPreferences("init", Context.MODE_PRIVATE)
            edit.edit().clear().apply()
            //mUserViewModel.deleteAllUsers()
            mUserViewModel.getUser().observe(viewLifecycleOwner, ) {
                mUserViewModel.deleteUser(User(it, view.name_prof.text.toString(), view.address_prof.text.toString(), view.phone_prof.text.toString(), view.email_prof.text.toString(), img_prof.toString()))
            }
            findNavController().navigate(R.id.action_profileFragment_to_splashFragment)
        }
        view.edit.setOnClickListener{
            findNavController().navigate(R.id.action_profileFragment_to_editFragment)
        }

        return view
    }

}