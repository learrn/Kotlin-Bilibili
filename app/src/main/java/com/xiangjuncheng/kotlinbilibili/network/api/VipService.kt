package com.xiangjuncheng.kotlinbilibili.network.api

import com.xiangjuncheng.kotlinbilibili.entity.discover.VipGameInfo
import retrofit2.http.GET
import rx.Observable


/**
 * Created by xiangjuncheng on 2017/6/13.
 *  大会员相关api
 */
interface VipService {

    @get:GET("api/v1/games/gift?access_key=19946e1ef3b5cad1a756c475a67185bb&actionKey=appkey&appkey=27eb53fc9058f8c3&build=3940&device=phone&mobi_app=iphone&platform=ios&sign=f6a995f30f3d4362a628191d523e3012&ts=1477922329")
    val vipGame: Observable<VipGameInfo>
}