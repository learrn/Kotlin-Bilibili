package com.xiangjuncheng.kotlinbilibili.entity.search

import com.google.gson.annotations.SerializedName



/**
 * Created by xiangjuncheng on 2017/6/14.
 * 番剧搜索模型类
 */
class SearchBangumiInfo {

    /**
     * code : 0
     * data : {"pages":1,"items":[{"title":"房东妹子青春期","cover":"http://i0.hdslb.com/bfs/bangumi/2c4612ae6f880172826a4e974407777dc2b06bb1.jpg","uri":"bilibili://bangumi/season/3116","param":"3116","goto":"bangumi","finish":1,"index":"12","newest_cat":"tv","newest_season":"TV","cat_desc":"TV(1)
     * ","total_count":12,"official_verify":{"type":0,"desc":""},"status":0},{"title":"小森同学拒绝不了！","cover":"http://i0.hdslb.com/bfs/bangumi/f7aa7c3f93ff41127992e0487e4ec1db3128d643.jpg","uri":"bilibili://bangumi/season/2744","param":"2744","goto":"bangumi","finish":1,"index":"12","newest_cat":"tv","newest_season":"TV","cat_desc":"TV(1)
     * ","total_count":12,"official_verify":{"type":0,"desc":""},"status":0},{"title":"不思议美眉","cover":"http://i0.hdslb.com/bfs/bangumi/1b2dc503a4d0aa06581d757061371df3cd5600ea.jpg","uri":"bilibili://bangumi/season/2733","param":"2733","goto":"bangumi","finish":1,"index":"12","newest_cat":"tv","newest_season":"TV","cat_desc":"TV(1)
     * ","total_count":12,"official_verify":{"type":0,"desc":""},"status":0}]}
     * message :
     */

    var code: Int = 0

    /**
     * pages : 1
     * items : [{"title":"房东妹子青春期","cover":"http://i0.hdslb.com/bfs/bangumi/2c4612ae6f880172826a4e974407777dc2b06bb1.jpg","uri":"bilibili://bangumi/season/3116","param":"3116","goto":"bangumi","finish":1,"index":"12","newest_cat":"tv","newest_season":"TV","cat_desc":"TV(1)
     * ","total_count":12,"official_verify":{"type":0,"desc":""},"status":0},{"title":"小森同学拒绝不了！","cover":"http://i0.hdslb.com/bfs/bangumi/f7aa7c3f93ff41127992e0487e4ec1db3128d643.jpg","uri":"bilibili://bangumi/season/2744","param":"2744","goto":"bangumi","finish":1,"index":"12","newest_cat":"tv","newest_season":"TV","cat_desc":"TV(1)
     * ","total_count":12,"official_verify":{"type":0,"desc":""},"status":0},{"title":"不思议美眉","cover":"http://i0.hdslb.com/bfs/bangumi/1b2dc503a4d0aa06581d757061371df3cd5600ea.jpg","uri":"bilibili://bangumi/season/2733","param":"2733","goto":"bangumi","finish":1,"index":"12","newest_cat":"tv","newest_season":"TV","cat_desc":"TV(1)
     * ","total_count":12,"official_verify":{"type":0,"desc":""},"status":0}]
     */

    var data: DataBean? = null

    var message: String? = null


    class DataBean {

        var pages: Int = 0

        /**
         * title : 房东妹子青春期
         * cover : http://i0.hdslb.com/bfs/bangumi/2c4612ae6f880172826a4e974407777dc2b06bb1.jpg
         * uri : bilibili://bangumi/season/3116
         * param : 3116
         * goto : bangumi
         * finish : 1
         * index : 12
         * newest_cat : tv
         * newest_season : TV
         * cat_desc : TV(1)
         * total_count : 12
         * official_verify : {"type":0,"desc":""}
         * status : 0
         */

        var items: List<ItemsBean>? = null


        class ItemsBean {

            var title: String? = null

            var cover: String? = null

            var uri: String? = null

            var param: String? = null

            @SerializedName("goto")
            var gotoX: String? = null

            var finish: Int = 0

            var index: String? = null

            var newest_cat: String? = null

            var newest_season: String? = null

            var cat_desc: String? = null

            var total_count: Int = 0

            /**
             * type : 0
             * desc :
             */

            var official_verify: OfficialVerifyBean? = null

            var status: Int = 0


            class OfficialVerifyBean {

                var type: Int = 0

                var desc: String? = null
            }
        }
    }
}