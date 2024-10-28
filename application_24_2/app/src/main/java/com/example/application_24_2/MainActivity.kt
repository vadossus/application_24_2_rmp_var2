package com.example.application_24_2

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.application_24_2.EditTaskDialogFragment
import com.example.application_24_2.Fragment3
import com.example.application_24_2.R
import com.example.application_24_2.Income
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), EditTaskDialogFragment.EditTaskDialogListener {
    private lateinit var navController: androidx.navigation.NavController
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val host = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment? ?: return
        navController = host.navController

        val sidebar = findViewById<NavigationView>(R.id.nav_view)
        val drawer_layout = findViewById<DrawerLayout>(R.id.main)
        sidebar?.setupWithNavController(navController)

        val appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout = drawer_layout)
        val toolBar = findViewById<Toolbar>(R.id.toolbar)
        toolBar.setupWithNavController(navController,appBarConfiguration)

        val bottomBar = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomBar?.setupWithNavController(navController)

        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_right_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_fragment2 -> {
                navController.navigate(R.id.fragment2)
                return true
            }
            R.id.action_fragment3 -> {
                navController.navigate(R.id.fragment3)
                return true
            }
            R.id.action_fragment4 -> {
                navController.navigate(R.id.fragment4)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onTaskEdited(income: Income) {
        updateFragment3Data()
    }

    fun updateFragment3Data() {
        val fragment3 = supportFragmentManager.findFragmentById(R.id.fragment3) as? Fragment3
        if (fragment3 != null) {
            Log.d("MainActivity", "Fragment3 found, updating data")
            fragment3.updateData()
        } else {
            Log.d("MainActivity", "Fragment3 not found")
        }
    }
}