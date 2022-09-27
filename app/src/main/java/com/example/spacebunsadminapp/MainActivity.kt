package com.example.spacebunsadminapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.*
import com.example.spacebunsadminapp.databinding.ActivityMainBinding
import com.example.spacebunsadminapp.databinding.HeaderBinding

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
            ),
            binding.drawerLayout
        )

        setupActionBarWithNavController(nav, abc) //control the action bar. Add abc it can swap the hamburger menu
        binding.navView.setupWithNavController(nav)

        val h = binding.navView.getHeaderView(0) // find the header component from the layout
        val hb = HeaderBinding.bind(h) // using the binding method to bind the header
        hb.profilePic.setImageResource(R.drawable.ic_launcher_background) // changing the image
        hb.lblName.text = "LEE JIEUN" // changing the name
//        hb.email.text = "jieun@gmail.com" // changing the email
    }

    override fun onSupportNavigateUp(): Boolean {
        return nav.navigateUp(abc) || super.onSupportNavigateUp()
    }

}