package com.xiangjuncheng.kotlinbilibili.entity.bangumi

/**
 * Created by xiangjuncheng on 2017/6/14.
 * 分季新番模型类
 */
class SeasonNewBangumiInfo {

    var code: Int = 0

    var message: String? = null

    var result: List<ResultBean>? = null


    class ResultBean {

        var season: Int = 0

        var year: Int = 0

        /**
         * cover : http://i0.hdslb.com/bfs/bangumi/2673ac643b48eb5bda64c960a2ca850fbebb839d.jpg
         * favourites : 1262185
         * is_finish : 0
         * last_time : 0
         * newest_ep_index : 4
         * pub_time : 1475607600
         * season_id : 5550
         * season_status : 2
         * title : 夏目友人帐 伍
         * version : tv
         * watching_count : 0
         */

        var list: List<ListBean>? = null


        class ListBean {

            var cover: String? = null

            var favourites: String? = null

            var is_finish: Int = 0

            var last_time: Int = 0

            var newest_ep_index: String? = null

            var pub_time: Int = 0

            var season_id: Int = 0

            var season_status: Int = 0

            var title: String? = null

            var version: String? = null

            var watching_count: Int = 0
        }
    }
}