package com.example.application_24_2

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.DialogFragment
import com.example.application_24_2.R
import com.example.application_24_2.Income
import com.google.android.material.snackbar.Snackbar

class EditTaskDialogFragment(private val income: Income) : DialogFragment() {

    private lateinit var editDescription: EditText
    private lateinit var editAmount: EditText
    private lateinit var datePicker: DatePicker

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_edit_task, container, false)
        editDescription = view.findViewById(R.id.editTextDescription1)
        editAmount = view.findViewById(R.id.editTextAmount1)
        datePicker = view.findViewById(R.id.datePicker1)

        editDescription.setText(income.description)
        editAmount.setText(income.amount.toString())
        datePicker.updateDate(income.year, income.month, income.day)

        val buttonSave: AppCompatButton = view.findViewById(R.id.buttonChange)
        buttonSave.setOnClickListener {
            val year = datePicker.year
            val month = datePicker.month
            val day = datePicker.dayOfMonth
            val description = editDescription.text.toString()
            val amount = editAmount.text.toString().toDoubleOrNull()

            if (description.isNotEmpty() && amount != null) {
                val updatedIncome = Income(income.id, year, month, day, description, amount)
                (parentFragment as? EditTaskDialogListener)?.onTaskEdited(updatedIncome)
                dismiss()
            } else {
                Snackbar.make(view, "Не все поля заполнены", Snackbar.LENGTH_SHORT).show()
            }
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        val params = dialog?.window?.attributes
        params?.width = ViewGroup.LayoutParams.MATCH_PARENT
        dialog?.window?.attributes = params
    }

    interface EditTaskDialogListener {
        fun onTaskEdited(income: Income)
    }
}