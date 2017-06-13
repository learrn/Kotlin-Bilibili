package com.xiangjuncheng.kotlinbilibili.entity.user

import android.os.Parcel
import android.os.Parcelable



/**
 * Created by xiangjuncheng on 2017/6/13.
 * 用户收藏夹模型类
 */
class UserFavoritesInfo : Parcelable {

    /**
     * code : 0
     * data : [{"fid":1361235,"mid":159122,"name":"默认收藏夹","max_count":200,"cur_count":161,"atten_count":0,"state":0,"ctime":1438922096,"videos":[{"aid":6479279,"pic":"http://i2.hdslb.com/bfs/archive/64905249599c1f3bcde0789693302020ca755be5.jpg"},{"aid":6594604,"pic":"http://i2.hdslb.com/bfs/archive/d6e914a0a7b31bc4f8c510e5fed22e0af2e2c472.jpg"},{"aid":6625642,"pic":"http://i1.hdslb.com/bfs/archive/bf89b962bfd200d2c54ff5be41b7036250140364.jpg"}]},{"fid":24411892,"mid":159122,"name":"童年","max_count":150,"cur_count":65,"atten_count":0,"state":2,"ctime":1456309984,"videos":[{"aid":2836581,"pic":"http://i1.hdslb.com/video/63/633da1fd395a82c0edf76c39e61b8786.jpg"},{"aid":6049123,"pic":"http://i0.hdslb.com/bfs/archive/756459bd7d815014c78949ac95c27c7f77606098.jpg"},{"aid":5804090,"pic":"http://i0.hdslb.com/bfs/archive/659b42b0975b290e7b4219b1ec47b97287244fda.jpg"}]}]
     */

    var code: Int = 0

    /**
     * fid : 1361235
     * mid : 159122
     * name : 默认收藏夹
     * max_count : 200
     * cur_count : 161
     * atten_count : 0
     * state : 0
     * ctime : 1438922096
     * videos : [{"aid":6479279,"pic":"http://i2.hdslb.com/bfs/archive/64905249599c1f3bcde0789693302020ca755be5.jpg"},{"aid":6594604,"pic":"http://i2.hdslb.com/bfs/archive/d6e914a0a7b31bc4f8c510e5fed22e0af2e2c472.jpg"},{"aid":6625642,"pic":"http://i1.hdslb.com/bfs/archive/bf89b962bfd200d2c54ff5be41b7036250140364.jpg"}]
     */

    var data: List<DataBean>? = null


    class DataBean : Parcelable {

        var fid: Int = 0

        var mid: Int = 0

        var name: String? = null

        var max_count: Int = 0

        var cur_count: Int = 0

        var atten_count: Int = 0

        var state: Int = 0

        var ctime: Int = 0

        /**
         * aid : 6479279
         * pic : http://i2.hdslb.com/bfs/archive/64905249599c1f3bcde0789693302020ca755be5.jpg
         */

        var videos: List<VideosBean>? = null


        class VideosBean : Parcelable {

            var aid: Int = 0

            var pic: String? = null


            override fun describeContents(): Int {

                return 0
            }


            override fun writeToParcel(dest: Parcel, flags: Int) {

                dest.writeInt(this.aid)
                dest.writeString(this.pic)
            }



            protected constructor(`in`: Parcel) {

                this.aid = `in`.readInt()
                this.pic = `in`.readString()
            }

            companion object {


                val CREATOR: Parcelable.Creator<VideosBean> = object : Parcelable.Creator<VideosBean> {

                    override fun createFromParcel(source: Parcel): VideosBean {

                        return VideosBean(source)
                    }


                    override fun newArray(size: Int): Array<VideosBean?> {

                        return arrayOfNulls(size)
                    }
                }
            }
        }


        override fun describeContents(): Int {

            return 0
        }


        override fun writeToParcel(dest: Parcel, flags: Int) {

            dest.writeInt(this.fid)
            dest.writeInt(this.mid)
            dest.writeString(this.name)
            dest.writeInt(this.max_count)
            dest.writeInt(this.cur_count)
            dest.writeInt(this.atten_count)
            dest.writeInt(this.state)
            dest.writeInt(this.ctime)
            dest.writeList(this.videos)
        }


        constructor() {

        }


        protected constructor(`in`: Parcel) {

            this.fid = `in`.readInt()
            this.mid = `in`.readInt()
            this.name = `in`.readString()
            this.max_count = `in`.readInt()
            this.cur_count = `in`.readInt()
            this.atten_count = `in`.readInt()
            this.state = `in`.readInt()
            this.ctime = `in`.readInt()
            this.videos = ArrayList<VideosBean>()
            `in`.readList(this.videos, VideosBean::class.java.classLoader)
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
        dest.writeList(this.data)
    }


    constructor() {

    }


    protected constructor(`in`: Parcel) {

        this.code = `in`.readInt()
        this.data = ArrayList<DataBean>()
        `in`.readList(this.data, DataBean::class.java.classLoader)
    }

    companion object {


        val CREATOR: Parcelable.Creator<UserFavoritesInfo> = object : Parcelable.Creator<UserFavoritesInfo> {

            override fun createFromParcel(source: Parcel): UserFavoritesInfo {

                return UserFavoritesInfo(source)
            }


            override fun newArray(size: Int): Array<UserFavoritesInfo?> {

                return arrayOfNulls(size)
            }
        }
    }
}