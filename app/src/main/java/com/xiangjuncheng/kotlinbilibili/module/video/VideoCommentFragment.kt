package com.xiangjuncheng.kotlinbilibili.module.video

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import com.xiangjuncheng.kotlinbilibili.R
import com.xiangjuncheng.kotlinbilibili.adapter.HeaderViewRecyclerAdapter
import com.xiangjuncheng.kotlinbilibili.adapter.VideoCommentAdapter
import com.xiangjuncheng.kotlinbilibili.adapter.VideoHotCommentAdapter
import com.xiangjuncheng.kotlinbilibili.adapter.helper.EndlessRecyclerOnScrollListener
import com.xiangjuncheng.kotlinbilibili.base.RxLazyFragment
import com.xiangjuncheng.kotlinbilibili.entity.video.VideoCommentInfo
import com.xiangjuncheng.kotlinbilibili.network.RetrofitHelper
import com.xiangjuncheng.kotlinbilibili.utils.ConstantUtil
import kotlinx.android.synthetic.main.fragment_video_comment.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.ArrayList

/**
 * Created by xiangjuncheng on 2017/10/13.
 */
class VideoCommentFragment : RxLazyFragment() {
    override fun getLayoutResId(): Int =R.layout.fragment_video_comment

    override fun finishCreateView(state: Bundle?) {
        aid = arguments.getInt(ConstantUtil.AID)
        initRecyclerView()
        loadData()
    }

    private val comments = ArrayList<VideoCommentInfo.List>()

    private val hotComments = ArrayList<VideoCommentInfo.HotList>()

    private var mAdapter: HeaderViewRecyclerAdapter? = null

    private var pageNum = 1

    private val pageSize = 20

    private var loadMoreView: View? = null

    private var aid: Int = 0

    private var mVideoHotCommentAdapter: VideoHotCommentAdapter? = null

    private var headView: View? = null

    override fun initRecyclerView() {

        val mRecyclerAdapter = VideoCommentAdapter(recycle, comments)
        recycle.setHasFixedSize(true)
        val mLinearLayoutManager = LinearLayoutManager(activity)
        recycle.layoutManager = mLinearLayoutManager
        mAdapter = HeaderViewRecyclerAdapter(mRecyclerAdapter)
        recycle.adapter = mAdapter
        createHeadView()
        createLoadMoreView()
        recycle.addOnScrollListener(object : EndlessRecyclerOnScrollListener(mLinearLayoutManager) {
            override fun onLoadMore(currentPage: Int) {
                pageNum++
                loadData()
                loadMoreView!!.visibility = View.VISIBLE
            }
        })
    }


    override fun loadData() {

        val ver = 3
        RetrofitHelper.getBiliAPI()
                .getVideoComment(aid, pageNum, pageSize, ver)
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ videoComment ->
                    val list = videoComment.list
                    val hotList = videoComment.hotList!!
                    if ( pageSize < list?.size!!) {
                        loadMoreView!!.visibility = View.GONE
                        mAdapter!!.removeFootView()
                    }
                    comments.addAll(list)
                    hotComments.addAll(hotList)
                    finishTask()
                }, { _ ->
                    loadMoreView!!.visibility = View.GONE
                    headView!!.visibility = View.GONE
                })
    }


    override fun finishTask() {

        loadMoreView!!.visibility = View.GONE
        mVideoHotCommentAdapter!!.notifyDataSetChanged()

        if (pageNum * pageSize - pageSize - 1 > 0) {
            mAdapter!!.notifyItemRangeChanged(pageNum * pageSize - pageSize - 1, pageSize)
        } else {
            mAdapter!!.notifyDataSetChanged()
        }
    }


    private fun createHeadView() {

        headView = LayoutInflater.from(activity).inflate(R.layout.layout_video_hot_comment_head,
                recycle, false)
        val mHotCommentRecycler = headView!!.findViewById(
                R.id.hot_comment_recycler) as RecyclerView
        mHotCommentRecycler.setHasFixedSize(false)
        mHotCommentRecycler.isNestedScrollingEnabled = false
        mHotCommentRecycler.layoutManager = LinearLayoutManager(activity)
        mVideoHotCommentAdapter = VideoHotCommentAdapter(mHotCommentRecycler, hotComments)
        mHotCommentRecycler.adapter = mVideoHotCommentAdapter
        mAdapter!!.addHeaderView(headView!!)
    }


    private fun createLoadMoreView() {

        loadMoreView = LayoutInflater.from(activity)
                .inflate(R.layout.layout_load_more, recycle, false)
        mAdapter!!.addFooterView(loadMoreView!!)
        loadMoreView!!.visibility = View.GONE
    }

    companion object {


        fun newInstance(aid: Int): VideoCommentFragment {

            val fragment = VideoCommentFragment()
            val bundle = Bundle()
            bundle.putInt(ConstantUtil.AID, aid)
            fragment.arguments = bundle
            return fragment
        }
    }
}