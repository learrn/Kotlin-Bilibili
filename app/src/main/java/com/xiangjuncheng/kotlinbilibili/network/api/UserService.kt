package com.xiangjuncheng.kotlinbilibili.network.api

import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable


/**
 * Created by xiangjuncheng on 2017/6/13.
 * 用户相关api
 */
interface UserService {

    /**
     * 用户所玩游戏
     */
    @GET("ajax/game/GetLastPlay")
    fun getUserPlayGames(@Query("mid") mid: Int): Observable<UserPlayGameInfo>

    /**
     * 用户投币视频
     */
    @GET("ajax/member/getCoinVideos")
    fun getUserCoinVideos(@Query("mid") mid: Int): Observable<UserCoinsInfo>

    /**
     * 用户追番
     */
    @GET("ajax/Bangumi/getList")
    fun getUserChaseBangumis(@Query("mid") mid: Int): Observable<UserChaseBangumiInfo>

    /**
     * 用户投稿视频
     */
    @GET("ajax/member/getSubmitVideos")
    fun getUserContributeVideos(
            @Query("mid") mid: Int, @Query("page") page: Int, @Query("pagesize") pageSize: Int): Observable<UserContributeInfo>
}