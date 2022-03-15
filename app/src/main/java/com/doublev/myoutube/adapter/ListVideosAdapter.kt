package com.doublev.myoutube.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.doublev.myoutube.api.YoutubeService
import com.doublev.myoutube.databinding.VideoItemLayoutBinding
import com.doublev.myoutube.model.Channel
import com.doublev.myoutube.model.YoutubeModel
import com.doublev.myoutube.model.YoutubeVideoModel
import retrofit2.Call
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit
import retrofit2.Callback
import retrofit2.Response

//Adapter chung cho các list video
class ListVideosAdapter(val listData : List<YoutubeVideoModel>)
    : RecyclerView.Adapter<ListVideosAdapter.ViewHolder>() {

    private lateinit var context:Context

    class ViewHolder(val binding: VideoItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        //khởi tạo viewbinding
        val binding = VideoItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        //lưu trữ biến môi trường đẻ dùng khi cần
        context = parent.context
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listData[position]
        holder.binding.tvTitle.text = data.snippet.title

        //dung thư viện glide để load ảnh từ url
        Glide.with(context).load(data.snippet.thumbnails.medium.url)
            .into(holder.binding.imgVideoThumbnail)
        holder.binding.tvDuration.text = data.contentDetails.convertDuration()
        val time = calculatorTimeAgo(data.snippet.publishedAt.time)
        val view = calculatorViews(data.statistics.viewCount)
        holder.binding.tvInfromation.text =
            "${data.snippet.channelTitle} . $view . $time"

        //do api của video lấy được không có thông tin về channel nên
        //phải call thêm 1 lần api
        YoutubeService.youtubeApi.getChannel(id = data.snippet.channelId)
            .enqueue(object:Callback<YoutubeModel<Channel>>{
                override fun onResponse(
                    call: Call<YoutubeModel<Channel>>,
                    response: Response<YoutubeModel<Channel>>
                ) {
                    val channelData = response.body()
                    if (channelData != null){
                        val items = channelData.items
                        val thumbnailUrl = items[0].snippet.thumbnails.default.url
                        Glide.with(context).load(thumbnailUrl)
                            .into(holder.binding.imgChannelThumbnail)
                    }
                }

                override fun onFailure(call: Call<YoutubeModel<Channel>>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
    }


    /**
     * Tính toán lượt view rút gọn
     */
    private fun calculatorViews(viewCounts : Long) : String{
        if (viewCounts < 1000){
            return viewCounts.toString()+"views"
        } else if (viewCounts < 10000){
            val count:Float = (viewCounts/1000).toFloat()
            return "$count K views"
        } else if (viewCounts < 1000000){
            val count:Int = (viewCounts/1000).toInt()
            return "$count K views"
        } else if(viewCounts < 10000000){
            val count = (viewCounts/1000000).toFloat()
            return "$count M views"
        }
        else if (viewCounts < 1000000000){
            val count = (viewCounts/1000000).toInt()
            return "$count M views"
        } else{
            val count = (viewCounts/1000000000).toFloat()
            return "$count B views"
        }
    }

    //Tính toán thời gian đăng video
    private fun calculatorTimeAgo(publishedTime: Long): String? {
        val currentDate = Calendar.getInstance().time
        val timeDifferenceMilliseconds = currentDate.time - publishedTime
        val diffSeconds = timeDifferenceMilliseconds / 1000
        val diffMinutes = timeDifferenceMilliseconds / (60 * 1000)
        val diffHours = timeDifferenceMilliseconds / (60 * 60 * 1000)
        val diffDays = timeDifferenceMilliseconds / (60 * 60 * 1000 * 24)
        val diffWeeks = timeDifferenceMilliseconds / (60 * 60 * 1000 * 24 * 7)
        val diffMonths = (timeDifferenceMilliseconds / (60 * 60 * 1000 * 24 * 30.41666666)).toLong()
        val diffYears = timeDifferenceMilliseconds / (60.toLong() * 60 * 1000 * 24 * 365)
        return if (diffSeconds < 1) {
            "less than a second"
        } else if (diffMinutes < 1) {
            "$diffSeconds seconds ago"
        } else if (diffHours < 1) {
            "$diffMinutes minutes ago"
        } else if (diffDays < 1) {
            "$diffHours hours ago"
        } else if (diffWeeks < 1) {
            "$diffDays days ago"
        } else if (diffMonths < 1) {
            "$diffWeeks weeks ago"
        } else if (diffYears < 1) {
            "$diffMonths months ago"
        } else {
            "$diffYears years ago"
        }
    }

    override fun getItemCount(): Int = listData.size
}