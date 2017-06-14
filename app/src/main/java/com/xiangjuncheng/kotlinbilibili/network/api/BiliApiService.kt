package com.xiangjuncheng.kotlinbilibili.network.api

import com.xiangjuncheng.kotlinbilibili.entity.attention.AttentionDynamicInfo
import com.xiangjuncheng.kotlinbilibili.entity.bangumi.BangumiDetailsCommentInfo
import com.xiangjuncheng.kotlinbilibili.entity.bangumi.SpecialTopic
import com.xiangjuncheng.kotlinbilibili.entity.bangumi.SpecialTopicIResult
import com.xiangjuncheng.kotlinbilibili.entity.discover.ActivityCenterInfo
import com.xiangjuncheng.kotlinbilibili.entity.discover.TopicCenterInfo
import com.xiangjuncheng.kotlinbilibili.entity.user.UserFavoritesInfo
import com.xiangjuncheng.kotlinbilibili.entity.video.VideoCommentInfo
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable


/**
 * Created by xiangjuncheng on 2017/6/13.
 */
interface BiliApiService {

    /**
     * 视频评论
     */
    @GET("feedback")
    fun getVideoComment(
            @Query("aid") aid: Int,
            @Query("page") page: Int, @Query("pagesize") pageSize: Int, @Query("ver") ver: Int): Observable<VideoCommentInfo>

    /**
     * 专题详情
     */
    @GET("sp")
    fun getSpInfo(@Query("spid") spid: Int, @Query("title") title: String): Observable<SpecialTopic>

    /**
     * 专题视频
     */
    @GET("spview")
    fun getSpItemList(
            @Query("spid") spid: Int, @Query("season_id") season_id: Int, @Query("bangumi") bangumi: Int): Observable<SpecialTopicIResult>

    /**
     * 话题中心
     */
    @get:GET("topic/getlist?device=phone&mobi_app=iphone&page=1&pagesize=137")
    val topicCenterList: Observable<TopicCenterInfo>

    /**
     * 话题中心
     */
    @GET("event/getlist?device=phone&mobi_app=iphone")
    fun getActivityCenterList(
            @Query("page") page: Int, @Query("pagesize") pageSize: Int): Observable<ActivityCenterInfo>

    /**
     * 用户收藏夹
     */
    @GET("x/app/favourite/folder?")
    fun getUserFavorites(@Query("mid") mid: Int): Observable<UserFavoritesInfo>

    /**
     * 首页关注
     */
    @get:GET("x/feed/pull?access_key=9afd8a2836e5948e84e037ca5b33309c&actionKey=appkey&appkey=27eb53fc9058f8c3&build=4000&device=phone&mobi_app=iphone&platform=ios&pn=1&ps=30&sign=8d5f090c70b3743a6a7d899d885061f0&ts=1480131936&type=0")
    val attentionDynamic: Observable<AttentionDynamicInfo>

    /**
     * 番剧详情评论
     */
    @get:GET("x/v2/reply?_device=iphone&_hwid=c84c067f8d99f9d3&_ulv=10000&access_key=19946e1ef3b5cad1a756c475a67185bb&appkey=27eb53fc9058f8c3&appver=3940&build=3940&nohot=0&oid=5189987&platform=ios&pn=1&ps=20&sign=c3b059e907f5c1d3416daa9fcc6396bf&sort=0&type=1")
    val bangumiDetailsComments: Observable<BangumiDetailsCommentInfo>
}