package com.example.fragment_application_24_2

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class EditTaskDialogFragment(private val income: Income) : DialogFragment() {

    private lateinit var listener: EditTaskDialogListener

    interface EditTaskDialogListener {
        fun onTaskEdited(income: Income)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as EditTaskDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement EditTaskDialogListener")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.dialog_edit_task, null)

            val editTextDescription = view.findViewById<EditText>(R.id.editTextDescription1)
            val editTextAmount = view.findViewById<EditText>(R.id.editTextAmount1)
            val datePicker = view.findViewById<DatePicker>(R.id.datePicker1)

            editTextDescription.setText(income.description)
            editTextAmount.setText(income.amount.toString())
            datePicker.updateDate(income.year, income.month, income.day)

            builder.setView(view)
                .setPositiveButton("Сохранить") { _, _ ->
                    val description = editTextDescription.text.toString()
                    val amount = editTextAmount.text.toString().toDoubleOrNull()
                    val year = datePicker.year
                    val month = datePicker.month
                    val day = datePicker.dayOfMonth

                    if (description.isNotEmpty() && amount != null) {
                        val editedIncome = Income(year, month, day, description, amount)
                        listener.onTaskEdited(editedIncome)
                        dismiss()
                        Log.d("EditTaskDialog", "EditTaskDialog: Передано $editedIncome")
                    }
                }
                .setNegativeButton("Отмена", null)
            builder.create()
        } ?: throw IllegalStateException("Активити не должно быть пустым")
    }
}