package com.xiangjuncheng.kotlinbilibili.widget

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.xiangjuncheng.kotlinbilibili.R
import com.xiangjuncheng.kotlinbilibili.module.user.UserInfoDetailsActivity

class UserTagView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {

    private val avatarView: CircleImageView

    private val userNameText: TextView

    private var onClickListener: View.OnClickListener? = null

    private var activity: Activity? = null

    private var name: String? = null

    private var mid = -1

    private var avatarUrl: String? = null


    init {
        @SuppressLint("InflateParams")
        val cardView = LayoutInflater.from(context)
                .inflate(R.layout.layout_user_tag_view, null) as LinearLayout
        avatarView = cardView.findViewById(R.id.user_avatar) as CircleImageView
        userNameText = cardView.findViewById(R.id.user_name) as TextView

        val lp = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                resources.getDimensionPixelSize(R.dimen.user_tag_view_height))
        this.addView(cardView, lp)

        cardView.setOnClickListener { view ->

            if (mid != -1 && activity != null) {
                UserInfoDetailsActivity.launch(activity!!, name, mid, avatarUrl)
            } else if (onClickListener != null) {
                onClickListener!!.onClick(view)
            }
        }
    }


    fun setAvatar(bitmap: Bitmap) {

        avatarView.setImageBitmap(bitmap)
    }


    fun setAvatar(drawable: Drawable) {

        avatarView.setImageDrawable(drawable)
    }


    fun setAvatar(@DrawableRes id: Int) {

        avatarView.setImageResource(id)
    }


    fun setUserName(userName: String) {

        userNameText.text = userName
    }


    fun setUpWithInfo(activity: Activity, name: String, mid: Int, avatarUrl: String) {

        this.activity = activity
        this.name = name
        this.mid = mid
        this.avatarUrl = avatarUrl
        this.setUserName(name)

        Glide.with(context)
                .load(this.avatarUrl)
                .centerCrop()
                .dontAnimate()
                .placeholder(R.drawable.ico_user_default)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(avatarView)
    }


    override fun setOnClickListener(listener: View.OnClickListener?) {

        this.onClickListener = listener
    }
}
