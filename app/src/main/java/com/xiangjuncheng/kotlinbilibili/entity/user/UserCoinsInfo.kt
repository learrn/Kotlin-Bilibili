package com.xiangjuncheng.kotlinbilibili.entity.user

import android.os.Parcel
import android.os.Parcelable



/**
 * Created by xiangjuncheng on 2017/6/13.
 * 用户详情投币模型类
 */
class UserCoinsInfo : Parcelable {

    /**
     * status : true
     * data : {"list":[{"aid":7157356,"pic":"http://i1.hdslb.com/bfs/archive/4b4f985f505a5423e63dc49a723c29dd808862c4.jpg","title":"【MMD配布预告】哪个是你老婆呢？随心所欲Mercy
     * [崩坏3模型5人组×K3ls渲]","stat":{"view":610,"danmaku":14,"reply":31,"favorite":70,"coin":36,"share":1,"now_rank":0,"his_rank":0}},{"aid":7076004,"pic":"http://i1.hdslb.com/bfs/archive/d8df0f38c25b6c5775f2c0ddc020fc5929acb4d5.jpg","title":"【MMD】drop
     * pop candy 【简易PV】","stat":{"view":446,"danmaku":7,"reply":21,"favorite":62,"coin":45,"share":2,"now_rank":0,"his_rank":0}}],"pages":1,"count":2}
     */

    var isStatus: Boolean = false

    var data: DataBean? = null


    class DataBean : Parcelable {

        /**
         * list : [{"aid":7157356,"pic":"http://i1.hdslb.com/bfs/archive/4b4f985f505a5423e63dc49a723c29dd808862c4.jpg","title":"【MMD配布预告】哪个是你老婆呢？随心所欲Mercy
         * [崩坏3模型5人组×K3ls渲]","stat":{"view":610,"danmaku":14,"reply":31,"favorite":70,"coin":36,"share":1,"now_rank":0,"his_rank":0}},{"aid":7076004,"pic":"http://i1.hdslb.com/bfs/archive/d8df0f38c25b6c5775f2c0ddc020fc5929acb4d5.jpg","title":"【MMD】drop
         * pop candy 【简易PV】","stat":{"view":446,"danmaku":7,"reply":21,"favorite":62,"coin":45,"share":2,"now_rank":0,"his_rank":0}}]
         * pages : 1
         * count : 2
         */

        var pages: Int = 0

        var count: Int = 0

        var list: List<ListBean>? = null


        class ListBean : Parcelable {

            /**
             * aid : 7157356
             * pic : http://i1.hdslb.com/bfs/archive/4b4f985f505a5423e63dc49a723c29dd808862c4.jpg
             * title : 【MMD配布预告】哪个是你老婆呢？随心所欲Mercy [崩坏3模型5人组×K3ls渲]
             * stat : {"view":610,"danmaku":14,"reply":31,"favorite":70,"coin":36,"share":1,"now_rank":0,"his_rank":0}
             */

            var aid: Int = 0

            var pic: String? = null

            var title: String? = null

            var stat: StatBean? = null


            class StatBean : Parcelable {

                /**
                 * view : 610
                 * danmaku : 14
                 * reply : 31
                 * favorite : 70
                 * coin : 36
                 * share : 1
                 * now_rank : 0
                 * his_rank : 0
                 */

                var view: Int = 0

                var danmaku: Int = 0

                var reply: Int = 0

                var favorite: Int = 0

                var coin: Int = 0

                var share: Int = 0

                var now_rank: Int = 0

                var his_rank: Int = 0


                override fun describeContents(): Int {

                    return 0
                }


                override fun writeToParcel(dest: Parcel, flags: Int) {

                    dest.writeInt(this.view)
                    dest.writeInt(this.danmaku)
                    dest.writeInt(this.reply)
                    dest.writeInt(this.favorite)
                    dest.writeInt(this.coin)
                    dest.writeInt(this.share)
                    dest.writeInt(this.now_rank)
                    dest.writeInt(this.his_rank)
                }


                constructor() {

                }


                protected constructor(`in`: Parcel) {

                    this.view = `in`.readInt()
                    this.danmaku = `in`.readInt()
                    this.reply = `in`.readInt()
                    this.favorite = `in`.readInt()
                    this.coin = `in`.readInt()
                    this.share = `in`.readInt()
                    this.now_rank = `in`.readInt()
                    this.his_rank = `in`.readInt()
                }

                companion object {


                    val CREATOR: Parcelable.Creator<StatBean> = object : Parcelable.Creator<StatBean> {

                        override fun createFromParcel(source: Parcel): StatBean {

                            return StatBean(source)
                        }


                        override fun newArray(size: Int): Array<StatBean?> {

                            return arrayOfNulls(size)
                        }
                    }
                }
            }


            override fun describeContents(): Int {

                return 0
            }


            override fun writeToParcel(dest: Parcel, flags: Int) {

                dest.writeInt(this.aid)
                dest.writeString(this.pic)
                dest.writeString(this.title)
                dest.writeParcelable(this.stat, flags)
            }


            constructor() {

            }


            protected constructor(`in`: Parcel) {

                this.aid = `in`.readInt()
                this.pic = `in`.readString()
                this.title = `in`.readString()
                this.stat = `in`.readParcelable<StatBean>(StatBean::class.java.classLoader)
            }

            companion object {


                val CREATOR: Parcelable.Creator<ListBean> = object : Parcelable.Creator<ListBean> {

                    override fun createFromParcel(source: Parcel): ListBean {

                        return ListBean(source)
                    }


                    override fun newArray(size: Int): Array<ListBean?> {

                        return arrayOfNulls(size)
                    }
                }
            }
        }


        override fun describeContents(): Int {

            return 0
        }


        override fun writeToParcel(dest: Parcel, flags: Int) {

            dest.writeInt(this.pages)
            dest.writeInt(this.count)
            dest.writeList(this.list)
        }


        constructor() {

        }


        protected constructor(`in`: Parcel) {

            this.pages = `in`.readInt()
            this.count = `in`.readInt()
            this.list = ArrayList<ListBean>()
            `in`.readList(this.list, ListBean::class.java.classLoader)
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


    constructor() {

    }


    protected constructor(`in`: Parcel) {

        this.isStatus = `in`.readByte().toInt() != 0
        this.data = `in`.readParcelable<DataBean>(DataBean::class.java.classLoader)
    }

    companion object {


        val CREATOR: Parcelable.Creator<UserCoinsInfo> = object : Parcelable.Creator<UserCoinsInfo> {

            override fun createFromParcel(source: Parcel): UserCoinsInfo {

                return UserCoinsInfo(source)
            }


            override fun newArray(size: Int): Array<UserCoinsInfo?> {

                return arrayOfNulls(size)
            }
        }
    }
}