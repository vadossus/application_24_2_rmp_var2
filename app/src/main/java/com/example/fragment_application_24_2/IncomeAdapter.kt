package com.example.fragment_application_24_2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class IncomeAdapter(
    private var incomeList: MutableList<Income>,
    private val onIncomeRemoved: (Income) -> Unit
) : RecyclerView.Adapter<IncomeAdapter.IncomeViewHolder>() {

    private var selectedItems = mutableListOf<Income>()

    class IncomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        val amountTextView: TextView = itemView.findViewById(R.id.amountTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IncomeViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.income_item, parent, false)
        return IncomeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: IncomeViewHolder, position: Int) {
        val income = incomeList[position]
        holder.dateTextView.text = income.date
        holder.descriptionTextView.text = income.description
        holder.amountTextView.text = income.amount.toString()

        holder.itemView.setOnClickListener {
            if (selectedItems.contains(income)) {
                selectedItems.remove(income)
                holder.itemView.setBackgroundColor(holder.itemView.context.getColor(android.R.color.transparent))
            } else {
                selectedItems.add(income)
                holder.itemView.setBackgroundColor(holder.itemView.context.getColor(android.R.color.darker_gray))
            }
        }

        holder.itemView.setOnLongClickListener {
            removeIncome(income)
            true
        }
    }

    override fun getItemCount(): Int {
        return incomeList.size
    }

    fun getSelectedItems(): List<Income> {
        return selectedItems
    }

    fun removeIncome(income: Income) {
        incomeList.remove(income)
        onIncomeRemoved(income)
        notifyDataSetChanged()
    }
}