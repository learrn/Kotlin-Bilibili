package com.xiangjuncheng.kotlinbilibili.network.api

import com.xiangjuncheng.kotlinbilibili.entity.user.UserInterestQuanInfo
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable


/**
 * Created by xiangjuncheng on 2017/6/13.
 */
interface Im9Service {

    /**
     * 用户兴趣圈
     */
    @GET("api/query.community.list.do?access_key=c8455e3e73a29e2c451a2695dc77410b&actionKey=appkey&appkey=27eb53fc9058f8c3&build=3710&data_type=4&device=phone&mobi_app=iphone&platform=ios&sign=38d9bc710fd5d1c9a4e35d0bff545388&ts=1474365557")
    fun getUserInterestQuanData(
            @Query("mid") mid: Int, @Query("page_no") page: Int, @Query("page_size") pageSize: Int): Observable<UserInterestQuanInfo>
}