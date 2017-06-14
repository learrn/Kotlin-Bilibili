package com.xiangjuncheng.kotlinbilibili.entity.bangumi

import com.google.gson.annotations.SerializedName



/**
 * Created by xiangjuncheng on 2017/6/14.
 * 新番连载
 */
class NewBangumiSerialInfo {

    var count: String? = null

    /**
     * title : 藍海少女（僅限台灣地區）
     * area : 日本
     * arealimit : 0
     * attention : 1981
     * bangumi_id : 2986
     * bgmcount : 5
     * cover : http://i0.hdslb.com/bfs/bangumi/4edab54e9db126fa3b3a81e3960e292ccd22a31b.jpg
     * square_cover : http://i0.hdslb.com/bfs/bangumi/1f127711ea8bb36c93039380eb5e74e2aebeddac.jpg
     * danmaku_count : 1382
     * favorites : 1981
     * is_finish : 0
     * lastupdate_at : 2016-08-05 23:00:02
     * new : true
     * play_count : 11189
     * season_id : 5046
     * spid : 0
     * url : /bangumi/i/5046/
     * viewRank : 0
     * weekday : 5
     */

    var list: List<ListBean>? = null


    class ListBean {

        var title: String? = null

        var area: String? = null

        var arealimit: Int = 0

        var attention: Int = 0

        var bangumi_id: Int = 0

        var bgmcount: String? = null

        var cover: String? = null

        var square_cover: String? = null

        var danmaku_count: Int = 0

        var favorites: Int = 0

        var is_finish: Int = 0

        var lastupdate_at: String? = null

        @SerializedName("new")
        var isNewX: Boolean = false

        var play_count: Int = 0

        var season_id: Int = 0

        var spid: Int = 0

        var url: String? = null

        var viewRank: Int = 0

        var weekday: Int = 0
    }
}