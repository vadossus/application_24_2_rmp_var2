package com.example.application_24_2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.navigation.fragment.NavHostFragment
import com.example.application_24_2.R

class Fragment1 : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {


        val fragmentLayout = inflater.inflate(R.layout.fragment_1, container,false)

        val navController = NavHostFragment.findNavController(this)

        val button2 = fragmentLayout.findViewById<AppCompatButton>(R.id.button2)
        val button3 = fragmentLayout.findViewById<AppCompatButton>(R.id.button3)
        val button4 = fragmentLayout.findViewById<AppCompatButton>(R.id.button4)

        button2.setOnClickListener {
            navController.navigate(R.id.fragment2)
        }

        button3.setOnClickListener {
            navController.navigate(R.id.fragment3)
        }

        button4.setOnClickListener {
            navController.navigate(R.id.fragment4)
        }

        return fragmentLayout
    }

}