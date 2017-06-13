package com.xiangjuncheng.kotlinbilibili.network.api

import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable


/**
 * Created by xiangjuncheng on 2017/6/13.
 * 用户个人账号相关api
 */
interface AccountService {

    /**
     * 用户详情数据
     */
    @GET("api/member/getCardByMid")
    fun getUserInfoById(@Query("mid") mid: Int): Observable<UserDetailsInfo>
}