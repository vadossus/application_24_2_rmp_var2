package com.example.fragment_application_24_2

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class Fragment3 : Fragment() {

    private lateinit var incomeAdapter: IncomeAdapter
    private lateinit var incomeList: MutableList<Income>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_3, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomNavView = view.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    findNavController().navigate(R.id.fragment1)
                    true
                }
                R.id.nav_search -> {
                    findNavController().navigate(R.id.fragment2)
                    true
                }
                R.id.nav_profile -> {
                    findNavController().navigate(R.id.fragment4)
                    true
                }
                else -> false
            }
        }

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        incomeList = mutableListOf()
        incomeAdapter = IncomeAdapter(incomeList, {})
        recyclerView.adapter = incomeAdapter

        val sharedPreferences = requireContext().getSharedPreferences("income_data", MODE_PRIVATE)
        val incomeSet = sharedPreferences.getStringSet("income_list", emptySet())
        incomeList.clear()
        incomeList.addAll(incomeSet!!.map { Income.fromString(it) })
        incomeAdapter.notifyDataSetChanged()
    }

}