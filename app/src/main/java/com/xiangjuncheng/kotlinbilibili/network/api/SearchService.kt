package com.xiangjuncheng.kotlinbilibili.network.api

import com.xiangjuncheng.kotlinbilibili.entity.discover.HotSearchTag
import retrofit2.http.GET
import rx.Observable


/**
 * Created by xiangjuncheng on 2017/6/13.
 * 搜索相关api
 */
interface SearchService {

    /**
     * 首页发现热搜词
     */
    @get:GET("main/hotword?access_key=ec0f54fc369d8c104ee1068672975d6a&actionKey=appkey&appkey=27eb53fc9058f8c3")
    val hotSearchTags: Observable<HotSearchTag>
}
