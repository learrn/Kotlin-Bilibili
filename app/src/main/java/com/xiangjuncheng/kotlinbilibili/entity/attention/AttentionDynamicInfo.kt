package com.xiangjuncheng.kotlinbilibili.entity.attention

/**
 * Created by xiangjuncheng on 2017/6/14.
 * 关注动态模型类
 */
class AttentionDynamicInfo {

    var code: Int = 0

    var data: DataBean? = null


    class DataBean {

        var page: PageBean? = null

        var feeds: List<FeedsBean>? = null


        class PageBean {

            /**
             * count : 180
             * num : 1
             * size : 20
             */

            var count: Int = 0

            var num: Int = 0

            var size: Int = 0
        }

        class FeedsBean {

            var id: Int = 0

            var src_id: Int = 0

            var add_id: Int = 0

            var type: Int = 0

            var mcid: Int = 0

            var ctime: Int = 0

            var source: SourceBean? = null

            var addition: AdditionBean? = null


            class SourceBean {

                /**
                 * mid : 883968
                 * uname : 暴走漫画
                 * sex : 男
                 * sign : 无论你会不会画画,只要你有创意、够搞笑,暴走漫画给你平台,让你秀出你漫画家的潜质!请到官网www.baozoumanhua.com制作你的暴走漫画！
                 * avatar : http://i2.hdslb.com/bfs/face/7f51ff6f4ff3ad7a5ac8893aab75a67cd1850e5a.jpg
                 * rank : 10000
                 * DisplayRank : 10000
                 * level_info : {"current_level":6,"current_min":28800,"current_exp":2531756,"next_exp":"-"}
                 * pendant : {"pid":0,"name":"","image":"","expire":0}
                 * nameplate : {"nid":0,"name":"","image":"","image_small":"","level":"","condition":""}
                 * official_verify : {"type":1,"desc":"暴走漫画官方微博。"}
                 */

                var mid: String? = null

                var uname: String? = null

                var sex: String? = null

                var sign: String? = null

                var avatar: String? = null

                var rank: String? = null

                var displayRank: String? = null

                var level_info: LevelInfoBean? = null

                var pendant: PendantBean? = null

                var nameplate: NameplateBean? = null

                var official_verify: OfficialVerifyBean? = null


                class LevelInfoBean {

                    /**
                     * current_level : 6
                     * current_min : 28800
                     * current_exp : 2531756
                     * next_exp : -
                     */

                    var current_level: Int = 0

                    var current_min: Int = 0

                    var current_exp: Int = 0

                    var next_exp: String? = null
                }

                class PendantBean {

                    /**
                     * pid : 0
                     * name :
                     * image :
                     * expire : 0
                     */

                    var pid: Int = 0

                    var name: String? = null

                    var image: String? = null

                    var expire: Long = 0
                }

                class NameplateBean {

                    /**
                     * nid : 0
                     * name :
                     * image :
                     * image_small :
                     * level :
                     * condition :
                     */

                    var nid: Int = 0

                    var name: String? = null

                    var image: String? = null

                    var image_small: String? = null

                    var level: String? = null

                    var condition: String? = null
                }

                class OfficialVerifyBean {

                    /**
                     * type : 1
                     * desc : 暴走漫画官方微博。
                     */

                    var type: Int = 0

                    var desc: String? = null
                }
            }

            class AdditionBean {

                /**
                 * aid : 7163652
                 * typeid : 65
                 * typename : 网游·电竞
                 * title : 【暴走玩啥游戏第二季】11盗版秀优越感这波我服，核心危机中二少年传奇
                 * subtitle :
                 * play : 118347
                 * review : 6
                 * video_review : 3371
                 * favorites : 477
                 * mid : 883968
                 * author : 暴走漫画
                 * link : http://www.bilibili.com/video/av7163652/
                 * keywords : 暴走漫画,暴走出品,原创,暴走玩啥游戏第二季
                 * description : 这是一款讲述洗剪吹之间恩怨情仇的游戏大片，是游戏剧情CG中奇幻战斗的经典范本，游戏的剧情推进一度沉重，却能让玩家越打越喜感，
                 * *  没有了主角光环，中二少年的传奇之路该怎么走，性感旁白君带你走进本期上榜—核心危机。
                 *
                 *
                 * create : 2016-11-19 16:58
                 * pic : http://i0.hdslb.com/bfs/archive/723ca9c2fb42a46d9d4a885656e8862d4df5da98.jpg_320x200.jpg
                 * credit : 0
                 * coins : 2179
                 * money : 0
                 * duration : 17:10
                 * status : 0
                 * view : 0
                 * view_at :
                 * fav_create : 0
                 * fav_create_at :
                 * flag : p
                 */

                var aid: Int = 0

                var typeid: Int = 0

                var typename: String? = null

                var title: String? = null

                var subtitle: String? = null

                var play: Int = 0

                var review: Int = 0

                var video_review: Int = 0

                var favorites: Int = 0

                var mid: Int = 0

                var author: String? = null

                var link: String? = null

                var keywords: String? = null

                var description: String? = null

                var create: String? = null

                var pic: String? = null

                var credit: Int = 0

                var coins: Int = 0

                var money: Int = 0

                var duration: String? = null

                var status: Int = 0

                var view: Int = 0

                var view_at: String? = null

                var fav_create: Int = 0

                var fav_create_at: String? = null

                var flag: String? = null
            }
        }
    }
}