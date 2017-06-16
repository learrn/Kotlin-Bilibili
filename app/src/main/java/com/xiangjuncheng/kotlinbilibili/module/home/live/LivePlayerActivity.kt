package com.xiangjuncheng.kotlinbilibili.module.home.live

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.SurfaceHolder
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.xiangjuncheng.kotlinbilibili.R
import com.xiangjuncheng.kotlinbilibili.base.RxBaseActivity
import com.xiangjuncheng.kotlinbilibili.module.user.UserInfoDetailsActivity
import com.xiangjuncheng.kotlinbilibili.network.RetrofitHelper
import com.xiangjuncheng.kotlinbilibili.utils.ConstantUtil
import com.xiangjuncheng.kotlinbilibili.utils.LogUtil
import com.xiangjuncheng.kotlinbilibili.widget.CircleImageView
import kotlinx.android.synthetic.main.activity_live_details.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import tv.danmaku.ijk.media.player.IjkMediaPlayer
import java.io.IOException
import java.util.concurrent.TimeUnit


class LivePlayerActivity : RxBaseActivity() {

    private var ijkMediaPlayer: IjkMediaPlayer? = null

    private var holder: SurfaceHolder? = null

    private var flag = 0

    private var isPlay = false

    private var mAnimViewBackground: AnimationDrawable? = null

    private var cid: Int = 0

    private var title: String? = null

    private var online: Int = 0

    private var face: String? = null

    private var name: String? = null

    private var mid: Int = 0

    override fun getLayoutId(): Int = R.layout.activity_live_details

    override fun initViews(savedInstanceState: Bundle?) {
        if (intent != null) {
            cid = intent.getIntExtra(ConstantUtil.EXTRA_CID, 0)
            title = intent.getStringExtra(ConstantUtil.EXTRA_TITLE)
            online = intent.getIntExtra(ConstantUtil.EXTRA_ONLINE, 0)
            face = intent.getStringExtra(ConstantUtil.EXTRA_FACE)
            name = intent.getStringExtra(ConstantUtil.EXTRA_NAME)
            mid = intent.getIntExtra(ConstantUtil.EXTRA_MID, 0)
        }
        right_play.setOnClickListener { ControlVideo() }
        bottom_play.setOnClickListener { ControlVideo() }
        video_view.setOnClickListener {
            if (flag == 0) {
                startBottomShowAnim()
                flag = 1
            } else {
                startBottomHideAnim()
                flag = 0
            }
        }

        user_pic.setOnClickListener {
            UserInfoDetailsActivity.launch(this@LivePlayerActivity, name, mid, face)
            ControlVideo()
            right_play.visibility = View.VISIBLE
        }
        bottom_love.setOnClickListener { love_layout.addLove(); }
        initVideo()
        initUserInfo()
        startAnim()
    }

    /**
     * 设置用户信息
     */
    private fun initUserInfo() {
        Glide.with(this@LivePlayerActivity)
                .load(face)
                .centerCrop()
                .dontAnimate()
                .placeholder(R.drawable.ico_user_default)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(user_pic as CircleImageView)

        user_name.text = name
        live_num.text = online.toString()
    }

    private fun startAnim() {
        mAnimViewBackground = bili_anim.background as AnimationDrawable?
        mAnimViewBackground?.start()
    }

    private fun stopAnim() {
        mAnimViewBackground?.stop()
        bili_anim.visibility = View.GONE
        video_start_info.visibility = View.GONE
    }

    override fun initToolBar() {
        toolbar.title = title
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initVideo() {
        holder = video_view.holder
        ijkMediaPlayer = IjkMediaPlayer()
        getLiveUrl()
    }

    private fun getLiveUrl() {
        Observable.just("hello rx Android 3")
                .map({ t -> return@map t + " map 操作赋"; })
        RetrofitHelper.getLiveAPI()
                .getLiveUrl(cid)
                .compose(this.bindToLifecycle())
                .map { responseBody ->
                    {
                        try {
                            val str: String = responseBody.string()
                            str.substring(str.lastIndexOf("[") + 1, str.lastIndexOf("]") - 1)
                        } catch (e: IOException) {
                            e.printStackTrace()
                            null
                        }
                    }
                }
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap({ t ->
                    playVideo(t.toString())
                    return@flatMap Observable.timer(2000, TimeUnit.MILLISECONDS)
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    stopAnim()
                    isPlay = true
                    video_view.visibility = View.VISIBLE
                    right_play.setImageResource(R.drawable.ic_tv_stop)
                    bottom_play.setImageResource(R.drawable.ic_portrait_stop)
                }, { throwable ->
                    run {
                        LogUtil.all("直播地址url获取失败" + throwable.message)
                    }
                })
    }

    private fun playVideo(url: String) {
        try {
            ijkMediaPlayer?.setDataSource(this, Uri.parse(url))
            ijkMediaPlayer?.setDisplay(holder)
            holder?.addCallback(object : SurfaceHolder.Callback {
                override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
                    ijkMediaPlayer?.setDisplay(holder)
                }

                override fun surfaceDestroyed(holder: SurfaceHolder?) {
                }

                override fun surfaceCreated(holder: SurfaceHolder?) {
                }
            })
            ijkMediaPlayer?.prepareAsync()
            ijkMediaPlayer?.start()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        ijkMediaPlayer?.setKeepInBackground(false)
    }

    private fun startBottomShowAnim() {
        bottom_layout.visibility = View.VISIBLE
        right_play.visibility = View.VISIBLE
    }

    private fun startBottomHideAnim() {
        bottom_layout.visibility = View.GONE
        right_play.visibility = View.GONE
    }

    fun launch(activity: Activity, cid: Int, title: String, online: Int, face: String, name: String, mid: Int) {
        val mIntent = Intent(activity, LivePlayerActivity::class.java)
        mIntent.putExtra(ConstantUtil.EXTRA_CID, cid)
        mIntent.putExtra(ConstantUtil.EXTRA_TITLE, title)
        mIntent.putExtra(ConstantUtil.EXTRA_ONLINE, online)
        mIntent.putExtra(ConstantUtil.EXTRA_FACE, face)
        mIntent.putExtra(ConstantUtil.EXTRA_NAME, name)
        mIntent.putExtra(ConstantUtil.EXTRA_MID, mid)
        activity.startActivity(mIntent)
    }

    private fun ControlVideo() {

        if (isPlay) {
            ijkMediaPlayer?.pause()
            isPlay = false
            right_play.setImageResource(R.drawable.ic_tv_play)
            bottom_play.setImageResource(R.drawable.ic_portrait_play)
        } else {
            ijkMediaPlayer?.start()
            isPlay = true
            right_play.setImageResource(R.drawable.ic_tv_stop)
            bottom_play.setImageResource(R.drawable.ic_portrait_stop)
        }
    }


    override fun onDestroy() {

        super.onDestroy()
        ijkMediaPlayer?.release()
    }
}
