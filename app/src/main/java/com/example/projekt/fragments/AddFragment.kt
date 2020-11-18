package com.example.projekt.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.projekt.R
import kotlinx.android.synthetic.main.fragment_add.view.*

class AddFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(savedInstanceState.toString(), "Error Add!")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view : View = inflater.inflate(R.layout.fragment_add, container, false)

        view.add_button.setOnClickListener{
            var ok : Boolean = true
            if (view.add_name.text.isEmpty()){
                view.add_name.error = "Empty"
                ok = false
            }

            if (view.add_calorie.text.isEmpty()){
                view.add_calorie.error = "Empty"
                ok = false
            }

            if (ok == true){
                Navigation.findNavController(view).navigate(R.id.action_addFragment_to_listFragment)
            }

        }

        return view
    }

}