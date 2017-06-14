package com.xiangjuncheng.kotlinbilibili.adapter

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.xiangjuncheng.kotlinbilibili.R
import com.xiangjuncheng.kotlinbilibili.entity.live.LiveAppIndexInfo
import com.xiangjuncheng.kotlinbilibili.widget.CircleImageView
import com.xiangjuncheng.kotlinbilibili.widget.banner.BannerEntity
import com.xiangjuncheng.kotlinbilibili.widget.banner.BannerView
import kotlinx.android.synthetic.main.item_live_banner.view.*
import kotlinx.android.synthetic.main.item_live_entrance.view.*
import kotlinx.android.synthetic.main.item_live_partition.view.*
import kotlinx.android.synthetic.main.item_live_partition_title.view.*


/**
 * Created by xiangjuncheng on 2017/6/13.
 * 首页直播adapter
 */
class LiveAppIndexAdapter(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
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

        //直播界面Banner ViewHolder
        class LiveBannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
            val banner : BannerView = itemView.item_live_banner as BannerView
        }

        //直播界面Item分类 ViewHolder
        class LiveEntranceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val tv: TextView = itemView.live_entrance_title
            val image: ImageView = itemView.live_entrance_image

        }

        //直播界面Grid Item ViewHolder
        class LiveItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val itemLiveCover: ImageView = itemView.item_live_cover
            val itemLiveUser: TextView = itemView.item_live_user
            val itemLiveTitle: TextView = itemView.item_live_title
            val itemLiveUserCover : CircleImageView = itemView.item_live_user_cover as CircleImageView
            val itemLiveCount : TextView = itemView.item_live_count
            val itemLiveLayout : CardView = itemView.item_live_layout
        }

        //直播界面分区类型 ViewHolder
        class LivePartitionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
            val itemIcon : ImageView = itemView.item_live_partition_icon
            val itemTitle : TextView = itemView.item_live_partition_title
            val itemCount : TextView = itemTitle.item_live_partition_count
        }


    }

    private var bannerEntitys: ArrayList<BannerEntity>? = null


    private var liveSizes: ArrayList<Int>? = null

    private val entranceIconRes = intArrayOf(R.drawable.live_home_follow_anchor, R.drawable.live_home_live_center, R.drawable.live_home_search_room, R.drawable.live_home_all_category)

    init {
        this.context = context
    }

    fun setLiveInfo(liveAppIndexInfo: LiveAppIndexInfo) {
        mLiveAppIndexInfo = liveAppIndexInfo
        entranceSize = 4
        liveSizes?.clear()
        bannerEntitys?.clear()
        val tempSize = 0
        val partitionSize = mLiveAppIndexInfo?.data?.partitions?.size
        //TODO
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? {
        var view: View
        when (viewType) {
            TYPE_ENTRANCE -> {
                view = LayoutInflater.from(parent?.context).inflate(R.layout.item_live_entrance, null)
                return LiveEntranceViewHolder(view)
            }
            TYPE_LIVE_ITEM -> {
                view = LayoutInflater.from(parent?.context).inflate(R.layout.item_live_partition, null)
                return LiveItemViewHolder(view)
            }
            TYPE_PARTITION -> {
                view = LayoutInflater.from(parent?.context).inflate(R.layout.item_live_partition_title, null)
                return LiveItemViewHolder(view)
            }
            TYPE_BANNER -> {
                view = LayoutInflater.from(parent?.context).inflate(R.layout.item_live_banner, null)
                return LiveItemViewHolder(view)
            }
            else -> return null
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        var position = -1
        var liveBean: LiveAppIndexInfo.DataBean.PartitionsBean.LivesBean?
        when (holder) {
            is LiveEntranceViewHolder -> {
                holder.tv.text = entranceTitles[position]
                Glide.with(context)
                        .load(entranceIconRes[position])
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.image)
            }
            is LiveItemViewHolder -> {
                liveBean = mLiveAppIndexInfo?.data?.partitions?.get(getItemPosition(position))?.lives?.get(position - 1 - entranceSize - getItemPosition(position) * 5)
                Glide.with(context)
                        .load(liveBean?.cover?.src)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.bili_default_image_tv)
                        .dontAnimate()
                        .into(holder.itemLiveCover)
                Glide.with(context)
                        .load(liveBean?.cover?.src)
                        .centerCrop()
                        .dontAnimate()
                        .placeholder(R.drawable.ico_user_default)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.itemLiveCover)
                holder.itemLiveTitle.text = liveBean?.title
                holder.itemLiveUser.text = liveBean?.owner?.name
                holder.itemLiveCount.text = liveBean?.online.toString()
                holder.itemLiveLayout.setOnClickListener { }//LivePlayActivity. }
            }
            is LivePartitionViewHolder -> {
                val partition = mLiveAppIndexInfo?.data?.partitions?.get(getItemPosition(position))?.partition
                Glide.with(context)
                        .load(partition?.sub_icon?.src)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.itemIcon)
                holder.itemTitle.text = partition?.name
                val stringBuilder = SpannableStringBuilder(
                        "当前" + partition?.count + "个直播")
                val foregroundColorSpan = ForegroundColorSpan(
                        context?.resources!!.getColor(R.color.pink_text_color))
                stringBuilder.setSpan(foregroundColorSpan,2,stringBuilder.length-3,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                holder.itemCount.text = stringBuilder
            }
            is LiveBannerViewHolder -> bannerEntitys?.let { holder.banner.delayTime(5).build(it) }
        }
    }

    override fun getItemCount(): Int {
        if (mLiveAppIndexInfo != null){
            return 1 + entranceIconRes.size + mLiveAppIndexInfo?.data?.partitions?.size!!.times(5)
        }else{
            return 0
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0)
            return TYPE_BANNER
        var position =position - 1
        if (position < entranceSize) {
            return TYPE_ENTRANCE
        } else if (isPartitionTitle(position)) {
            return TYPE_PARTITION
        } else {
            return TYPE_LIVE_ITEM
        }
    }

    /**
     * 获取当前Item在第几组中
     */
    private fun getItemPosition(pos: Int): Int = (pos - entranceSize) / 5

    private fun isPartitionTitle(pos: Int): Boolean = (pos - entranceSize) % 5 == 0

}
