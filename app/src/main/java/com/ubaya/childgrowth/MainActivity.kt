package com.ubaya.childgrowth

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.ubaya.childgrowth.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        navController = (supportFragmentManager.findFragmentById(R.id.hostFragment) as NavHostFragment).navController
//entah knp line 27 error
//        NavigationUI.setupActionBarWithNavController(this,navController,binding.drawerLayout)
//        NavigationUI.setupWithNavController(binding.navView, navController)

        binding.bottomNav.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
//        return NavigationUI.navigateUp(navController, binding.drawerLayout) || onSupportNavigateUp()
        return onSupportNavigateUp()
    }
}