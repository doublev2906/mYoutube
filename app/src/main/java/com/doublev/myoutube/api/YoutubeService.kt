package com.doublev.myoutube.api

import android.os.AsyncTask
import com.doublev.myoutube.model.Channel
import com.doublev.myoutube.model.YoutubeModel
import com.doublev.myoutube.model.YoutubeSearchResultModel
import com.doublev.myoutube.model.YoutubeVideoModel
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.lang.StringBuilder

const val api_key = "AIzaSyDWhFTWwZD6WXgnNlQJaVuRMAdI9PNjJ3g"
var listReturn: List<YoutubeVideoModel>? = null

//Sử dụng retrofit để call api từ youtube api
interface YoutubeApi {
    /**
     *Lấy list video khi tìm kiếm
     * thông tin chi tiết https://developers.google.com/youtube/v3/docs/search/list
     */
    @GET("search")
    fun getVideosOnSearch(
        @Query("part") part: String = "snippet",
        @Query("channelId") channelId: String? = null,
        @Query("maxResults") maxResults: Int? = null,
        @Query("pageToken") pageToken: String? = null,
        @Query("q") q: String? = null,
        @Query("regionCode") regionCode: String = "VN",
        @Query("relatedToVideoId") relatedToVideoId: String? = null,
        @Query("regionCode") relevanceLanguage: String? = null,
        @Query("type") type: String? = null,
        @Query("key") key: String = api_key
    ): Call<YoutubeModel<YoutubeSearchResultModel>>

    /**
     * Lấy video hoặc list video của youtube api
     * thông tin chi tiết  https://developers.google.com/youtube/v3/docs/videos/list
     */
    @GET("videos")
    fun getVideosList(
        @Query("part") part: String = "snippet,contentDetails,statistics",
        @Query("chart") chart: String? = null,
        @Query("hl") hl: String? = "vi",
        @Query("id") id: String? = null,
        @Query("maxResults") maxResults: Int? = null,
        @Query("pageToken") pageToken: String? = null,
        @Query("regionCode") regionCode: String? = null,
        @Query("videoCategoryId") videoCategoryId: String? = null,
        @Query("key") key: String = api_key
    ): Call<YoutubeModel<YoutubeVideoModel>>


    /**
     * lấy thông tin về channel
     * thông tin chi tiết  https://developers.google.com/youtube/v3/docs/channels/list
     */
    @GET("channels")
    fun getChannel(
        @Query("part") part: String = "snippet,contentDetails,statistics",
        @Query("categoryId") categoryId: String? = null,
        @Query("id") id: String? = null,
        @Query("hl") hl: String? = "vi",
        @Query("maxResults") maxResults: Int? = null,
        @Query("pageToken") pageToken: String? = null,
        @Query("key") key: String = api_key
    ) : Call<YoutubeModel<Channel>>
}

//Singleton design pattern
object YoutubeService {
    private const val BASE_URL = "https://youtube.googleapis.com/youtube/v3/"

    private fun youtubeApiService(): Retrofit {
        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val youtubeApi: YoutubeApi by lazy {
        youtubeApiService().create(YoutubeApi::class.java)
    }
}