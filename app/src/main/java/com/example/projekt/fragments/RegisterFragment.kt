package com.example.projekt.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
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

private lateinit var mUserViewModel : UserViewModel
private  lateinit var sharedPreferences: SharedPreferences
private lateinit var imagePath: String

class RegisterFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view : View = inflater.inflate(R.layout.fragment_register, container, false)

        /*Glide.with(requireContext())
            .load(url or drawble)
            .into(img_reg)*/

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        sharedPreferences = context?.getSharedPreferences("init", Context.MODE_PRIVATE)!!

        change.setOnClickListener{
            // Check runtime permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (context?.checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    // permission denied
                    val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    // show popup to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE)
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

        reg.setOnClickListener{
            insertDataToDatabase()
        }

        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?){

    }

    @SuppressLint("CommitPrefEdits")
    private fun insertDataToDatabase(){
        var ok = true

        val username = name_reg
        val img = img_reg
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

        val phoneRegex = compile("[\\+0-9\\(\\)\\- ]{7,19}")
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
            val user = User(0, username.text.toString(), address.text.toString(), email.text.toString(), phone.text.toString(), img.toString())
            // Add Data to database
            mUserViewModel.addUser(user)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
            // Add User to sharedPreferences
            var edit = sharedPreferences.edit()
            edit.clear()
            edit.putString("username", username.text.toString())
            edit.putString("address", address.text.toString())
            edit.putString("email", email.text.toString())
            edit.putString("phone", phone.text.toString())
            edit.putString("img", img.toString())
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
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
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
            /*imagePath = data?.data.toString()
            Glide.with(requireContext())
                .load(data?.data)
                .into(img_reg)*/
            img_reg.setImageURI(data?.data)
        }
    }

}