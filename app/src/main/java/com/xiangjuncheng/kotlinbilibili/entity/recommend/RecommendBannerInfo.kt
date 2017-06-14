package com.xiangjuncheng.kotlinbilibili.entity.recommend

/**
 * Created by xiangjuncheng on 2017/6/14.
 * 首页Banner推荐
 */
class RecommendBannerInfo {

    var code: Int = 0

    var data: List<DataBean>? = null


    class DataBean {

        var title: String? = null

        var value: String? = null

        var image: String? = null

        var type: Int = 0

        var weight: Int = 0

        var remark: String? = null

        var hash: String? = null
    }
}