package com.example.spacebunsadminapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.*
import com.example.spacebunsadminapp.data.CategoryViewModel
import com.example.spacebunsadminapp.data.UserViewModal
import com.example.spacebunsadminapp.databinding.ActivityMainBinding
import com.example.spacebunsadminapp.databinding.HeaderBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val nav by lazy { supportFragmentManager.findFragmentById(R.id.host)!!.findNavController() }
    private lateinit var abc: AppBarConfiguration

    // View models
    private val categoryVM: CategoryViewModel by viewModels()
    private val userVM: UserViewModal by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        abc = AppBarConfiguration(
            setOf(
                R.id.loginFragment,
                R.id.dashboardFragment,
                R.id.userFragment,
                R.id.productFragment,
                R.id.vouchersFragment,
                R.id.donationsFragment,
                R.id.orderFragment,
                R.id.salesFragment,
                R.id.contactsFragment,
                R.id.settingsFragment,
                R.id.categoryFragment,
                R.id.categoryDetailsFragment,
                R.id.userDetailsFragment,
                R.id.changeEmailActivity,
                R.id.resetPasswordActivity,
            ),
            binding.drawerLayout
        )

        setupActionBarWithNavController(nav, abc) //control the action bar. Add abc it can swap the hamburger menu
        binding.navView.setupWithNavController(nav)

        // Initialize view models
        categoryVM.init()
        userVM.init()
    }

    override fun onSupportNavigateUp(): Boolean {
        return nav.navigateUp(abc) || super.onSupportNavigateUp()
    }

}