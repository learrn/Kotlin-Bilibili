package com.xiangjuncheng.kotlinbilibili.entity.bangumi

/**
 * Created by xiangjuncheng on 2017/6/14.
 * 首页番剧内容模型类
 */
class BangumiAppIndexInfo {

    /**
     * code : 0
     * message : success
     * result : {"ad":{"body":[{"img":"http://i0.hdslb.com/bfs/bangumi/7151e449a4d670e8d26db5e3359a0e36c860dafd.png","index":1,"link":"http://bangumi.bilibili.com/moe/2016/cn/mobile","title":"国产萌战"}],"head":[{"id":0,"img":"http://i0.hdslb.com/bfs/bangumi/5ac8277af4f06a8d14374c4428403233d20ac4e4.jpg","is_ad":0,"link":"http://bangumi.bilibili.com/anime/5542","pub_time":"2016-11-01
     * 01:05:00","title":"TRICKSTER"},{"id":0,"img":"http://i0.hdslb.com/bfs/bangumi/17f4538384d75d9748d0d1f2e71cbb8d226a2b71.jpg","is_ad":0,"link":"http://bangumi.bilibili.com/anime/5540","pub_time":"2016-10-31
     * 22:30:00","title":"斯特拉的魔法"},{"id":0,"img":"http://i0.hdslb.com/bfs/bangumi/89d276e9eba37528cd847e4c609179b322450773.jpg","is_ad":0,"link":"http://bangumi.bilibili.com/anime/3253","pub_time":"2016-11-01
     * 10:00:00","title":"十万个冷笑话"},{"id":0,"img":"http://i0.hdslb.com/bfs/bangumi/be4b30db286b33a5fd6e044250a20eada6d8261f.jpg","is_ad":0,"link":"http://bangumi.bilibili.com/anime/5102","pub_time":"2016-11-01
     * 10:00:00","title":"灵契"}]},"previous":{"list":[{"cover":"http://i0.hdslb.com/bfs/bangumi/6b4ba7bd59be1f9de225294d18ca5e6819185c06.jpg","favourites":"1407680","is_finish":1,"last_time":1474725610,"newest_ep_index":"13","pub_time":1467468000,"season_id":5017,"season_status":2,"title":"食戟之灵
     * 贰之皿","watching_count":0},{"cover":"http://i0.hdslb.com/bfs/bangumi/6e6a7524c42efb8061985f9fe1a0448e6913c3e2.jpg","favourites":"794585","is_finish":1,"last_time":1474813810,"newest_ep_index":"13","pub_time":1467556200,"season_id":5022,"season_status":2,"title":"热诚传说X","watching_count":0},{"cover":"http://i0.hdslb.com/bfs/bangumi/6bf7624d5b688b3e916c1ae1e94c2e16cd4714a7.jpg","favourites":"748228","is_finish":1,"last_time":1474903810,"newest_ep_index":"12","pub_time":1468251000,"season_id":5056,"season_status":2,"title":"弹丸论破3
     * -未来篇-","watching_count":0}],"season":3,"year":2016},"serializing":[{"cover":"http://i0.hdslb.com/bfs/bangumi/eb4f17335f48951945fb9da47e6ee0bc65fa2fbb.jpg","favourites":"681671","is_finish":0,"is_started":1,"last_time":1477958410,"newest_ep_index":"87","pub_time":1467590400,"season_id":5070,"season_status":2,"title":"齐木楠雄的灾难（日播版）","watching_count":2591},{"cover":"http://i0.hdslb.com/bfs/bangumi/9925ece99e3458760fc074e8564f74a1d6f46e1d.jpg","favourites":"48334","is_finish":0,"is_started":1,"last_time":1477953910,"newest_ep_index":"6","pub_time":1474905600,"season_id":5504,"season_status":2,"title":"喵阿楞","watching_count":69},{"cover":"http://i0.hdslb.com/bfs/bangumi/82709ec1fb027631b1f939c9dfcc2d850ffa12a2.jpg","favourites":"60284","is_finish":0,"is_started":1,"last_time":1477935300,"newest_ep_index":"5","pub_time":1475516100,"season_id":5539,"season_status":2,"title":"灼热乒乓妹","watching_count":532},{"cover":"http://i0.hdslb.com/bfs/bangumi/3501378364ff84cbb42d2ee946334cd3b2dec453.jpg","favourites":"360053","is_finish":0,"is_started":1,"last_time":1477933500,"newest_ep_index":"5","pub_time":1475514300,"season_id":5542,"season_status":2,"title":"TRICKSTER","watching_count":1048},{"cover":"http://i0.hdslb.com/bfs/bangumi/c74ba43e76c346476c8c09e653d35de69073cc49.jpg","favourites":"112429","is_finish":0,"is_started":1,"last_time":1477929610,"newest_ep_index":"5","pub_time":1475424000,"season_id":5543,"season_status":2,"title":"学园Handsome","watching_count":149},{"cover":"http://i0.hdslb.com/bfs/bangumi/a7d604cb9024faeb775a79a95c33542e3cdd420c.jpg","favourites":"75865","is_finish":0,"is_started":1,"last_time":1477924210,"newest_ep_index":"5","pub_time":1475505000,"season_id":5540,"season_status":2,"title":"斯特拉的魔法","watching_count":383}]}
     */

    var code: Int = 0

    var message: String? = null

    /**
     * ad : {"body":[{"img":"http://i0.hdslb.com/bfs/bangumi/7151e449a4d670e8d26db5e3359a0e36c860dafd.png","index":1,"link":"http://bangumi.bilibili.com/moe/2016/cn/mobile","title":"国产萌战"}],"head":[{"id":0,"img":"http://i0.hdslb.com/bfs/bangumi/5ac8277af4f06a8d14374c4428403233d20ac4e4.jpg","is_ad":0,"link":"http://bangumi.bilibili.com/anime/5542","pub_time":"2016-11-01
     * 01:05:00","title":"TRICKSTER"},{"id":0,"img":"http://i0.hdslb.com/bfs/bangumi/17f4538384d75d9748d0d1f2e71cbb8d226a2b71.jpg","is_ad":0,"link":"http://bangumi.bilibili.com/anime/5540","pub_time":"2016-10-31
     * 22:30:00","title":"斯特拉的魔法"},{"id":0,"img":"http://i0.hdslb.com/bfs/bangumi/89d276e9eba37528cd847e4c609179b322450773.jpg","is_ad":0,"link":"http://bangumi.bilibili.com/anime/3253","pub_time":"2016-11-01
     * 10:00:00","title":"十万个冷笑话"},{"id":0,"img":"http://i0.hdslb.com/bfs/bangumi/be4b30db286b33a5fd6e044250a20eada6d8261f.jpg","is_ad":0,"link":"http://bangumi.bilibili.com/anime/5102","pub_time":"2016-11-01
     * 10:00:00","title":"灵契"}]}
     * previous : {"list":[{"cover":"http://i0.hdslb.com/bfs/bangumi/6b4ba7bd59be1f9de225294d18ca5e6819185c06.jpg","favourites":"1407680","is_finish":1,"last_time":1474725610,"newest_ep_index":"13","pub_time":1467468000,"season_id":5017,"season_status":2,"title":"食戟之灵
     * 贰之皿","watching_count":0},{"cover":"http://i0.hdslb.com/bfs/bangumi/6e6a7524c42efb8061985f9fe1a0448e6913c3e2.jpg","favourites":"794585","is_finish":1,"last_time":1474813810,"newest_ep_index":"13","pub_time":1467556200,"season_id":5022,"season_status":2,"title":"热诚传说X","watching_count":0},{"cover":"http://i0.hdslb.com/bfs/bangumi/6bf7624d5b688b3e916c1ae1e94c2e16cd4714a7.jpg","favourites":"748228","is_finish":1,"last_time":1474903810,"newest_ep_index":"12","pub_time":1468251000,"season_id":5056,"season_status":2,"title":"弹丸论破3
     * -未来篇-","watching_count":0}],"season":3,"year":2016}
     * serializing : [{"cover":"http://i0.hdslb.com/bfs/bangumi/eb4f17335f48951945fb9da47e6ee0bc65fa2fbb.jpg","favourites":"681671","is_finish":0,"is_started":1,"last_time":1477958410,"newest_ep_index":"87","pub_time":1467590400,"season_id":5070,"season_status":2,"title":"齐木楠雄的灾难（日播版）","watching_count":2591},{"cover":"http://i0.hdslb.com/bfs/bangumi/9925ece99e3458760fc074e8564f74a1d6f46e1d.jpg","favourites":"48334","is_finish":0,"is_started":1,"last_time":1477953910,"newest_ep_index":"6","pub_time":1474905600,"season_id":5504,"season_status":2,"title":"喵阿楞","watching_count":69},{"cover":"http://i0.hdslb.com/bfs/bangumi/82709ec1fb027631b1f939c9dfcc2d850ffa12a2.jpg","favourites":"60284","is_finish":0,"is_started":1,"last_time":1477935300,"newest_ep_index":"5","pub_time":1475516100,"season_id":5539,"season_status":2,"title":"灼热乒乓妹","watching_count":532},{"cover":"http://i0.hdslb.com/bfs/bangumi/3501378364ff84cbb42d2ee946334cd3b2dec453.jpg","favourites":"360053","is_finish":0,"is_started":1,"last_time":1477933500,"newest_ep_index":"5","pub_time":1475514300,"season_id":5542,"season_status":2,"title":"TRICKSTER","watching_count":1048},{"cover":"http://i0.hdslb.com/bfs/bangumi/c74ba43e76c346476c8c09e653d35de69073cc49.jpg","favourites":"112429","is_finish":0,"is_started":1,"last_time":1477929610,"newest_ep_index":"5","pub_time":1475424000,"season_id":5543,"season_status":2,"title":"学园Handsome","watching_count":149},{"cover":"http://i0.hdslb.com/bfs/bangumi/a7d604cb9024faeb775a79a95c33542e3cdd420c.jpg","favourites":"75865","is_finish":0,"is_started":1,"last_time":1477924210,"newest_ep_index":"5","pub_time":1475505000,"season_id":5540,"season_status":2,"title":"斯特拉的魔法","watching_count":383}]
     */

    var result: ResultBean? = null


    class ResultBean {

        var ad: AdBean? = null

        /**
         * list : [{"cover":"http://i0.hdslb.com/bfs/bangumi/6b4ba7bd59be1f9de225294d18ca5e6819185c06.jpg","favourites":"1407680","is_finish":1,"last_time":1474725610,"newest_ep_index":"13","pub_time":1467468000,"season_id":5017,"season_status":2,"title":"食戟之灵
         * 贰之皿","watching_count":0},{"cover":"http://i0.hdslb.com/bfs/bangumi/6e6a7524c42efb8061985f9fe1a0448e6913c3e2.jpg","favourites":"794585","is_finish":1,"last_time":1474813810,"newest_ep_index":"13","pub_time":1467556200,"season_id":5022,"season_status":2,"title":"热诚传说X","watching_count":0},{"cover":"http://i0.hdslb.com/bfs/bangumi/6bf7624d5b688b3e916c1ae1e94c2e16cd4714a7.jpg","favourites":"748228","is_finish":1,"last_time":1474903810,"newest_ep_index":"12","pub_time":1468251000,"season_id":5056,"season_status":2,"title":"弹丸论破3
         * -未来篇-","watching_count":0}]
         * season : 3
         * year : 2016
         */

        var previous: PreviousBean? = null

        /**
         * cover : http://i0.hdslb.com/bfs/bangumi/eb4f17335f48951945fb9da47e6ee0bc65fa2fbb.jpg
         * favourites : 681671
         * is_finish : 0
         * is_started : 1
         * last_time : 1477958410
         * newest_ep_index : 87
         * pub_time : 1467590400
         * season_id : 5070
         * season_status : 2
         * title : 齐木楠雄的灾难（日播版）
         * watching_count : 2591
         */

        var serializing: List<SerializingBean>? = null


        class AdBean {

            /**
             * img : http://i0.hdslb.com/bfs/bangumi/7151e449a4d670e8d26db5e3359a0e36c860dafd.png
             * index : 1
             * link : http://bangumi.bilibili.com/moe/2016/cn/mobile
             * title : 国产萌战
             */

            var body: List<BodyBean>? = null

            /**
             * id : 0
             * img : http://i0.hdslb.com/bfs/bangumi/5ac8277af4f06a8d14374c4428403233d20ac4e4.jpg
             * is_ad : 0
             * link : http://bangumi.bilibili.com/anime/5542
             * pub_time : 2016-11-01 01:05:00
             * title : TRICKSTER
             */

            var head: List<HeadBean>? = null


            class BodyBean {

                var img: String? = null

                var index: Int = 0

                var link: String? = null

                var title: String? = null
            }

            class HeadBean {

                var id: Int = 0

                var img: String? = null

                var is_ad: Int = 0

                var link: String? = null

                var pub_time: String? = null

                var title: String? = null
            }
        }

        class PreviousBean {

            var season: Int = 0

            var year: Int = 0

            /**
             * cover : http://i0.hdslb.com/bfs/bangumi/6b4ba7bd59be1f9de225294d18ca5e6819185c06.jpg
             * favourites : 1407680
             * is_finish : 1
             * last_time : 1474725610
             * newest_ep_index : 13
             * pub_time : 1467468000
             * season_id : 5017
             * season_status : 2
             * title : 食戟之灵 贰之皿
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

                var watching_count: Int = 0
            }
        }

        class SerializingBean {

            var cover: String? = null

            var favourites: String? = null

            var is_finish: Int = 0

            var is_started: Int = 0

            var last_time: Int = 0

            var newest_ep_index: String? = null

            var pub_time: Int = 0

            var season_id: Int = 0

            var season_status: Int = 0

            var title: String? = null

            var watching_count: Int = 0
        }
    }
}