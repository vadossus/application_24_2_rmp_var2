package com.example.application_24_2
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class Fragment4 : Fragment(), EditTaskDialogFragment.EditTaskDialogListener {

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
        return inflater.inflate(R.layout.fragment_4, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        incomeList = mutableListOf()
        incomeAdapter = IncomeAdapter(incomeList) { income ->
            lifecycleScope.launch {
                incomeDao.delete(income)
                loadDataFromDatabase()
            }
        }
        loadDataFromDatabase()
        recyclerView.adapter = incomeAdapter

        val buttonDelete: Button = view.findViewById(R.id.buttonDelete)
        buttonDelete.setOnClickListener {
            val selectedItems = incomeAdapter.getSelectedItems()
            if (selectedItems.isNotEmpty()) {
                lifecycleScope.launch {
                    for (income in selectedItems) {
                        incomeDao.delete(income)
                    }
                    loadDataFromDatabase()
                }
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

    private fun loadDataFromDatabase() {
        lifecycleScope.launch {
            incomeList.clear()
            incomeList.addAll(incomeDao.getAllIncomes())
            incomeAdapter.notifyDataSetChanged()
        }
    }

    override fun onTaskEdited(income: Income) {
        lifecycleScope.launch {
            incomeDao.update(income)
            loadDataFromDatabase()
            val fragment3 = (requireActivity() as? MainActivity)?.supportFragmentManager?.findFragmentById(R.id.fragment3)
            (requireActivity() as? MainActivity)?.updateFragment3Data()
        }
    }
}
