package com.mertyazi.footballapp.shared.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mertyazi.footballapp.R
import com.mertyazi.footballapp.databinding.ActivityFootballBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FootballActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFootballBinding
    private lateinit var mNavController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFootballBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavView: BottomNavigationView = binding.bottomNavView
        mNavController = findNavController(R.id.fragment_host)
        bottomNavView.setupWithNavController(mNavController)

        bottomNavView.setOnItemSelectedListener { item ->
            NavigationUI.onNavDestinationSelected(item, mNavController)
            mNavController.popBackStack(item.itemId, inclusive = false)
            true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(mNavController, null) || super.onSupportNavigateUp()
    }
}