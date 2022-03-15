package com.doublev.myoutube.model

import java.io.Serializable
import java.util.*

data class Id(
    val kind: String,
    val videoId: String
)


data class Thumbnail(
    val url: String,
    val with: Int,
    val height: Int
)

data class Thumbnails(
    val default: Thumbnail,
    val medium: Thumbnail,
    val high: Thumbnail
)

data class Snippet(
    val publishedAt: Date,
    val channelId: String,
    val title: String,
    val description: String,
    val thumbnails: Thumbnails,
    val channelTitle: String = "none",
    val liveBroadcastContent: String,
    val publishTime: Date
)


class ContentDetails(val duration : String){
    /**
     * do thời gian video trả về dạng string nên cần phân tích chuỗi
     * để lấy được thời gian video chính xác
     */
     fun convertDuration():String{
        //sample : PT21M3S
        val result = duration.replace("PT", "").replace("H", ":").replace("M", ":").replace("S", "")

        //sau khi phân tích cần phải tạo dạng chuẩn để hiện thị cho người dùng
        val arr = result.trim().split(":".toRegex()).toTypedArray()

         if (arr.size == 1){
             return String.format("%d", arr[0].toInt())
         }
         if (arr.size == 2){
             if(arr[1] == ""){
                 return String.format("%d:%02d", arr[0].toInt(),0)
             }
             return String.format("%d:%02d", arr[0].toInt(), arr[1].toInt())
         }
         else{
             if (arr[1] == "" && arr[2] != ""){
                 return String.format("%d:%02d:%02d", arr[0].toInt(),0, arr[2].toInt())
             }
             else if (arr[1] != "" && arr[2] == ""){
                 return String.format("%d:%02d:%02d", arr[0].toInt(), arr[1].toInt(),0)
             }
             else if(arr[1] == "" && arr[2] == "") {
                 return String.format("%d:%02d:%02d", arr[0].toInt())
             }
             return String.format("%d:%02d:%02d", arr[0].toInt(), arr[1].toInt(), arr[2].toInt())
         }

    }
}
data class Statistics(
    val viewCount : Long,
    val likeCount : Long,
    val commentCount : Long
)

data class Channel(
    val kind: String = "youtube#channel",
    val id: String,
    val snippet: Snippet,
    val contentDetails: ContentDetails,
    val statistics: Statistics
)

data class YoutubeSearchResultModel(
    val id: Id,
    val snippet: Snippet
)

data class YoutubeVideoModel(
    val kind: String = "youtube#video",
    val id: String,
    val snippet: Snippet,
    val contentDetails: ContentDetails,
    val statistics: Statistics,
    val channelTitle: String,
    var channel: Channel? = null
)

/**
 * T là 1 trong 3 kiểu Channel,YoutubeVideoModel,YoutubeSearchResultModel
 * trả về lần lượt các list item khi call api tương ứng
 */
data class YoutubeModel<T>(
    val kind: String,
    val etag : String,
    val items : List<T>,
    val nextPageToken : String
)