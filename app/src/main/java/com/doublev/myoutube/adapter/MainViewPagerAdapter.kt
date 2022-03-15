package com.doublev.myoutube.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.doublev.myoutube.fragment.HomeFragment
import com.doublev.myoutube.fragment.VideoFragment
import com.doublev.myoutube.fragment.UserFragment

//Adapter cho viewpager ở màn hình chính
class MainViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> VideoFragment()
            2 -> UserFragment()
            else -> HomeFragment()
        }
    }
}