package com.xiangjuncheng.kotlinbilibili.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.xiangjuncheng.kotlinbilibili.R
import com.xiangjuncheng.kotlinbilibili.entity.live.LiveAppIndexInfo
import com.xiangjuncheng.kotlinbilibili.widget.banner.BannerEntity


/**
 * Created by xiangjuncheng on 2017/6/13.
 */
class LiveAppIndexAdapter(context: Context) : RecyclerView.Adapter<> {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: ?, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private var context: Context? = null

    private var mLiveAppIndexInfo: LiveAppIndexInfo? = null

    private var entranceSize: Int = 0

    private val entranceTitles = arrayOf("关注主播", "直播中心", "搜索直播", "全部分类")

    companion object {
        //直播分类入口
        private val TYPE_ENTRANCE = 0

        //直播Item
        private val TYPE_LIVE_ITEM = 1

        //直播分类Title
        private val TYPE_PARTITION = 2

        //直播页Banner
        private val TYPE_BANNER = 3
    }


    private var bannerEntitys : ArrayList<BannerEntity>? = null

    private var liveSizes : ArrayList<Int>? = null

    private val entranceIconRes = intArrayOf(R.drawable.live_home_follow_anchor, R.drawable.live_home_live_center, R.drawable.live_home_search_room, R.drawable.live_home_all_category)

    init {
        this.context = context;
    }

    fun setLiveInfo(liveAppIndexInfo : LiveAppIndexInfo){
        mLiveAppIndexInfo = liveAppIndexInfo
        entranceSize = 4
        liveSizes?.clear()
        bannerEntitys?.clear()
        val tempSize = 0
        val partitionSize = mLiveAppIndexInfo?.data?.partitions?.size
        //TODO
    }
}