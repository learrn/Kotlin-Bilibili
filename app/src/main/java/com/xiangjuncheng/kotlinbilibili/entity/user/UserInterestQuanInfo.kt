package com.xiangjuncheng.kotlinbilibili.entity.user

import android.os.Parcel
import android.os.Parcelable



/**
 * Created by xiangjuncheng on 2017/6/13.
 * 用户详情兴趣圈模型类
 */
class UserInterestQuanInfo : Parcelable {

    /**
     * ts : 1474365557
     * code : 0
     * type : 4
     * data : {"total_count":1,"total_page":1,"result":[{"id":23,"name":"东方","desc":"东方Project
     * 专题讨论&幻想乡住民的聚集地","thumb":"http://img.yo9.com/4b713fe0273211e6910600163e000cdb","post_count":479,"post_count_today":6,"is_join_community":1,"member_count":34500,"certification":0,"role_id":4,"post_nickname":"帖子","member_nickname":"乡民"}]}
     */

    var ts: String? = null

    var code: Int = 0

    var type: Int = 0

    /**
     * total_count : 1
     * total_page : 1
     * result : [{"id":23,"name":"东方","desc":"东方Project 专题讨论&幻想乡住民的聚集地","thumb":"http://img.yo9.com/4b713fe0273211e6910600163e000cdb","post_count":479,"post_count_today":6,"is_join_community":1,"member_count":34500,"certification":0,"role_id":4,"post_nickname":"帖子","member_nickname":"乡民"}]
     */

    var data: DataBean? = null


    class DataBean : Parcelable {

        var total_count: Int = 0

        var total_page: Int = 0

        /**
         * id : 23
         * name : 东方
         * desc : 东方Project 专题讨论&幻想乡住民的聚集地
         * thumb : http://img.yo9.com/4b713fe0273211e6910600163e000cdb
         * post_count : 479
         * post_count_today : 6
         * is_join_community : 1
         * member_count : 34500
         * certification : 0
         * role_id : 4
         * post_nickname : 帖子
         * member_nickname : 乡民
         */

        var result: List<ResultBean>? = null


        class ResultBean : Parcelable {

            var id: Int = 0

            var name: String? = null

            var desc: String? = null

            var thumb: String? = null

            var post_count: Int = 0

            var post_count_today: Int = 0

            var is_join_community: Int = 0

            var member_count: Int = 0

            var certification: Int = 0

            var role_id: Int = 0

            var post_nickname: String? = null

            var member_nickname: String? = null


            override fun describeContents(): Int {

                return 0
            }


            override fun writeToParcel(dest: Parcel, flags: Int) {

                dest.writeInt(this.id)
                dest.writeString(this.name)
                dest.writeString(this.desc)
                dest.writeString(this.thumb)
                dest.writeInt(this.post_count)
                dest.writeInt(this.post_count_today)
                dest.writeInt(this.is_join_community)
                dest.writeInt(this.member_count)
                dest.writeInt(this.certification)
                dest.writeInt(this.role_id)
                dest.writeString(this.post_nickname)
                dest.writeString(this.member_nickname)
            }


            protected constructor(`in`: Parcel) {

                this.id = `in`.readInt()
                this.name = `in`.readString()
                this.desc = `in`.readString()
                this.thumb = `in`.readString()
                this.post_count = `in`.readInt()
                this.post_count_today = `in`.readInt()
                this.is_join_community = `in`.readInt()
                this.member_count = `in`.readInt()
                this.certification = `in`.readInt()
                this.role_id = `in`.readInt()
                this.post_nickname = `in`.readString()
                this.member_nickname = `in`.readString()
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

            dest.writeInt(this.total_count)
            dest.writeInt(this.total_page)
            dest.writeList(this.result)
        }


        constructor() {

        }


        protected constructor(`in`: Parcel) {

            this.total_count = `in`.readInt()
            this.total_page = `in`.readInt()
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

        dest.writeString(this.ts)
        dest.writeInt(this.code)
        dest.writeInt(this.type)
        dest.writeParcelable(this.data, flags)
    }


    constructor() {

    }


    protected constructor(`in`: Parcel) {

        this.ts = `in`.readString()
        this.code = `in`.readInt()
        this.type = `in`.readInt()
        this.data = `in`.readParcelable<DataBean>(DataBean::class.java.classLoader)
    }

    companion object {


        val CREATOR: Parcelable.Creator<UserInterestQuanInfo> = object : Parcelable.Creator<UserInterestQuanInfo> {

            override fun createFromParcel(source: Parcel): UserInterestQuanInfo {

                return UserInterestQuanInfo(source)
            }


            override fun newArray(size: Int): Array<UserInterestQuanInfo?> {

                return arrayOfNulls(size)
            }
        }
    }
}