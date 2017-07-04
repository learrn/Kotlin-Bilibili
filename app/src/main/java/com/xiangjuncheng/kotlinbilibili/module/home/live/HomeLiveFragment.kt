package com.xiangjuncheng.kotlinbilibili.module.home.live

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.xiangjuncheng.kotlinbilibili.R
import com.xiangjuncheng.kotlinbilibili.adapter.LiveAppIndexAdapter
import com.xiangjuncheng.kotlinbilibili.base.RxLazyFragment
import com.xiangjuncheng.kotlinbilibili.network.RetrofitHelper
import kotlinx.android.synthetic.main.fragment_home_live.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


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
            override fun getSpanSize(position: Int): Int = mLiveAppIndexAdapter?.getSpanSize(position) as Int
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
        initEmptyView()
//        RetrofitHelper.getLiveAPI()
//                .liveAppIndex
//                .compose(bindToLifecycle())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({ liveAppIndexInfo ->
//                    mLiveAppIndexAdapter?.setLiveInfo(liveAppIndexInfo)
//                    finishTask()
//                }) { initEmptyView() }
    }

    private fun initEmptyView() {

        swipe_refresh_layout.isRefreshing = false
        empty_layout.visibility = View.VISIBLE
        recycle.visibility = View.GONE
        empty_layout.setEmptyImage(R.drawable.img_tips_error_load_error)
        empty_layout.setEmptyText("加载失败~(≧▽≦)~啦啦啦.")
        //SnackbarUtil.showMessage(recycle, "数据加载失败,请重新加载或者检查网络是否链接")
    }


    fun hideEmptyView() {
        empty_layout.visibility = View.GONE
        recycle.visibility = View.VISIBLE
    }


    override fun finishTask() {
        hideEmptyView()
        swipe_refresh_layout.isRefreshing = false
        mLiveAppIndexAdapter?.notifyDataSetChanged()
        recycle.scrollToPosition(0)
    }
}
