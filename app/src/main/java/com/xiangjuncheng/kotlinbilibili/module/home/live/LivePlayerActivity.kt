package com.xiangjuncheng.kotlinbilibili.module.home.live

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.SurfaceHolder
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.xiangjuncheng.kotlinbilibili.R
import com.xiangjuncheng.kotlinbilibili.base.RxBaseActivity
import com.xiangjuncheng.kotlinbilibili.utils.ConstantUtil
import com.xiangjuncheng.kotlinbilibili.widget.CircleImageView
import kotlinx.android.synthetic.main.activity_live_details.*
import kotlinx.android.synthetic.main.layout_toolbar.*


class LivePlayerActivity : RxBaseActivity() {

    private val ijkMediaPlayer: IjkMediaPlayer? = null

    private val holder: SurfaceHolder? = null

    private val flag = 0

    private val isPlay = false

    private var mAnimViewBackground: AnimationDrawable? = null

    private var cid: Int = 0

    private var title: String? = null

    private var online: Int = 0

    private var face: String? = null

    private var name: String? = null

    private var mid: Int = 0

    override fun getLayoutId(): Int = R.layout.activity_live_details

    override fun initViews(savedInstanceState: Bundle?) {
        if (intent != null){
            cid = intent.getIntExtra(ConstantUtil.EXTRA_CID, 0)
            title = intent.getStringExtra(ConstantUtil.EXTRA_TITLE)
            online = intent.getIntExtra(ConstantUtil.EXTRA_ONLINE, 0)
            face = intent.getStringExtra(ConstantUtil.EXTRA_FACE)
            name = intent.getStringExtra(ConstantUtil.EXTRA_NAME)
            mid = intent.getIntExtra(ConstantUtil.EXTRA_MID, 0)
        }
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

    private fun startAnim(){
        mAnimViewBackground = bili_anim.background as AnimationDrawable?
        mAnimViewBackground?.start()
    }

    private fun stopAnim(){
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

    override fun onDestroy() {

        super.onDestroy()
        ijkMediaPlayer.release()
    }
}
