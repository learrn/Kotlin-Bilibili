package com.xiangjuncheng.kotlinbilibili.entity.user

import android.os.Parcel
import android.os.Parcelable



/**
 * Created by xiangjuncheng on 2017/6/13.
 * 用户详情所玩游戏模型类
 */
class UserPlayGameInfo : Parcelable {

    /**
     * status : true
     * data : {"games":[{"website":"http://yys.biligame.com/","image":"http://i0.hdslb.com/bfs/game/3b205675d44bbd90e6ea46d4baec9674bda6e642.png","name":"阴阳师"},{"website":"http://djsy.biligame.com/","image":"http://i0.hdslb.com/bfs/game/80008bbf4cb9b0343fd6e4325127645b2323c1a3.png","name":"刀剑神域黑衣剑士"},{"website":"http://acg.tv/u1g3","image":"http://i0.hdslb.com/u_user/7baceb341073fe823faad36d2e1c805e.png","name":"ICHU偶像进行曲"},{"website":"http://100p.biligame.com/","image":"http://i0.hdslb.com/bfs/game/3e8f079c18c2f81627703c0914e3c285f6d1a7b2.png","name":"梦100"},{"website":"http://xsqst.biligame.com/","image":"http://i2.hdslb.com/u_user/b3c01eb5b7d9925e4488f581baef8006.jpg","name":"像素骑士团"}],"count":5}
     */

    var isStatus: Boolean = false

    /**
     * games : [{"website":"http://yys.biligame.com/","image":"http://i0.hdslb.com/bfs/game/3b205675d44bbd90e6ea46d4baec9674bda6e642.png","name":"阴阳师"},{"website":"http://djsy.biligame.com/","image":"http://i0.hdslb.com/bfs/game/80008bbf4cb9b0343fd6e4325127645b2323c1a3.png","name":"刀剑神域黑衣剑士"},{"website":"http://acg.tv/u1g3","image":"http://i0.hdslb.com/u_user/7baceb341073fe823faad36d2e1c805e.png","name":"ICHU偶像进行曲"},{"website":"http://100p.biligame.com/","image":"http://i0.hdslb.com/bfs/game/3e8f079c18c2f81627703c0914e3c285f6d1a7b2.png","name":"梦100"},{"website":"http://xsqst.biligame.com/","image":"http://i2.hdslb.com/u_user/b3c01eb5b7d9925e4488f581baef8006.jpg","name":"像素骑士团"}]
     * count : 5
     */

    var data: DataBean? = null


    class DataBean : Parcelable {

        var count: Int = 0

        /**
         * website : http://yys.biligame.com/
         * image : http://i0.hdslb.com/bfs/game/3b205675d44bbd90e6ea46d4baec9674bda6e642.png
         * name : 阴阳师
         */

        var games: List<GamesBean>? = null


        class GamesBean : Parcelable {

            var website: String? = null

            var image: String? = null

            var name: String? = null


            override fun describeContents(): Int {

                return 0
            }


            override fun writeToParcel(dest: Parcel, flags: Int) {

                dest.writeString(this.website)
                dest.writeString(this.image)
                dest.writeString(this.name)
            }


            protected constructor(`in`: Parcel) {

                this.website = `in`.readString()
                this.image = `in`.readString()
                this.name = `in`.readString()
            }

            companion object {


                val CREATOR: Parcelable.Creator<GamesBean> = object : Parcelable.Creator<GamesBean> {

                    override fun createFromParcel(source: Parcel): GamesBean {

                        return GamesBean(source)
                    }


                    override fun newArray(size: Int): Array<GamesBean?> {

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
            dest.writeList(this.games)
        }




        protected constructor(`in`: Parcel) {

            this.count = `in`.readInt()
            this.games = ArrayList<GamesBean>()
            `in`.readList(this.games, GamesBean::class.java.classLoader)
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


    protected constructor(`in`: Parcel) {

        this.isStatus = `in`.readByte().toInt() != 0
        this.data = `in`.readParcelable<DataBean>(DataBean::class.java.classLoader)
    }

    companion object {


        val CREATOR: Parcelable.Creator<UserPlayGameInfo> = object : Parcelable.Creator<UserPlayGameInfo> {

            override fun createFromParcel(source: Parcel): UserPlayGameInfo {

                return UserPlayGameInfo(source)
            }


            override fun newArray(size: Int): Array<UserPlayGameInfo?> {

                return arrayOfNulls(size)
            }
        }
    }
}