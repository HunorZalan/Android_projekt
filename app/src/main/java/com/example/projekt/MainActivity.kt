package com.example.projekt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.projekt.fragments.AddFragment
import com.example.projekt.fragments.InformationFragment
import com.example.projekt.fragments.ListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val listF = ListFragment()
    private val addF = AddFragment()
    private val infoF = InformationFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(savedInstanceState.toString(), "Error Main!")

        replaceFragment(listF)

        bottom_nav.setOnNavigationItemReselectedListener {
            when (it.itemId){
                R.id.list -> {replaceFragment(listF)}
                R.id.add -> {replaceFragment(addF)}
                R.id.info -> {replaceFragment(infoF)}
            }

        }

    }

    private fun replaceFragment(fragment : Fragment){
        if (fragment != null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }
}