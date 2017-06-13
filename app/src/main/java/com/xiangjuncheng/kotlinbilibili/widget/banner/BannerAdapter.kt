package com.xiangjuncheng.kotlinbilibili.widget.banner

import android.view.ViewGroup
import android.view.ViewParent
import android.widget.ImageView.ScaleType
import android.support.v4.view.PagerAdapter
import android.view.View
import android.widget.ImageView


/**
 * Created by xiangjuncheng on 2017/6/13.
 * Banner适配器
 */
class BannerAdapter internal constructor(private val mList: List<ImageView>) : PagerAdapter() {

    private var pos: Int = 0

    private var mViewPagerOnItemClickListener: ViewPagerOnItemClickListener? = null


    internal fun setmViewPagerOnItemClickListener(mViewPagerOnItemClickListener: ViewPagerOnItemClickListener) {

        this.mViewPagerOnItemClickListener = mViewPagerOnItemClickListener
    }


    override fun getCount(): Int = Integer.MAX_VALUE

    override fun isViewFromObject(arg0: View, arg1: Any): Boolean = arg0 === arg1


    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var position = position

        //对ViewPager页号求模取出View列表中要显示的项
        position %= mList.size
        if (position < 0) {
            position = mList.size + position
        }
        val v = mList[position]
        pos = position
        v.scaleType = ImageView.ScaleType.CENTER
        //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
        (v.parent as? ViewGroup)?.removeView(v)
        v.setOnClickListener({ v1 ->
            if (mViewPagerOnItemClickListener != null) {
                mViewPagerOnItemClickListener!!.onItemClick()
            }
        })
        container.addView(v)
        return v
    }


    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
    }


    internal interface ViewPagerOnItemClickListener {
        fun onItemClick()
    }
}