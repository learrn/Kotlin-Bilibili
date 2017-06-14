package com.xiangjuncheng.kotlinbilibili.network.api

import com.xiangjuncheng.kotlinbilibili.entity.bangumi.*
import retrofit2.http.GET
import rx.Observable



/**
 * Created by xiangjuncheng on 2017/6/13.
 * 番剧相关api
 */
interface BangumiService {

    /**
     * 首页番剧
     */
    @get:GET("api/app_index_page_v4?build=3940&device=phone&mobi_app=iphone&platform=ios")
    val bangumiAppIndex: Observable<BangumiAppIndexInfo>

    /**
     * 番剧详情番剧推荐
     */
    @get:GET("api/season/recommend/5070.json?access_key=19946e1ef3b5cad1a756c475a67185bb&actionKey=appkey&appkey=27eb53fc9058f8c3&build=3940&device=phone&mobi_app=iphone&platform=ios&season_id=5070&sign=744e3a3f52829e4344c33908f7a0c1ef&ts=1477898527")
    val bangumiDetailsRecommend: Observable<BangumiDetailsRecommendInfo>

    /**
     * 番剧详情
     */
    @get:GET("api/season_v4?access_key=19946e1ef3b5cad1a756c475a67185bb&actionKey=appkey&appkey=27eb53fc9058f8c3&build=3940&device=phone&mobi_app=iphone&platform=ios&season_id=5070&sign=3e5d4d7460961d9bab5da2341fd98dc1&ts=1477898526&type=bangumi")
    val bangumiDetails: Observable<BangumiDetailsInfo>

    /**
     * 首页番剧推荐
     */
    @get:GET("api/bangumi_recommend?access_key=f5bd4e793b82fba5aaf5b91fb549910a&actionKey=appkey&appkey=27eb53fc9058f8c3&build=3470&cursor=0&device=phone&mobi_app=iphone&pagesize=10&platform=ios&sign=56329a5709c401d4d7c0237f64f7943f&ts=1469613558")
    val bangumiRecommended: Observable<BangumiRecommendInfo>

    /**
     * 分季新番
     */
    @get:GET("api/season_group.json?build=3940&device=phone&mobi_app=iphone&platform=ios")
    val seasonNewBangumiList: Observable<SeasonNewBangumiInfo>

    /**
     * 番剧时间表
     */
    @get:GET("api/timeline_v4?access_key=19946e1ef3b5cad1a756c475a67185bb&actionKey=appkey&appkey=27eb53fc9058f8c3&area_id=2&build=3940&device=phone&mobi_app=iphone&platform=ios&see_mine=0&sign=d8cbbacab2e5fd0196b4883201e2103e&ts=1477981526")
    val bangumiSchedules: Observable<BangumiScheduleInfo>

    /**
     * 番剧索引
     */
    @get:GET("api/bangumi_index_cond?access_key=19946e1ef3b5cad1a756c475a67185bb&actionKey=appkey&appkey=27eb53fc9058f8c3&build=3940&device=phone&mobi_app=iphone&platform=ios&sign=cfc6903a13ba89e81c904b4c589e5369&ts=1477974966&type=0")
    val bangumiIndex: Observable<BangumiIndexInfo>
}