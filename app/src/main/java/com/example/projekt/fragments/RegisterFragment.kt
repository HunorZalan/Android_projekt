package com.example.projekt.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.projekt.R
import com.example.projekt.models.User
import com.example.projekt.viewmodels.UserViewModel
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.view.*
import java.util.regex.Pattern.compile


class RegisterFragment : Fragment() {

    private lateinit var mUserViewModel : UserViewModel
    private  lateinit var sharedPreferences: SharedPreferences
    private lateinit var image : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(savedInstanceState.toString(), "Register!")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view : View = inflater.inflate(R.layout.fragment_register, container, false)

        image = "https://icons-for-free.com/download-icon-avatar+person+profile+user+icon-1320086059654790795_512.png"
        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        sharedPreferences = context?.getSharedPreferences("init", Context.MODE_PRIVATE)!!


        view.change.setOnClickListener{
            // Check runtime permission
            if (context?.checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                // permission denied
                val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                // show popup to request runtime permission
                requestPermissions(permissions, PERMISSION_CODE)
            }
            else { // Permission already grannted
                pickImageFromGallery()
            }
        }

        view.reg.setOnClickListener{
            insertDataToDatabase()
        }

        return view
    }

    @SuppressLint("CommitPrefEdits")
    private fun insertDataToDatabase(){
        var ok = true

        val username = name_reg
        val address = address_reg
        val phone = phone_reg
        val email = email_reg

        val nameRegex = compile("^[A-Z][a-zA-Z]{3,}(?: [A-Z][a-zA-Z]*){0,2}\$")
        if (username.text.isEmpty() || ! nameRegex.matcher(username.text).matches()){
            username.error = "Wrong username!"
            ok = false
        }

        if (address.text.isEmpty()){
            address.error = "Wrong address!"
            ok = false
        }

        val phoneRegex = compile("[+0-9()\\- ]{7,19}")
        if (phone.text.isEmpty() || ! phoneRegex.matcher(phone.text).matches()){
            phone.error = "Wrong phone number!"
            ok = false
        }

        val emailRegex = compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+")
        if (email.text.isEmpty() || !emailRegex.matcher(email.text).matches()){
            email.error = "Wrong email!"
            ok = false
        }

        if (ok){
            // Create User Object
            val user = User(0, username.text.toString(), address.text.toString(), email.text.toString(), phone.text.toString(), image)

            // Add Data to database
            mUserViewModel.addUser(user)
            //Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()

            // Add User to sharedPreferences
            val edit = sharedPreferences.edit()
            edit.clear()
            edit.putString("username", username.text.toString())
            edit.putString("address", address.text.toString())
            edit.putString("email", email.text.toString())
            edit.putString("phone", phone.text.toString())
            edit.putString("img", image)
            edit.apply()

            // Navigate Back
            findNavController().navigate(R.id.action_registerFragment_to_listFragment)
        }
        else{
            Toast.makeText(requireContext(), "Please correct fill out all fields!", Toast.LENGTH_LONG).show()
        }
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
                .into(img_reg)
            //img_reg.setImageURI(data?.data)
        }
    }

}