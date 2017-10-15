package com.xiangjuncheng.kotlinbilibili.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.xiangjuncheng.kotlinbilibili.R
import com.xiangjuncheng.kotlinbilibili.adapter.helper.AbsRecyclerViewAdapter
import com.xiangjuncheng.kotlinbilibili.entity.video.VideoDetailsInfo
import com.xiangjuncheng.kotlinbilibili.utils.NumberUtil

/**
 * Created by xiangjuncheng on 2017/10/13.
 * 视频评论Adapter
 */
class VideoRelatedAdapter(recyclerView: RecyclerView, private val relates: List<VideoDetailsInfo.DataBean.RelatesBean>) : AbsRecyclerViewAdapter(recyclerView) {
    override fun getItemCount(): Int = relates.size


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClickableViewHolder {

        bindContext(parent.context)
        return ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.item_video_strip, parent, false))
    }


    override fun onBindViewHolder(holder: ClickableViewHolder, position: Int) {

        if (holder is ItemViewHolder) {
            val itemViewHolder = holder
            val relatesBean = relates[position]

            Glide.with(context)
                    .load(relatesBean.pic)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.bili_default_image_tv)
                    .dontAnimate()
                    .into(itemViewHolder.mVideoPic)

            itemViewHolder.mVideoTitle.text = relatesBean.title
            itemViewHolder.mVideoPlayNum.text = relatesBean.stat?.view?.let { NumberUtil.converString(it) }
            itemViewHolder.mVideoReviewNum.text = relatesBean.stat?.danmaku?.let { NumberUtil.converString(it) }
            itemViewHolder.mUpName.text = relatesBean.owner?.name
        }
        super.onBindViewHolder(holder, position)
    }


    inner class ItemViewHolder(itemView: View) : AbsRecyclerViewAdapter.ClickableViewHolder(itemView) {
        internal var mVideoPic: ImageView = `$`(R.id.item_img)
        internal var mVideoTitle: TextView = `$`(R.id.item_title)
        internal var mVideoPlayNum: TextView = `$`(R.id.item_play)
        internal var mVideoReviewNum: TextView = `$`(R.id.item_review)
        internal var mUpName: TextView = `$`(R.id.item_user_name)
    }
}