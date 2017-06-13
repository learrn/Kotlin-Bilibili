package com.xiangjuncheng.kotlinbilibili.network.api

import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable


/**
 * Created by xiangjuncheng on 2017/6/13.
 * 排行榜相关api
 */
interface RankService {

    /**
     * 原创排行榜请求
     */
    @GET("index/rank/{type}")
    fun getOriginalRanks(@Path("type") type: String): Observable<OriginalRankInfo>

    /**
     * 全区排行榜数据请求
     */
    @GET("index/rank/{type}")
    fun getAllareasRanks(@Path("type") type: String): Observable<AllareasRankInfo>
}