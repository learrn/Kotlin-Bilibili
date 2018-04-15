package com.xiangjuncheng.kotlinbilibili.network.api

import com.xiangjuncheng.kotlinbilibili.entity.recommend.RecommendBannerInfo
import com.xiangjuncheng.kotlinbilibili.entity.recommend.RecommendInfo
import com.xiangjuncheng.kotlinbilibili.entity.region.RegionDetailsInfo
import com.xiangjuncheng.kotlinbilibili.entity.region.RegionRecommendInfo
import com.xiangjuncheng.kotlinbilibili.entity.search.*
import com.xiangjuncheng.kotlinbilibili.entity.video.VideoDetailsInfo
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable


/**
 * Created by xiangjuncheng on 2017/6/13.
 */
interface BiliAppService {

    /**
     * 首页推荐数据
     */
    @get:GET("x/show/old?platform=android&device=&build=412001")
    val recommendedInfo: Observable<RecommendInfo>

    /**
     * 首页推荐banner
     */
    @get:GET("x/banner?plat=4&build=411007&channel=bilih5")
    val recommendedBannerInfo: Observable<RecommendBannerInfo>

    /**
     * 视频详情数据
     */
    @GET("x/view?access_key=19946e1ef3b5cad1a756c475a67185bb&actionKey=appkey&appkey=27eb53fc9058f8c3&build=3940&device=phone&mobi_app=iphone&platform=ios&sign=1206255541e2648c1badb87812458046&ts=1478349831")
    fun getVideoDetails(@Query("aid") aid: Int): Observable<VideoDetailsInfo>

    /**
     * 综合搜索
     */
    @GET("x/v2/search?actionKey=appkey&appkey=27eb53fc9058f8c3&build=3710&device=phone&duration=0&mobi_app=iphone&order=default&platform=ios&rid=0")
    fun searchArchive(
            @Query("keyword") content: String, @Query("pn") page: Int, @Query("ps") pagesize: Int): Observable<SearchArchiveInfo>

    /**
     * 番剧搜索
     */
    @GET("x/v2/search/type?actionKey=appkey&appkey=27eb53fc9058f8c3&build=3710&device=phone&mobi_app=iphone&platform=ios&type=1")
    fun searchBangumi(
            @Query("keyword") content: String, @Query("pn") page: Int, @Query("ps") pagesize: Int): Observable<SearchBangumiInfo>

    /**
     * up主搜索
     */
    @GET("x/v2/search/type?actionKey=appkey&appkey=27eb53fc9058f8c3&build=3710&device=phone&mobi_app=iphone&platform=ios&type=2")
    fun searchUpper(
            @Query("keyword") content: String, @Query("pn") page: Int, @Query("ps") pagesize: Int): Observable<SearchUpperInfo>

    /**
     * 影视搜索
     */
    @GET("x/v2/search/type?actionKey=appkey&appkey=27eb53fc9058f8c3&build=3710&device=phone&mobi_app=iphone&platform=ios&type=3")
    fun searchMovie(
            @Query("keyword") content: String, @Query("pn") page: Int, @Query("ps") pagesize: Int): Observable<SearchMovieInfo>

    /**
     * 专题搜索
     */
    @GET("x/v2/search/type?actionKey=appkey&appkey=27eb53fc9058f8c3&build=3710&device=phone&mobi_app=iphone&platform=ios&type=4")
    fun searchSp(
            @Query("keyword") content: String, @Query("pn") page: Int, @Query("ps") pagesize: Int): Observable<SearchSpInfo>

    /**
     * 分区推荐
     */
    @GET("x/v2/region/show?access_key=67cbf6a1e9ad7d7f11bfbd918e50c837&actionKey=appkey&appkey=27eb53fc9058f8c3&build=3600&device=phone&mobi_app=iphone&plat=1&platform=ios&sign=959d7b8c09c65e7a66f7e58b1a2bdab9&ts=1472310694")
    fun getRegionRecommends(@Query("rid") rid: Int): Observable<RegionRecommendInfo>

    /**
     * 分区类型详情
     */
    @GET("x/v2/region/show/child?build=3600")
    fun getRegionDetails(@Query("rid") rid: Int): Observable<RegionDetailsInfo>

    /**
     * 视频信息
     */
    @GET("view")
    fun getView(@Query("id") id: Int,@Query("page") page: Int): Observable<String>
}