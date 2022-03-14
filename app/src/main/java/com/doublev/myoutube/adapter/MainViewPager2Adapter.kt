package com.doublev.myoutube.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.doublev.myoutube.fragment.HomeFragment
import com.doublev.myoutube.fragment.MyVideoFragment
import com.doublev.myoutube.fragment.UserFragment

class MainViewPager2Adapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity){

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> HomeFragment()
            1 -> MyVideoFragment()
            2 -> UserFragment()
            else -> HomeFragment()
        }
    }
}