package com.doublev.myoutube

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.doublev.myoutube.adapter.MainViewPager2Adapter
import com.doublev.myoutube.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //use view binding to map view in activity_main layout
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mapBottomNavWithViewPager2()
    }

    /**
     * Nối bottom navigation bar với main viewpager2 và xử
     * lý sự kiện khi vuốt view pager hoặc chọn menu item
     */
    private fun mapBottomNavWithViewPager2() {
        val mainViewPager2Adapter = MainViewPager2Adapter(this)
        binding.mainViewpager2.adapter = mainViewPager2Adapter

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.menu_nav_home -> binding.mainViewpager2.currentItem = 0
                R.id.menu_nav_my_video -> binding.mainViewpager2.currentItem = 1
                R.id.menu_nav_user -> binding.mainViewpager2.currentItem = 2
            }
            true
        }

        binding.mainViewpager2.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    when (position) {
                        0 -> binding.bottomNavigationView.menu.findItem(R.id.menu_nav_home)
                                .isChecked = true
                        1 -> binding.bottomNavigationView.menu.findItem(R.id.menu_nav_my_video)
                            .isChecked = true
                        2 -> binding.bottomNavigationView.menu.findItem(R.id.menu_nav_user)
                            .isChecked = true


                    }
                }
            })
    }
}