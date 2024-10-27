package com.example.fragment_application_24_2

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.snackbar.Snackbar


class Fragment2 : Fragment() {

    private lateinit var incomeList: MutableList<Income>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val datePicker: DatePicker = view.findViewById(R.id.datePicker)
        val editDescription: EditText = view.findViewById(R.id.editDescription)
        val editAmount: EditText = view.findViewById(R.id.editAmount)
        val buttonSave: AppCompatButton = view.findViewById(R.id.buttonSave)

        incomeList = mutableListOf()
        loadDataFromSharedPreferences()

        buttonSave.setOnClickListener {
            val year = datePicker.year
            val month = datePicker.month
            val day = datePicker.dayOfMonth
            val description = editDescription.text.toString()
            val amount = editAmount.text.toString().toDoubleOrNull()

            if (description.isNotEmpty() && amount != null) {
                val income = Income(year, month, day, description, amount)
                incomeList.add(income)
                saveDataToSharedPreferences()

                Snackbar.make(it, "Данные заполнены", Snackbar.LENGTH_SHORT).show()
            } else {
                Snackbar.make(it, "Не все поля заполнены", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadDataFromSharedPreferences() {
        val sharedPreferences = requireContext().getSharedPreferences("income_data", MODE_PRIVATE)
        val incomeSet = sharedPreferences.getStringSet("income_list", emptySet())
        incomeList.clear()
        incomeList.addAll(incomeSet!!.map { Income.fromString(it) })
    }

    private fun saveDataToSharedPreferences() {
        val sharedPreferences = requireContext().getSharedPreferences("income_data", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val incomeSet = incomeList.map { Income.toString(it) }.toSet()
        editor.putStringSet("income_list", incomeSet)
        editor.apply()
    }
}