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
 * Created by xiangjuncheng on 2017/10/13.
 */
class VideoHotCommentAdapter(recyclerView: RecyclerView, private val hotComments: List<VideoCommentInfo.HotList>) : AbsRecyclerViewAdapter(recyclerView) {
    override fun getItemCount(): Int  = if (hotComments.isEmpty()) 0 else 3


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClickableViewHolder {

        bindContext(parent.context)
        return ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.item_video_comment, parent, false))
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ClickableViewHolder, position: Int) {

        if (holder is ItemViewHolder) {
            val mHolder = holder
            val hotList = hotComments[position]
            mHolder.mUserName.text = hotList.nick

            Glide.with(context)
                    .load(UrlHelper.getFaceUrlByUrl(hotList.face!!))
                    .centerCrop()
                    .dontAnimate()
                    .placeholder(R.drawable.ico_user_default)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(mHolder.mUserAvatar)

            val currentLevel = hotList.level_info?.current_level
            currentLevel?.let { checkLevel(it, mHolder) }

            when (hotList.sex) {
                "女" -> mHolder.mUserSex.setImageResource(R.drawable.ic_user_female)
                "男" -> mHolder.mUserSex.setImageResource(R.drawable.ic_user_male)
                else -> mHolder.mUserSex.setImageResource(R.drawable.ic_user_gay_border)
            }

            mHolder.mCommentNum.text = hotList.reply_count.toString()
            mHolder.mSpot.text=hotList.good.toString()
            val l = hotList.create_at?.let { DateUtil.stringToLong(it, "yyyy-MM-dd HH:mm") }
            val time = l?.let { DateUtil.getDescriptionTimeFromTimestamp(it) }
            mHolder.mCommentTime.text = time
            mHolder.mContent.text = hotList.msg
            mHolder.mFloor.text = "#" + hotList.lv

            if (position == hotComments.size - 1) {
                mHolder.mLine.visibility = View.GONE
            } else {
                mHolder.mLine.visibility = View.VISIBLE
            }
        }

        super.onBindViewHolder(holder, position)
    }


    private fun checkLevel(currentLevel: Int, mHolder: ItemViewHolder) {

        if (currentLevel == 0) {
            mHolder.mUserLv.setImageResource(R.drawable.ic_lv0)
        } else if (currentLevel == 1) {
            mHolder.mUserLv.setImageResource(R.drawable.ic_lv1)
        } else if (currentLevel == 2) {
            mHolder.mUserLv.setImageResource(R.drawable.ic_lv2)
        } else if (currentLevel == 3) {
            mHolder.mUserLv.setImageResource(R.drawable.ic_lv3)
        } else if (currentLevel == 4) {
            mHolder.mUserLv.setImageResource(R.drawable.ic_lv4)
        } else if (currentLevel == 5) {
            mHolder.mUserLv.setImageResource(R.drawable.ic_lv5)
        } else if (currentLevel == 6) {
            mHolder.mUserLv.setImageResource(R.drawable.ic_lv6)
        }
    }


    inner class ItemViewHolder(itemView: View) : ClickableViewHolder(itemView) {

        internal var mUserAvatar: CircleImageView = `$`(R.id.item_user_avatar)

        internal var mUserName: TextView = `$`(R.id.item_user_name)

        internal var mUserLv: ImageView = `$`(R.id.item_user_lever)

        internal var mUserSex: ImageView = `$`(R.id.item_user_sex)

        internal var mFloor: TextView = `$`(R.id.item_comment_floor)

        internal var mCommentTime: TextView = `$`(R.id.item_comment_time)

        internal var mCommentNum: TextView = `$`(R.id.item_comment_num)

        internal var mSpot: TextView = `$`(R.id.item_comment_spot)

        internal var mContent: TextView = `$`(R.id.item_comment_content)

        internal var mLine: View = `$`(R.id.line)


    }
}