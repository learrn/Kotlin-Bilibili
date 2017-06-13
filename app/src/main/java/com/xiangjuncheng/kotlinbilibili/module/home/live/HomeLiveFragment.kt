package com.xiangjuncheng.kotlinbilibili.module.home.live

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import com.xiangjuncheng.kotlinbilibili.R
import com.xiangjuncheng.kotlinbilibili.adapter.LiveAppIndexAdapter
import com.xiangjuncheng.kotlinbilibili.base.RxLazyFragment
import com.xiangjuncheng.kotlinbilibili.network.RetrofitHelper
import kotlinx.android.synthetic.main.fragment_home_live.*

/**
 * Created by xiangjuncheng on 2017/6/13.
 */
object HomeLiveFragment : RxLazyFragment() {
    private var mLiveAppIndexAdapter: LiveAppIndexAdapter? = null

    override fun getLayoutResId(): Int = R.layout.fragment_home_live

    override fun finishCreateView(state: Bundle?) {
        isPrepared = true
        lazyLoad()
    }

    public override fun lazyLoad() {
        if (!isPrepared || !isVisible) {
            return
        }
        initRefreshLayout()
        initRecyclerView()
        isPrepared = false
    }

    override fun initRecyclerView() {
        mLiveAppIndexAdapter = LiveAppIndexAdapter(activity)
        recycle.adapter = mLiveAppIndexAdapter
        val layout = GridLayoutManager(activity, 12)
        layout.orientation = LinearLayoutManager.VERTICAL
        layout.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int = mLiveAppIndexAdapter.getSpanSize(position)
        }
        recycle.layoutManager = layout
    }

    override fun initRefreshLayout() {
        swipe_refresh_layout.setColorSchemeResources(R.color.colorPrimary)
        swipe_refresh_layout.setOnRefreshListener { this::loadData }
        swipe_refresh_layout.post {
            swipe_refresh_layout.isRefreshing = true
            loadData()
        }
    }

    override fun loadData() {
        RetrofitHelper.getLiveAPI()
    }
}
