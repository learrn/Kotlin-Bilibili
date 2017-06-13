package com.xiangjuncheng.kotlinbilibili.entity.user

import android.os.Parcel
import android.os.Parcelable



/**
 * Created by xiangjuncheng on 2017/6/13.
 * 用户详情追番模型类
 */
class UserChaseBangumiInfo protected constructor(`in`: Parcel) : Parcelable {

    /**
     * status : true
     * data : {"count":3,"pages":1,"result":[{"season_id":"5523","share_url":"http://bangumi.bilibili.com/anime/5523/","title":"3月的狮子","is_finish":0,"favorites":201926,"newest_ep_index":1,"last_ep_index":0,"total_count":22,"cover":"http://i0.hdslb.com/bfs/bangumi/7bfd5b9a4aabee8df09df12939d2f32c2f41a0d7.jpg","evaluate":"","brief":"独自居住在东京旧市街的17岁职业将棋棋士\u2014\u2014桐山零。\n他是个幼时就因为意外失去家人，怀抱着深沉孤独的..."},{"season_id":"5063","share_url":"http://bangumi.bilibili.com/anime/5063/","title":"DAYS","is_finish":0,"favorites":177725,"newest_ep_index":14,"last_ep_index":0,"total_count":24,"cover":"http://i0.hdslb.com/bfs/bangumi/20f05ecc4c50d560814e511ced4205f37e640501.jpg","evaluate":"","brief":"【本周更新的13话将改为10月2日播出】电视动画《DAYS》改编自日本漫画家安田刚士原作的同名漫画。..."},{"season_id":"5029","share_url":"http://bangumi.bilibili.com/anime/5029/","title":"天真与闪电","is_finish":1,"favorites":410969,"newest_ep_index":12,"last_ep_index":0,"total_count":12,"cover":"http://i0.hdslb.com/bfs/bangumi/5626f7afbd39a0b4561dea5bd267ba1ef2248c0d.jpg","evaluate":"","brief":"妻子亡故后，独自努力养育女儿的数学教师·犬冢。不擅长料理又是个味觉白痴的他，在偶然之下和学生·饭田小..."}]}
     */

    var isStatus: Boolean = false

    /**
     * count : 3
     * pages : 1
     * result : [{"season_id":"5523","share_url":"http://bangumi.bilibili.com/anime/5523/","title":"3月的狮子","is_finish":0,"favorites":201926,"newest_ep_index":1,"last_ep_index":0,"total_count":22,"cover":"http://i0.hdslb.com/bfs/bangumi/7bfd5b9a4aabee8df09df12939d2f32c2f41a0d7.jpg","evaluate":"","brief":"独自居住在东京旧市街的17岁职业将棋棋士\u2014\u2014桐山零。\n他是个幼时就因为意外失去家人，怀抱着深沉孤独的..."},{"season_id":"5063","share_url":"http://bangumi.bilibili.com/anime/5063/","title":"DAYS","is_finish":0,"favorites":177725,"newest_ep_index":14,"last_ep_index":0,"total_count":24,"cover":"http://i0.hdslb.com/bfs/bangumi/20f05ecc4c50d560814e511ced4205f37e640501.jpg","evaluate":"","brief":"【本周更新的13话将改为10月2日播出】电视动画《DAYS》改编自日本漫画家安田刚士原作的同名漫画。..."},{"season_id":"5029","share_url":"http://bangumi.bilibili.com/anime/5029/","title":"天真与闪电","is_finish":1,"favorites":410969,"newest_ep_index":12,"last_ep_index":0,"total_count":12,"cover":"http://i0.hdslb.com/bfs/bangumi/5626f7afbd39a0b4561dea5bd267ba1ef2248c0d.jpg","evaluate":"","brief":"妻子亡故后，独自努力养育女儿的数学教师·犬冢。不擅长料理又是个味觉白痴的他，在偶然之下和学生·饭田小..."}]
     */

    var data: DataBean? = null


    open class DataBean : Parcelable {

        var count: Int = 0

        var pages: Int = 0

        /**
         * season_id : 5523
         * share_url : http://bangumi.bilibili.com/anime/5523/
         * title : 3月的狮子
         * is_finish : 0
         * favorites : 201926
         * newest_ep_index : 1
         * last_ep_index : 0
         * total_count : 22
         * cover : http://i0.hdslb.com/bfs/bangumi/7bfd5b9a4aabee8df09df12939d2f32c2f41a0d7.jpg
         * evaluate :
         * brief : 独自居住在东京旧市街的17岁职业将棋棋士——桐山零。
         * 他是个幼时就因为意外失去家人，怀抱着深沉孤独的...
         */

        var result: List<ResultBean>? = null


        class ResultBean : Parcelable {

            var season_id: String? = null

            var share_url: String? = null

            var title: String? = null

            var is_finish: Int = 0

            var favorites: Int = 0

            var newest_ep_index: Int = 0

            var last_ep_index: Int = 0

            var total_count: Int = 0

            var cover: String? = null

            var evaluate: String? = null

            var brief: String? = null


            override fun describeContents(): Int {

                return 0
            }


            override fun writeToParcel(dest: Parcel, flags: Int) {

                dest.writeString(this.season_id)
                dest.writeString(this.share_url)
                dest.writeString(this.title)
                dest.writeInt(this.is_finish)
                dest.writeInt(this.favorites)
                dest.writeInt(this.newest_ep_index)
                dest.writeInt(this.last_ep_index)
                dest.writeInt(this.total_count)
                dest.writeString(this.cover)
                dest.writeString(this.evaluate)
                dest.writeString(this.brief)
            }


            constructor() {

            }


            protected constructor(`in`: Parcel) {

                this.season_id = `in`.readString()
                this.share_url = `in`.readString()
                this.title = `in`.readString()
                this.is_finish = `in`.readInt()
                this.favorites = `in`.readInt()
                this.newest_ep_index = `in`.readInt()
                this.last_ep_index = `in`.readInt()
                this.total_count = `in`.readInt()
                this.cover = `in`.readString()
                this.evaluate = `in`.readString()
                this.brief = `in`.readString()
            }

            companion object {


                val CREATOR: Parcelable.Creator<ResultBean> = object : Parcelable.Creator<ResultBean> {

                    override fun createFromParcel(source: Parcel): ResultBean {

                        return ResultBean(source)
                    }


                    override fun newArray(size: Int): Array<ResultBean?> {
                        return arrayOfNulls(size)
                    }
                }
            }
        }


        override fun describeContents(): Int {

            return 0
        }


        override fun writeToParcel(dest: Parcel, flags: Int) {

            dest.writeInt(this.count)
            dest.writeInt(this.pages)
            dest.writeList(this.result)
        }


        protected constructor(`in`: Parcel) {

            this.count = `in`.readInt()
            this.pages = `in`.readInt()
            this.result = ArrayList<ResultBean>()
            `in`.readList(this.result, ResultBean::class.java.classLoader)
        }

        companion object {


            val CREATOR: Parcelable.Creator<DataBean> = object : Parcelable.Creator<DataBean> {

                override fun createFromParcel(source: Parcel): DataBean {

                    return DataBean(source)
                }


                override fun newArray(size: Int): Array<DataBean?> {

                    return arrayOfNulls(size)
                }
            }
        }
    }


    override fun describeContents(): Int {

        return 0
    }


    override fun writeToParcel(dest: Parcel, flags: Int) {

        dest.writeByte(if (this.isStatus) 1.toByte() else 0.toByte())
        dest.writeParcelable(this.data, flags)
    }


    companion object {


        val CREATOR: Parcelable.Creator<UserChaseBangumiInfo> = object : Parcelable.Creator<UserChaseBangumiInfo> {

            override fun createFromParcel(source: Parcel): UserChaseBangumiInfo {

                return UserChaseBangumiInfo(source)
            }


            override fun newArray(size: Int): Array<UserChaseBangumiInfo?> {

                return arrayOfNulls(size)
            }
        }
    }

    init {
        this.isStatus = `in`.readByte().toInt() != 0
        this.data = `in`.readParcelable<DataBean>(DataBean::class.java.classLoader)
    }
}