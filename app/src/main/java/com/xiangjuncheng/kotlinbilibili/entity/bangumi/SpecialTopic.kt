package com.xiangjuncheng.kotlinbilibili.entity.bangumi

import com.google.gson.annotations.SerializedName



/**
 * Created by xiangjuncheng on 2017/6/14.
 * 专题视频(包括二三次元番剧)
 */
class SpecialTopic {

    // 专题ID
    var spid: Int = 0

    // 专题名
    var title: String? = null

    // 发布日期 UNIX 时间戳
    var pubdate: Int = 0

    // 发布日期
    var created_at: String? = null

    // 最后更新日期 UNIX 时间戳
    var lastupdate: Int = 0

    // 最后更新日期
    var lastupdate_at: String? = null

    // 同义词
    var alias: String? = null

    // 封面
    var cover: String? = null

    // 是否为新番 1=2次元新番 2=3次元新番
    var isbangumi: Int = 0

    // 是否已经播放结束
    var isbangumi_end: Int = 0

    // 开播日期
    var bangumi_date: String? = null

    // 专题简介
    var description: String? = null

    // 点击次数
    var view: Int = 0

    // 专题收藏次数
    var favourite: Int = 0

    // 专题被关注的次数
    var attention: Int = 0

    // 专题视频数量
    var count: Int = 0

    @SerializedName("video_view")
    var play: Int = 0

    class Item {

        // 标题
        var title: String? = null

        // 封面图片地址
        var cover: String? = null

        // 视频ID
        var aid: Int = 0

        // 点击次数
        var click: Int = 0

        //第几集
        var episode: String? = null
    }
}