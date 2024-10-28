package com.example.application_24_2

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
import androidx.lifecycle.lifecycleScope
import com.example.application_24_2.Income
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch


class Fragment2 : Fragment() {

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
        return inflater.inflate(R.layout.fragment_2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val datePicker: DatePicker = view.findViewById(R.id.datePicker)
        val editDescription: EditText = view.findViewById(R.id.editDescription)
        val editAmount: EditText = view.findViewById(R.id.editAmount)
        val buttonSave: AppCompatButton = view.findViewById(R.id.buttonSave)

        buttonSave.setOnClickListener {
            val year = datePicker.year
            val month = datePicker.month
            val day = datePicker.dayOfMonth
            val description = editDescription.text.toString()
            val amount = editAmount.text.toString().toDoubleOrNull()

            if (description.isNotEmpty() && amount != null) {
                val income = Income(0, year, month, day, description, amount)
                lifecycleScope.launch {
                    incomeDao.insert(income)
                }
                Snackbar.make(it, "Данные заполнены", Snackbar.LENGTH_SHORT).show()
            } else {
                Snackbar.make(it, "Не все поля заполнены", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}