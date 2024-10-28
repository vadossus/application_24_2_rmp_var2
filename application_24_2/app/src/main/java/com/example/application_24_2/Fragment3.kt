package com.example.application_24_2

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.application_24_2.Income
import com.example.application_24_2.IncomeAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

class Fragment3 : Fragment() {

    private lateinit var incomeAdapter: IncomeAdapter
    private lateinit var incomeList: MutableList<Income>
    private lateinit var incomeDao: IncomeDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = IncomeDatabase.getDatabase(requireContext())
        incomeDao = db.incomeDao()
    }

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

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView1)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        incomeList = mutableListOf()
        incomeAdapter = IncomeAdapter(incomeList, {})
        loadDataFromDatabase()
        recyclerView.adapter = incomeAdapter
    }

    private fun loadDataFromDatabase() {
        lifecycleScope.launch {
            incomeList.clear()
            incomeList.addAll(incomeDao.getAllIncomes())
            incomeAdapter.notifyDataSetChanged()
        }
    }

    fun updateData() {
        loadDataFromDatabase()
    }
}