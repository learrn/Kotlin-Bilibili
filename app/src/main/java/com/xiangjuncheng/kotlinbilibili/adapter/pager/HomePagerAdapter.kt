package com.xiangjuncheng.kotlinbilibili.adapter.pager

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.xiangjuncheng.kotlinbilibili.R

/**
 * Created by xiangjuncheng on 2017/5/26.
 * 主界面Fragment模块Adapter
 */
class HomePagerAdapter : FragmentPagerAdapter {
    private var TITLES: Array<String>? = null

    private var mFragments: Array<Fragment>? = null
    override fun getItem(position: Int): Fragment {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCount(): Int = TITLES?.size as Int

    constructor(fm: FragmentManager, context: Context?) : super(fm) {
        TITLES = context?.resources?.getStringArray(R.array.sections)
    }

}