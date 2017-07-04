package com.xiangjuncheng.kotlinbilibili.adapter.pager

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.util.Log
import com.xiangjuncheng.kotlinbilibili.R
import com.xiangjuncheng.kotlinbilibili.module.home.live.HomeLiveFragment
import com.xiangjuncheng.kotlinbilibili.module.home.recommend.HomeRecommendedFragment

/**
 * Created by xiangjuncheng on 2017/5/26.
 * 主界面Fragment模块Adapter
 */
class HomePagerAdapter(fm: FragmentManager, context: Context?) : FragmentPagerAdapter(fm) {
    private var TITLES: Array<String>? = null

    private var mFragments : Array<Fragment?>? =null

    init {
        TITLES = context?.resources?.getStringArray(R.array.sections)
        mFragments = arrayOfNulls<Fragment>(TITLES!!.size)
    }

    override fun getItem(position: Int): Fragment? {
        if (mFragments?.get(position) == null) {
            when (position) {
                0 -> mFragments?.set(position, HomeLiveFragment)
                1 -> mFragments?.set(position, HomeRecommendedFragment)
//                2 -> mFragments?.set(position, HomeBangumiFragment.newInstance())
//                3 -> mFragments?.set(position, HomeRegionFragment.newInstance())
//                4 -> mFragments?.set(position, HomeAttentionFragment.newIntance())
//                5 -> mFragments?.set(position, HomeDiscoverFragment.newInstance())
//                else -> {
//                }
            }
        }
        Log.e("xjc","mFragments = $mFragments , ${mFragments?.get(position)}")
        return mFragments?.get(position)
    }

    override fun getCount(): Int = TITLES?.size as Int

    override fun getPageTitle(position: Int): CharSequence? = TITLES?.get(position)
}