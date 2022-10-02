package com.example.spacebunsadminapp.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.spacebunsadminapp.ui.NewOrderFragment
import com.example.spacebunsadminapp.ui.OrderHistoryFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {

    private val mFragmentList = ArrayList<Fragment>()
    private val mFragmentTitleList = ArrayList<String>()
    override fun getItemCount(): Int {
        return mFragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        when(position){
            0 -> return NewOrderFragment()
            1 -> return OrderHistoryFragment()
            else -> return NewOrderFragment()
        }
    }

}