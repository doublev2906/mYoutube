package com.doublev.myoutube.adapter

import android.content.pm.FeatureInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.doublev.myoutube.fragment.homeFragment.*

class HomePagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 5

    override fun createFragment(position: Int): Fragment = when(position){
        0 -> FeaturedFragment()
        1 -> TrendingFragment()
        2 -> MusicFragment()
        3 -> GamingFragment()
        4 -> MoviesFragment()
        else -> FeaturedFragment()
    }

}