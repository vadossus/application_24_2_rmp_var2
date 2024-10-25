package com.example.fragment_application_24_2

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.snackbar.Snackbar


class Fragment2 : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editDate: EditText = view.findViewById(R.id.editDate)
        val editDescription: EditText = view.findViewById(R.id.editDescription)
        val editAmount: EditText = view.findViewById(R.id.editAmount)
        val buttonSave: AppCompatButton = view.findViewById(R.id.buttonSave)

        buttonSave.setOnClickListener {
            val date = editDate.text.toString()
            val description = editDescription.text.toString()
            val amount = editAmount.text.toString().toDoubleOrNull()

            if (date.isNotEmpty() && description.isNotEmpty() && amount != null) {
                val sharedPreferences = requireContext().getSharedPreferences("income_data", MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("date", date)
                editor.putString("description", description)
                editor.putString("amount", amount.toString())
                editor.apply()

                Snackbar.make(it, "Данные заполнены", Snackbar.LENGTH_SHORT).show()
            }
            else
            {
                Snackbar.make(it, "Не все поля заполнены", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}