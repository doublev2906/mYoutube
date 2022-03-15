package com.doublev.myoutube

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.doublev.myoutube.adapter.MainViewPagerAdapter
import com.doublev.myoutube.api.YoutubeApi
import com.doublev.myoutube.api.YoutubeService
import com.doublev.myoutube.api.listReturn
import retrofit2.converter.gson.GsonConverterFactory
import com.doublev.myoutube.databinding.ActivityMainBinding
import com.doublev.myoutube.model.Channel
import com.doublev.myoutube.model.YoutubeModel
import com.doublev.myoutube.model.YoutubeSearchResultModel
import com.doublev.myoutube.model.YoutubeVideoModel
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.StringBuilder


const val BASE_URL = "https://youtube.googleapis.com/youtube/v3/"

class MainActivity : AppCompatActivity() {
    //use view binding to map view in activity_main layout
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mapBottomNavWithViewPager2()

    }

    fun <T> createMultipleId(list: List<T>){

    }


    /**
     * Nối bottom navigation bar với main viewpager2 và xử
     * lý sự kiện khi vuốt view pager hoặc chọn menu item
     */
    private fun mapBottomNavWithViewPager2() {
        val mainViewPager2Adapter = MainViewPagerAdapter(this)
        binding.mainViewpager2.adapter = mainViewPager2Adapter

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
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