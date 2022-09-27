package com.example.spacebunsadminapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
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
                R.id.dashboard,
                R.id.user,
                R.id.order,
                R.id.product,
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