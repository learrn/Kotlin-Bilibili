package com.xiangjuncheng.kotlinbilibili.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.xiangjuncheng.kotlinbilibili.R


/**
 * Created by xiangjuncheng on 2017/6/7.
 */
class CustomEmptyView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {

    private var mEmptyImg: ImageView? = null

    private var mEmptyText: TextView? = null


    init {
        init()
    }


    fun init() {

        val view = LayoutInflater.from(context).inflate(R.layout.layout_empty, this)
        mEmptyImg = view.findViewById(R.id.empty_img) as ImageView
        mEmptyText = view.findViewById(R.id.empty_text) as TextView
    }


    fun setEmptyImage(imgRes: Int) {

        mEmptyImg!!.setImageResource(imgRes)
    }


    fun setEmptyText(text: String) {

        mEmptyText!!.text = text
    }
}