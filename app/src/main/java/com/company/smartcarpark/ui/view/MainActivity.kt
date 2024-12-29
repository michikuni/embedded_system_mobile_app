package com.company.smartcarpark.ui.view

import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.company.smartcarpark.R
import com.company.smartcarpark.databinding.ActivityMainBinding
import com.company.smartcarpark.ui.viewmodel.LoginViewModel
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var adminViewModel: LoginViewModel
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adminViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        // Set up ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Nhận thông tin từ Intent
        val adminName = intent.getStringExtra("admin_name")
        val adminUsername = intent.getStringExtra("admin_username")

        // Set up the Floating Action Button (FAB)
        binding.appBarMain.fab.setOnClickListener { view ->
            // Example action for FAB (you can modify this as needed)
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.fab).show()
        }

        // Set up the Drawer Layout and Navigation View
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView

        // Set up the Navigation Controller
        val navController = findNavController(R.id.nav_host_fragment_content_main)


        // AppBar Configuration with top-level destinations
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_home, R.id.nav_gate_controls, R.id.nav_parked_vehicles), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val headerView = navView.getHeaderView(0)
        val adminNameTextView: TextView = headerView.findViewById(R.id.name)
        val adminUsernameTextView: TextView = headerView.findViewById(R.id.username)

        adminNameTextView.text = adminName ?: "No name"
        adminUsernameTextView.text = adminUsername ?: "No username"
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
