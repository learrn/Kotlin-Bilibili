package com.xiangjuncheng.kotlinbilibili.entity.bangumi

/**
 * Created by xiangjuncheng on 2017/6/14.
 * 番剧索引模型类
 */
class BangumiIndexInfo {

    var code: Int = 0

    var message: String? = null

    var result: ResultBean? = null


    class ResultBean {

        /**
         * cover : http://i2.hdslb.com/u_user/f7a16e4a28fe524ceddfe0860b52d057.jpg
         * tag_id : 117
         * tag_name : 轻改
         */

        var category: List<CategoryBean>? = null

        var years: List<String>? = null


        class CategoryBean {

            var cover: String? = null

            var tag_id: String? = null

            var tag_name: String? = null
        }
    }
}