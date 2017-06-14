package com.xiangjuncheng.kotlinbilibili.network.api

import com.xiangjuncheng.kotlinbilibili.entity.bangumi.NewBangumiSerialInfo
import com.xiangjuncheng.kotlinbilibili.entity.video.HDVideoInfo
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import rx.Observable


/**
 * Created by xiangjuncheng on 2017/6/13.
 * bilibili-go相关api
 */
interface BiliGoService {

    /**
     * b站高清视频
     * quailty:清晰度(1~2，根据视频有不同)
     * type: 格式(mp4/flv)
     */
    @GET("/video/{cid}")
    fun getHDVideoUrl(@Path("cid") cid: Int,
                      @Query("quailty") quailty: Int,
                      @Query("type") type: String): Observable<HDVideoInfo>

    /**
     * 新番连载
     */
    @get:GET("bangumi")
    val newBangumiSerialList: Observable<NewBangumiSerialInfo>
}