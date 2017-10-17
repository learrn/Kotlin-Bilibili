package com.xiangjuncheng.kotlinbilibili.adapter

import android.annotation.SuppressLint
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
import com.xiangjuncheng.kotlinbilibili.entity.video.VideoCommentInfo
import com.xiangjuncheng.kotlinbilibili.network.auxiliary.UrlHelper
import com.xiangjuncheng.kotlinbilibili.utils.DateUtil
import com.xiangjuncheng.kotlinbilibili.widget.CircleImageView

/**
 * Created by xjc on 2017/10/15.
 * 视频评论界面
 */
class VideoCommentAdapter(recyclerView: RecyclerView, private val comments: List<VideoCommentInfo.List>) : AbsRecyclerViewAdapter(recyclerView) {
    override fun getItemCount(): Int = comments.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClickableViewHolder {
        bindContext(parent.context)
        return ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.item_video_comment, parent, false))
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ClickableViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            val mHolder = holder
            val list = comments[position]
            mHolder.mUserName.text = list.nick

            Glide.with(context)
                    .load(UrlHelper.getFaceUrlByUrl(list.face!!))
                    .centerCrop()
                    .dontAnimate()
                    .placeholder(R.drawable.ico_user_default)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(mHolder.mUserAvatar)

            val currentLevel = list.level_info?.current_level
            checkLevel(currentLevel!!, mHolder)

            mHolder.mCommentNum.text = list.reply_count.toString()
            mHolder.mSpot.text = list.good.toString()
            val l = list.create_at?.let { DateUtil.stringToLong(it, "yyyy-MM-dd HH:mm") }
            val time = DateUtil.getDescriptionTimeFromTimestamp(l!!)
            mHolder.mCommentTime.text = time
            mHolder.mContent.text = list.msg
            mHolder.mFloor.text = "#" + list.lv
        }
        super.onBindViewHolder(holder, position)
    }


    private fun checkLevel(currentLevel: Int, mHolder: ItemViewHolder) {
        when (currentLevel) {
            0 -> mHolder.mUserLv.setImageResource(R.drawable.ic_lv0)
            1 -> mHolder.mUserLv.setImageResource(R.drawable.ic_lv1)
            2 -> mHolder.mUserLv.setImageResource(R.drawable.ic_lv2)
            3 -> mHolder.mUserLv.setImageResource(R.drawable.ic_lv3)
            4 -> mHolder.mUserLv.setImageResource(R.drawable.ic_lv4)
            5 -> mHolder.mUserLv.setImageResource(R.drawable.ic_lv5)
            6 -> mHolder.mUserLv.setImageResource(R.drawable.ic_lv6)
        }
    }
    inner class ItemViewHolder(itemView: View) : AbsRecyclerViewAdapter.ClickableViewHolder(itemView) {

        internal var mUserAvatar: CircleImageView = `$`(R.id.item_user_avatar)
        internal var mUserName: TextView = `$`(R.id.item_user_name)
        internal var mUserLv: ImageView = `$`(R.id.item_user_lever)
        internal var mFloor: TextView = `$`(R.id.item_comment_floor)
        internal var mCommentTime: TextView = `$`(R.id.item_comment_time)
        internal var mCommentNum: TextView = `$`(R.id.item_comment_num)
        internal var mSpot: TextView = `$`(R.id.item_comment_spot)
        internal var mContent: TextView = `$`(R.id.item_comment_content)

    }
}