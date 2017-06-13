package com.xiangjuncheng.kotlinbilibili.entity.user

import android.os.Parcel
import android.os.Parcelable



/**
 * Created by xiangjuncheng on 2017/6/13.
 * 用户详情界面的直播状态模型类
 */
class UserLiveRoomStatusInfo : Parcelable {

    /**
     * code : 0
     * message :
     * data : {"roomStatus":1,"liveStatus":1,"url":"http://live.bilibili.com/36152","title":"芹菜的日常赶稿直播间","cover":"http://i0.hdslb.com/bfs/live/127e871d73f6dc39d1261d5e6cb8626bbbdcf672.jpg","online":118,"roomid":36152}
     */

    var code: Int = 0

    var message: String? = null

    /**
     * roomStatus : 1
     * liveStatus : 1
     * url : http://live.bilibili.com/36152
     * title : 芹菜的日常赶稿直播间
     * cover : http://i0.hdslb.com/bfs/live/127e871d73f6dc39d1261d5e6cb8626bbbdcf672.jpg
     * online : 118
     * roomid : 36152
     */

    var data: DataBean? = null


    class DataBean : Parcelable {

        var roomStatus: Int = 0

        var liveStatus: Int = 0

        var url: String? = null

        var title: String? = null

        var cover: String? = null

        var online: Int = 0

        var roomid: Int = 0


        override fun describeContents(): Int {

            return 0
        }


        override fun writeToParcel(dest: Parcel, flags: Int) {

            dest.writeInt(this.roomStatus)
            dest.writeInt(this.liveStatus)
            dest.writeString(this.url)
            dest.writeString(this.title)
            dest.writeString(this.cover)
            dest.writeInt(this.online)
            dest.writeInt(this.roomid)
        }




        protected constructor(`in`: Parcel) {

            this.roomStatus = `in`.readInt()
            this.liveStatus = `in`.readInt()
            this.url = `in`.readString()
            this.title = `in`.readString()
            this.cover = `in`.readString()
            this.online = `in`.readInt()
            this.roomid = `in`.readInt()
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

        dest.writeInt(this.code)
        dest.writeString(this.message)
        dest.writeParcelable(this.data, flags)
    }


    constructor() {

    }


    protected constructor(`in`: Parcel) {

        this.code = `in`.readInt()
        this.message = `in`.readString()
        this.data = `in`.readParcelable<DataBean>(DataBean::class.java.classLoader)
    }

    companion object {


        val CREATOR: Parcelable.Creator<UserLiveRoomStatusInfo> = object : Parcelable.Creator<UserLiveRoomStatusInfo> {

            override fun createFromParcel(source: Parcel): UserLiveRoomStatusInfo {

                return UserLiveRoomStatusInfo(source)
            }


            override fun newArray(size: Int): Array<UserLiveRoomStatusInfo?> {

                return arrayOfNulls(size)
            }
        }
    }
}