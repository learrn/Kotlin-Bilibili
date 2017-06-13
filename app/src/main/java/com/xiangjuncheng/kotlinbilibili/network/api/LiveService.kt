package com.xiangjuncheng.kotlinbilibili.network.api

import retrofit2.http.GET
import okhttp3.ResponseBody
import com.xiangjuncheng.kotlinbilibili.entity.live.LiveAppIndexInfo
import retrofit2.http.Query
import rx.Observable


/**
 * Created by xiangjuncheng on 2017/6/13.
 * 直播相关api
 */
interface LiveService {

    /**
     * 首页直播
     */
    @get:GET("AppIndex/home?_device=android&_hwid=51e96f5f2f54d5f9&_ulv=10000&access_key=563d6046f06289cbdcb472601ce5a761&appkey=c1b107428d337928&build=410000&platform=android&scale=xxhdpi&sign=fbdcfe141853f7e2c84c4d401f6a8758")
    val liveAppIndex: Observable<LiveAppIndexInfo>

    /**
     * 直播url
     */
    @GET("api/playurl?player=1&quality=0")
    fun getLiveUrl(@Query("cid") cid: Int): Observable<ResponseBody>

    /**
     * 获取直播状态
     */
    @GET("AppRoom/getRoomInfo")
    fun getUserLiveRoomStatus(@Query("mid") mid: Int): Observable<UserLiveRoomStatusInfo>
}