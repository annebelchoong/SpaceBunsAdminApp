package com.example.spacebunsadminapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.*
import com.example.spacebunsadminapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val nav by lazy { supportFragmentManager.findFragmentById(R.id.host)!!.findNavController() }
    private lateinit var abc: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        abc = AppBarConfiguration(
            setOf(
                R.id.dashboard,
                R.id.user,
                R.id.order,
                R.id.product,
            ),
            binding.drawerLayout
        )

        setupActionBarWithNavController(nav, abc) //control the action bar. Add abc it can swap the hamburger menu
        binding.navView.setupWithNavController(nav)
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menu?.setGroupDividerEnabled(true)
//        menuInflater.inflate(R.menu.drawer, menu)
//        return super.onCreateOptionsMenu(menu)
//    }

    override fun onSupportNavigateUp(): Boolean {
        return nav.navigateUp(abc) || super.onSupportNavigateUp()
    }

}