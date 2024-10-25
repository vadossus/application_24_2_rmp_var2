package com.example.fragment_application_24_2

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import android.widget.Button

class Fragment4 : Fragment() {

    private lateinit var incomeAdapter: IncomeAdapter
    private lateinit var incomeList: MutableList<Income>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_4, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        incomeList = mutableListOf()
        incomeAdapter = IncomeAdapter(incomeList) { income ->
            incomeList.remove(income)
            incomeAdapter.notifyDataSetChanged()
        }
        recyclerView.adapter = incomeAdapter

        val buttonDelete: Button = view.findViewById(R.id.buttonDelete)
        buttonDelete.setOnClickListener {
            val selectedItems = incomeAdapter.getSelectedItems()
            if (selectedItems.isNotEmpty()) {
                for (income in selectedItems) {
                    incomeList.remove(income)
                }
                incomeAdapter.notifyDataSetChanged()
                Snackbar.make(view, "${selectedItems.size} items deleted", Snackbar.LENGTH_LONG).show()
            } else {
                Snackbar.make(view, "No items selected", Snackbar.LENGTH_SHORT).show()
            }
        }

        // Загрузите данные из SharedPreferences или другого места
        val sharedPreferences = requireContext().getSharedPreferences("income_data", MODE_PRIVATE)
        val date = sharedPreferences.getString("date", "")
        val description = sharedPreferences.getString("description", "")
        val amount = sharedPreferences.getString("amount", "")

        if (date != null && description != null && amount != null) {
            val income = Income(date, description, amount.toDoubleOrNull() ?: 0.0)
            incomeList.add(income)
            incomeAdapter.notifyDataSetChanged()
        }
    }
}
