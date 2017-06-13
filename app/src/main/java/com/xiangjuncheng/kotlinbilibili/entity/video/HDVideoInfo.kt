package com.xiangjuncheng.kotlinbilibili.entity.video

/**
 * Created by xiangjuncheng on 2017/6/13.
 * B站高清视频
 */
class HDVideoInfo {

    var from: String? = null

    var result: String? = null

    var format: String? = null

    var timelength: Int = 0

    var accept_format: String? = null

    var seek_param: String? = null

    var seek_type: String? = null

    var accept_quality: List<Int>? = null

    var durl: List<DurlEntity>? = null


    class DurlEntity {

        var order: Int = 0

        var length: Int = 0

        var size: Int = 0

        var url: String? = null

        var backup_url: List<String>? = null
    }
}