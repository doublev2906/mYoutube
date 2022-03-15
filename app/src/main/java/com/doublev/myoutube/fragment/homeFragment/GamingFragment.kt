package com.doublev.myoutube.fragment.homeFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.doublev.myoutube.R
import com.doublev.myoutube.adapter.ListVideosAdapter
import com.doublev.myoutube.api.YoutubeService
import com.doublev.myoutube.databinding.TabFragmentLayoutBinding
import com.doublev.myoutube.model.YoutubeModel
import com.doublev.myoutube.model.YoutubeVideoModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class GamingFragment : Fragment() {

    private lateinit var binding: TabFragmentLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Khởi tạo viewbinding
        val binding = TabFragmentLayoutBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = TabFragmentLayoutBinding.bind(view)
        setDataForRecyclerView()
    }
    /**
     * Cài đặt recyclerview cho để hiện thị những video phổ biến ở
     * Việt Nam về trò chơi
     * categoryID = 20
     */

    private fun setDataForRecyclerView() {
        YoutubeService.youtubeApi.getVideosList(chart = "mostPopular",
            regionCode = "VN", maxResults = 25, videoCategoryId = "20")
            .enqueue(object : Callback<YoutubeModel<YoutubeVideoModel>> {
                override fun onResponse(
                    call: Call<YoutubeModel<YoutubeVideoModel>>,
                    response: Response<YoutubeModel<YoutubeVideoModel>>
                ) {
                    val newData = response.body()
                    val listVideo = newData?.items
                    val linearLayoutManager = LinearLayoutManager(requireContext())
                    binding.recyclerViewListVideo.layoutManager = linearLayoutManager
                    if (listVideo != null){
                        val adapter = ListVideosAdapter(listVideo)
                        binding.recyclerViewListVideo.adapter = adapter
                    }
                }

                override fun onFailure(call: Call<YoutubeModel<YoutubeVideoModel>>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
    }

}