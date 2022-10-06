package com.example.spacebunsadminapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.*
import com.example.spacebunsadminapp.data.CustomerViewModel
import com.example.spacebunsadminapp.data.StaffViewModal
import com.example.spacebunsadminapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val nav by lazy { supportFragmentManager.findFragmentById(R.id.host)!!.findNavController() }
    private lateinit var abc: AppBarConfiguration

//    // View models
//    private val cusVM: CustomerViewModel by viewModels()
//    private val staffVM: StaffViewModal by viewModels()

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
                R.id.changeEmailActivity,
                R.id.resetPasswordActivity,
                R.id.profileFragment,
                R.id.userUpdateFragment,
                R.id.staffFragment,
                R.id.staffInsertFragment,
                R.id.staffUpdateFragment,
                R.id.staffListFragment,
                R.id.customerFragment,
                R.id.customerListFragment,
                R.id.customerInsertFragment,
                R.id.customerUpdateFragment,

            ),
            binding.drawerLayout
        )

        setupActionBarWithNavController(nav, abc) //control the action bar. Add abc it can swap the hamburger menu
        binding.navView.setupWithNavController(nav)

//        // Initialize view models
//        cusVM.init()
//        staffVM.init()
    }

    override fun onSupportNavigateUp(): Boolean {
        return nav.navigateUp(abc) || super.onSupportNavigateUp()
    }

}