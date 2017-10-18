package com.xiangjuncheng.kotlinbilibili.module.video

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView

import com.xiangjuncheng.kotlinbilibili.R
import com.xiangjuncheng.kotlinbilibili.adapter.VideoRelatedAdapter
import com.xiangjuncheng.kotlinbilibili.adapter.helper.AbsRecyclerViewAdapter
import com.xiangjuncheng.kotlinbilibili.base.RxLazyFragment
import com.xiangjuncheng.kotlinbilibili.entity.video.VideoDetailsInfo
import com.xiangjuncheng.kotlinbilibili.network.RetrofitHelper
import com.xiangjuncheng.kotlinbilibili.utils.ConstantUtil
import com.xiangjuncheng.kotlinbilibili.utils.NumberUtil
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.fragment_video_introduction.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class VideoIntroductionFragment : RxLazyFragment() {
    companion object {
        fun newInstance(aid: Int): VideoIntroductionFragment {
            val fragment = VideoIntroductionFragment()
            val bundle = Bundle()
            bundle.putInt(ConstantUtil.EXTRA_AV, aid)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var av: Int = 0
    private lateinit var mVideoDetailsInfo: VideoDetailsInfo.DataBean
    override fun getLayoutResId(): Int = R.layout.fragment_video_introduction
    override fun finishCreateView(state: Bundle?) {
        av = arguments.getInt(ConstantUtil.EXTRA_AV)
        loadData()
        btn_share.setOnClickListener {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_SUBJECT, "分享")
                intent.putExtra(Intent.EXTRA_TEXT, "来自「哔哩哔哩」的分享:" + mVideoDetailsInfo.desc)
                startActivity(Intent.createChooser(intent, mVideoDetailsInfo.title))
        }
    }

    override fun loadData() {
        RetrofitHelper.getBiliAppAPI()
                .getVideoDetails(av)
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ videoDetails ->
                    mVideoDetailsInfo = videoDetails.data!!
                    finishTask()
                }, { _ -> })
    }

    override fun finishTask() {
        tv_title.text = mVideoDetailsInfo.title
        tv_play_time.text = mVideoDetailsInfo.stat?.view?.let { NumberUtil.converString(it) }
        tv_review_count.text = mVideoDetailsInfo.stat?.danmaku?.let { NumberUtil.converString(it) }
        tv_description.text = mVideoDetailsInfo.desc
        share_num.text = mVideoDetailsInfo.stat?.share?.let { NumberUtil.converString(it) }
        fav_num.text = mVideoDetailsInfo.stat?.favorite?.let { NumberUtil.converString(it) }
        coin_num.text = mVideoDetailsInfo.stat?.coin?.let { NumberUtil.converString(it) }
        RetrofitHelper.getAccountAPI()
                .getUserInfoById(mVideoDetailsInfo.owner?.mid!!)
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {userInfo ->
                    author_tag.setUpWithInfo(activity,mVideoDetailsInfo.owner?.name!!,mVideoDetailsInfo.owner?.mid!!, mVideoDetailsInfo.owner?.face!!,userInfo.card?.fans!!)
                }
        setVideoTags()
        setVideoRelated()
    }

    private fun setVideoRelated() {

        val relates = mVideoDetailsInfo.relates
        if (relates == null) {
            layout_video_related.visibility = View.GONE
            return
        }
        val mVideoRelatedAdapter = VideoRelatedAdapter(recycle, relates)
        recycle.setHasFixedSize(false)
        recycle.isNestedScrollingEnabled = false
        recycle.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, true)
        recycle.adapter = mVideoRelatedAdapter
        mVideoRelatedAdapter.setOnItemClickListener(object :AbsRecyclerViewAdapter.OnItemClickListener{
            override fun onItemClick(position: Int, holder: AbsRecyclerViewAdapter.ClickableViewHolder) {
                relates[position].pic?.let {
                    VideoDetailsActivity.launch(activity,
                            relates[position].aid, it)
                }
            }
        })
    }

    private fun setVideoTags() {

        val tags = mVideoDetailsInfo.tag
        tags_layout.setAdapter(object : TagAdapter<VideoDetailsInfo.DataBean.TagBean>(tags) {
            override fun getView(parent: FlowLayout?, position: Int, t: VideoDetailsInfo.DataBean.TagBean?): View {
                val mTags = LayoutInflater.from(activity)
                        .inflate(R.layout.layout_tags_item, parent, false) as TextView
                mTags.text = t?.tag_name
                return mTags
            }
        })
    }

}
