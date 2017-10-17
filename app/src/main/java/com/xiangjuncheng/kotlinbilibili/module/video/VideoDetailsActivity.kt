package com.xiangjuncheng.kotlinbilibili.module.video

import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.OvershootInterpolator
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.xiangjuncheng.kotlinbilibili.R
import com.xiangjuncheng.kotlinbilibili.base.RxBaseActivity
import com.xiangjuncheng.kotlinbilibili.entity.video.VideoDetailsInfo
import com.xiangjuncheng.kotlinbilibili.event.AppBarStateChangeEvent
import com.xiangjuncheng.kotlinbilibili.network.RetrofitHelper
import com.xiangjuncheng.kotlinbilibili.network.auxiliary.UrlHelper
import com.xiangjuncheng.kotlinbilibili.utils.ConstantUtil
import com.xiangjuncheng.kotlinbilibili.utils.DisplayUtil
import com.xiangjuncheng.kotlinbilibili.utils.SystemBarHelper
import kotlinx.android.synthetic.main.activity_video_details.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


class VideoDetailsActivity : RxBaseActivity() {
    companion object {
        fun launch(activity: Activity, aid: Int, imgUrl: String) {
            val intent = Intent(activity, VideoDetailsActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra(ConstantUtil.EXTRA_AV, aid)
            intent.putExtra(ConstantUtil.EXTRA_IMG_URL, imgUrl)
            activity.startActivity(intent)
        }
    }

    private val fragments = arrayListOf<Fragment>()
    private val titles = arrayListOf<String>()
    private var av: Int = 0
    private var imgUrl: String? = null
    private var mVideoDetailsInfo: VideoDetailsInfo.DataBean? = null
    override fun getLayoutId(): Int = R.layout.activity_video_details

    override fun initViews(savedInstanceState: Bundle?) {
        if (intent != null) {
            av = intent.getIntExtra(ConstantUtil.EXTRA_AV, -1)
            imgUrl = intent.getStringExtra(ConstantUtil.EXTRA_IMG_URL)
        }
        Glide.with(this@VideoDetailsActivity)
                .load(imgUrl?.let { UrlHelper.getClearVideoPreviewUrl(it) })
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.bili_default_image_tv)
                .dontAnimate()
                .into(video_preview)
        loadData()
        fab.isClickable = true
        fab.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.gray_20))
        fab.translationY = -resources.getDimension(R.dimen.floating_action_button_size_half)
        fab.setOnClickListener({ v ->
            VideoPlayerActivity.launch(this@VideoDetailsActivity,
                    mVideoDetailsInfo?.pages?.get(0)?.cid!!, mVideoDetailsInfo!!.title!!)
        })
        app_bar_layout.addOnOffsetChangedListener({ _, verticalOffset -> setViewsTranslation(verticalOffset) })
        app_bar_layout.addOnOffsetChangedListener(object : AppBarStateChangeEvent() {
            override fun onStateChanged(appBarLayout: AppBarLayout, state: State, verticalOffset: Int) {
                when (state) {
                    State.EXPANDED -> {
                        //展开状态
                        tv_player.visibility = View.GONE
                        tv_av.visibility = View.VISIBLE
                        toolbar.setContentInsetsRelative(DisplayUtil.dp2px(this@VideoDetailsActivity, 15F), 0)
                    }
                    State.COLLAPSED -> {
                        //折叠状态
                        tv_player.visibility = View.VISIBLE
                        tv_av.visibility = View.GONE
                        toolbar.setContentInsetsRelative(DisplayUtil.dp2px(this@VideoDetailsActivity, 150F), 0)
                    }
                    else -> {
                        tv_player.visibility = View.GONE
                        tv_av.visibility = View.VISIBLE
                        toolbar.setContentInsetsRelative(DisplayUtil.dp2px(this@VideoDetailsActivity, 15F), 0)
                    }
                }
            }

        })
    }

    override fun initToolBar() {
        toolbar.title = ""
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        collapsing_toolbar.setExpandedTitleColor(Color.TRANSPARENT)
        collapsing_toolbar.setCollapsedTitleTextColor(Color.WHITE)
        SystemBarHelper.immersiveStatusBar(this)
        SystemBarHelper.setHeightAndPadding(this, toolbar)
        tv_av.text = "av $av"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_video, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setViewsTranslation(target: Int) {
        fab.translationY = target.toFloat()
        if (target == 0) {
            showFAB()
        } else if (target < 0) {
            hideFAB()
        }
    }

    private fun showFAB() {

        fab.animate().scaleX(1f).scaleY(1f)
                .setInterpolator(OvershootInterpolator())
                .start()
        fab.isClickable = true
    }

    private fun hideFAB() {
        fab.animate().scaleX(0f).scaleY(0f)
                .setInterpolator(OvershootInterpolator())
                .start()
        fab.isClickable = true
    }

    override fun finishTask() {
        fab.isClickable = true
        fab.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.colorPrimary))
        collapsing_toolbar.title = ""
        if (TextUtils.isEmpty(imgUrl)) {
            Glide.with(this@VideoDetailsActivity)
                    .load<VideoDetailsInfo.DataBean>(mVideoDetailsInfo)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.bili_default_image_tv)
                    .dontAnimate()
                    .into(video_preview)
        }
        val mVideoIntroductionFragment = VideoIntroductionFragment.newInstance(av)
        val mVideoCommentFragment = VideoCommentFragment.newInstance(av)
        if (!mVideoIntroductionFragment.isAdded) {
            fragments.add(mVideoIntroductionFragment)
        }
        if (!mVideoCommentFragment.isAdded) {
            fragments.add(mVideoCommentFragment)
        }
        setPagerTitle(mVideoDetailsInfo?.stat?.reply.toString())
    }

    private fun setPagerTitle(num: String) {
        titles.add("简介")
        titles.add("评论($num)")

        view_pager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment = fragments[position]
            override fun getCount(): Int = fragments.size
            override fun getPageTitle(position: Int): CharSequence = titles[position]
        }
        view_pager.offscreenPageLimit = 2
        tab_layout.setViewPager(view_pager)
        measureTabLayoutTextWidth(0)
        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {
                measureTabLayoutTextWidth(position)
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    private fun measureTabLayoutTextWidth(position: Int) {
        val paint = tab_layout.getTitleView(position).paint
        val textWidth = paint.measureText(titles[position])
        tab_layout.indicatorWidth = textWidth / 3
    }

    override fun loadData() {
        RetrofitHelper.getBiliAppAPI()
                .getVideoDetails(av)
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ videoDetails ->

                    mVideoDetailsInfo = videoDetails.data
                    finishTask()
                }, {
                    fab.isClickable = false
                    fab.backgroundTintList = ColorStateList.valueOf(
                            resources.getColor(R.color.gray_20))
                })
    }
}
