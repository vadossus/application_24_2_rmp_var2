package com.example.fragment_application_24_2

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import android.widget.Button

class Fragment4 : Fragment(), EditTaskDialogFragment.EditTaskDialogListener {

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
        loadDataFromSharedPreferences()
        recyclerView.adapter = incomeAdapter

        val buttonDelete: Button = view.findViewById(R.id.buttonDelete)
        buttonDelete.setOnClickListener {
            val selectedItems = incomeAdapter.getSelectedItems()
            if (selectedItems.isNotEmpty()) {
                for (income in selectedItems) {
                    incomeList.remove(income)
                }
                incomeAdapter.notifyDataSetChanged()
                Snackbar.make(view, "${selectedItems.size} удалено", Snackbar.LENGTH_LONG).show()
            }
        }

        val buttonChange: Button = view.findViewById(R.id.buttonChange)
        buttonChange.setOnClickListener {
            val selectedItems = incomeAdapter.getSelectedItems()
            if (selectedItems.isNotEmpty()) {
                val income = selectedItems[0]
                val editTaskDialogFragment = EditTaskDialogFragment(income)
                editTaskDialogFragment.show(childFragmentManager, "EditTaskDialogFragment")
            }
        }


    }

    private fun loadDataFromSharedPreferences() {
        val sharedPreferences = requireContext().getSharedPreferences("income_data", MODE_PRIVATE)
        val incomeSet = sharedPreferences.getStringSet("income_list", emptySet())
        incomeList.clear()
        incomeList.addAll(incomeSet!!.map { Income.fromString(it) })
        incomeAdapter.notifyDataSetChanged()
    }

    override fun onPause() {
        super.onPause()
        saveDataToSharedPreferences()
    }

    private fun saveDataToSharedPreferences() {
        val sharedPreferences = requireContext().getSharedPreferences("income_data", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val incomeSet = incomeList.map { Income.toString(it) }.toSet()
        editor.putStringSet("income_list", incomeSet)
        editor.apply()
        Log.d("Fragment4: saveDataToSharedPreferences", "....")
    }

    override fun onTaskEdited(income: Income) {
        val selectedItems = incomeAdapter.getSelectedItems()
        if (selectedItems.isNotEmpty()) {
            val position = incomeList.indexOf(selectedItems[0])
            if (position != -1) {
                incomeList[position] = income
                incomeAdapter.notifyItemChanged(position)
                saveDataToSharedPreferences()
                val fragment3 = (requireActivity() as? MainActivity)?.supportFragmentManager?.findFragmentById(R.id.fragment3)
                if (fragment3 != null) {
                    (requireActivity() as? MainActivity)?.updateFragment3Data()
                } else {
                    Log.d("Fragment4", "Fragment3 not visible")
                }
            }
        }
    }
}
