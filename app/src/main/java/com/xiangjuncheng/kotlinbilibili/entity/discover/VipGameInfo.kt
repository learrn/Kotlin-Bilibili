package com.xiangjuncheng.kotlinbilibili.entity.discover

/**
 * Created by xiangjuncheng on 2017/6/14.
 * 大会员游戏礼包模型类
 */
class VipGameInfo {

    /**
     * code : 0
     * data : {"link":"http://vipgift.biligame.com/h5","imgPath":"http://i0.hdslb.com/bfs/vip/ae27c50022feb0cd47a1e20138de2d99b18eaf5f.png"}
     * msg :
     * ts : 1477923457747
     */

    var code: Int = 0

    /**
     * link : http://vipgift.biligame.com/h5
     * imgPath : http://i0.hdslb.com/bfs/vip/ae27c50022feb0cd47a1e20138de2d99b18eaf5f.png
     */

    var data: DataBean? = null

    var msg: String? = null

    var ts: Long = 0


    class DataBean {

        var link: String? = null

        var imgPath: String? = null
    }
}