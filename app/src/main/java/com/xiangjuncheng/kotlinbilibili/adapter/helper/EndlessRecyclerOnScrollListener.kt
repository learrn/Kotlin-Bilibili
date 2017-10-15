package com.xiangjuncheng.kotlinbilibili.adapter.helper

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

/**
 * Created by xjc on 2017/10/15.
 * 自定义RecylcerView上拉加载处理
 */
abstract class EndlessRecyclerOnScrollListener protected constructor(private val mLinearLayoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {

    private var previousTotal = 0
    private var loading = true
    private var currentPage = 1


    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (dy == 0) {
            return
        }

        val visibleItemCount = recyclerView!!.childCount
        val totalItemCount = mLinearLayoutManager.itemCount
        val lastCompletelyVisiableItemPosition = mLinearLayoutManager.findLastCompletelyVisibleItemPosition()

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false
                previousTotal = totalItemCount
            }
        }

        if (!loading && visibleItemCount > 0 &&
                lastCompletelyVisiableItemPosition >= totalItemCount - 1) {
            currentPage++
            onLoadMore(currentPage)
            loading = true
        }
    }


    fun refresh() {
        loading = true
        previousTotal = 0
        currentPage = 1
    }

    abstract fun onLoadMore(currentPage: Int)
}