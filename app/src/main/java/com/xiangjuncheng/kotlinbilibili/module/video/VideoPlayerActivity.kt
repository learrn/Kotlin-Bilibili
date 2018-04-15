package com.xiangjuncheng.kotlinbilibili.module.video

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView

import com.xiangjuncheng.kotlinbilibili.R
import com.xiangjuncheng.kotlinbilibili.base.RxBaseActivity
import com.xiangjuncheng.kotlinbilibili.media.MediaController
import com.xiangjuncheng.kotlinbilibili.media.VideoPlayerView
import com.xiangjuncheng.kotlinbilibili.media.callback.DanmukuSwitchListener
import com.xiangjuncheng.kotlinbilibili.media.callback.VideoBackListener
import com.xiangjuncheng.kotlinbilibili.media.damuku.BiliDanmukuDownloadUtil
import com.xiangjuncheng.kotlinbilibili.network.RetrofitHelper
import com.xiangjuncheng.kotlinbilibili.utils.ConstantUtil
import kotlinx.android.synthetic.main.activity_video_player.*
import master.flame.danmaku.controller.DrawHandler
import master.flame.danmaku.controller.IDanmakuView
import master.flame.danmaku.danmaku.model.BaseDanmaku
import master.flame.danmaku.danmaku.model.DanmakuTimer
import master.flame.danmaku.danmaku.model.IDisplayer
import master.flame.danmaku.danmaku.model.android.DanmakuContext
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Func1
import rx.schedulers.Schedulers
import tv.danmaku.ijk.media.player.IMediaPlayer
import java.util.HashMap

open class VideoPlayerActivity : RxBaseActivity(), DanmukuSwitchListener, VideoBackListener {
    override fun getLayoutId(): Int = R.layout.activity_video_player

    override fun initViews(savedInstanceState: Bundle?) {
        mDanmakuView = findViewById(R.id.sv_danmaku) as IDanmakuView
        val intent = intent
        if (intent != null) {
            cid = intent.getIntExtra(ConstantUtil.EXTRA_CID, 0)
            title = intent.getStringExtra(ConstantUtil.EXTRA_TITLE)
            aid = intent.getIntExtra(ConstantUtil.EXTRA_AV, 0)
        }
        initAnimation()
        initMediaPlayer()
    }

    private var mDanmakuView: IDanmakuView? = null
    private var cid: Int = 0
    private var title: String? = null
    private var aid: Int = 0
    private var LastPosition = 0
    private var startText = "初始化播放器..."
    private var mLoadingAnim: AnimationDrawable? = null
    private var danmakuContext: DanmakuContext? = null


    @SuppressLint("UseSparseArrays")
    private fun initMediaPlayer() {
        //配置播放器
        val mMediaController = MediaController(this)
        mMediaController.setTitle(title!!)
        playerView!!.setMediaController(mMediaController)
        playerView!!.setMediaBufferingIndicator(buffering_indicator)
        playerView!!.requestFocus()
        playerView!!.setOnInfoListener(onInfoListener)
        playerView!!.setOnSeekCompleteListener(onSeekCompleteListener)
        playerView!!.setOnCompletionListener(onCompletionListener)
        playerView!!.setOnControllerEventsListener(onControllerEventsListener)
        //设置弹幕开关监听
        mMediaController.setDanmakuSwitchListener(this)
        //设置返回键监听
        mMediaController.setVideoBackEvent(this)
        //配置弹幕库
        mDanmakuView!!.enableDanmakuDrawingCache(true)
        //设置最大显示行数
        val maxLinesPair = HashMap<Int, Int>()
        //滚动弹幕最大显示5行
        maxLinesPair.put(BaseDanmaku.TYPE_SCROLL_RL, 5)
        //设置是否禁止重叠
        val overlappingEnablePair = HashMap<Int, Boolean>()
        overlappingEnablePair.put(BaseDanmaku.TYPE_SCROLL_RL, true)
        overlappingEnablePair.put(BaseDanmaku.TYPE_FIX_TOP, true)
        //设置弹幕样式
        danmakuContext = DanmakuContext.create()
        danmakuContext!!.setDanmakuStyle(IDisplayer.DANMAKU_STYLE_STROKEN, 3F)
                .setDuplicateMergingEnabled(false)
                .setScrollSpeedFactor(1.2f)
                .setScaleTextSize(0.8f)
                .setMaximumLines(maxLinesPair)
                .preventOverlapping(overlappingEnablePair)
        loadData()
    }


    /**
     * 初始化加载动画
     */
    private fun initAnimation() {
        video_start!!.visibility = View.VISIBLE
        startText = "$startText【完成】\n解析视频地址...【完成】\n全舰弹幕填装..."
        video_start_info!!.text = startText
        mLoadingAnim = bili_anim!!.background as AnimationDrawable
        mLoadingAnim!!.start()
    }


    override fun initToolBar() {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.setBackgroundDrawable(null)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }


    /**
     * 获取视频数据以及解析弹幕
     */
    override fun loadData() {
        RetrofitHelper.getBiliAppAPI()
                .getVideoDetails(aid)
                //.getHDVideoUrl(cid, 4, ConstantUtil.VIDEO_TYPE_MP4)
                //.compose(bindToLifecycle())
//                .map({ videoInfo -> Uri.parse(videoInfo.durl?.get(0)?.url) })
                .map({ videoInfo ->
                    print(videoInfo)
                    Uri.parse("http://flashmedia.eastday.com/newdate/news/2016-11/shznews1125-19.mp4")
                })
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap({ uri ->
                    playerView!!.setVideoURI(uri)
                    playerView!!.setOnPreparedListener(IMediaPlayer.OnPreparedListener { mp ->
                        mLoadingAnim!!.stop()
                        startText = "$startText【完成】\n视频缓冲中..."
                        video_start_info!!.text = startText
                        video_start!!.visibility = View.GONE
                    })
                    val url = "http://comment.bilibili.com/$cid.xml"
                    BiliDanmukuDownloadUtil.downloadXML(url)
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ baseDanmakuParser ->
                    mDanmakuView!!.prepare(baseDanmakuParser, danmakuContext)
                    mDanmakuView!!.showFPS(false)
                    mDanmakuView!!.enableDanmakuDrawingCache(false)
                    mDanmakuView!!.setCallback(object : DrawHandler.Callback {
                        override fun prepared() {
                            mDanmakuView!!.start()
                        }

                        override fun updateTimer(danmakuTimer: DanmakuTimer) {}
                        override fun danmakuShown(danmaku: BaseDanmaku) {}
                        override fun drawingFinished() {}
                    })
                    playerView!!.start()
                }, { throwable ->
                    startText = "$startText【失败】\n视频缓冲中..."
                    video_start_info!!.text = startText
                    startText = startText + "【失败】\n" + throwable.message
                    video_start_info!!.text = startText
                })
    }


    /**
     * 视频缓冲事件回调
     */
    private val onInfoListener = IMediaPlayer.OnInfoListener { mp, what, extra ->
        if (what == IMediaPlayer.MEDIA_INFO_BUFFERING_START) {
            if (mDanmakuView != null && mDanmakuView!!.isPrepared) {
                mDanmakuView!!.pause()
                if (buffering_indicator != null) {
                    buffering_indicator!!.visibility = View.VISIBLE
                }
            }
        } else if (what == IMediaPlayer.MEDIA_INFO_BUFFERING_END) {
            if (mDanmakuView != null && mDanmakuView!!.isPaused) {
                mDanmakuView!!.resume()
            }
            if (buffering_indicator != null) {
                buffering_indicator!!.visibility = View.GONE
            }
        }
        true
    }

    /**
     * 视频跳转事件回调
     */
    private val onSeekCompleteListener = IMediaPlayer.OnSeekCompleteListener { mp ->
        if (mDanmakuView != null && mDanmakuView!!.isPrepared) {
            mDanmakuView!!.seekTo(mp.currentPosition)
        }
    }

    /**
     * 视频播放完成事件回调
     */
    private val onCompletionListener = IMediaPlayer.OnCompletionListener {
        if (mDanmakuView != null && mDanmakuView!!.isPrepared) {
            mDanmakuView!!.seekTo(0.toLong())
            mDanmakuView!!.pause()
        }
        playerView!!.pause()
    }

    /**
     * 控制条控制状态事件回调
     */
    private val onControllerEventsListener = object : VideoPlayerView.OnControllerEventsListener {
        override fun onVideoPause() {
            if (mDanmakuView != null && mDanmakuView!!.isPrepared) {
                mDanmakuView!!.pause()
            }
        }


        override fun OnVideoResume() {
            if (mDanmakuView != null && mDanmakuView!!.isPaused) {
                mDanmakuView!!.resume()
            }
        }
    }


    override fun onResume() {
        super.onResume()
        if (mDanmakuView != null && mDanmakuView!!.isPrepared && mDanmakuView!!.isPaused) {
            mDanmakuView!!.seekTo(LastPosition.toLong())
        }
        if (playerView != null && !playerView!!.isPlaying) {
            playerView!!.seekTo(LastPosition.toLong())
        }
    }


    override fun onPause() {
        super.onPause()
        if (playerView != null) {
            LastPosition = playerView!!.currentPosition
            playerView!!.pause()
        }
        if (mDanmakuView != null && mDanmakuView!!.isPrepared) {
            mDanmakuView!!.pause()
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        if (mDanmakuView != null) {
            mDanmakuView!!.release()
            mDanmakuView = null
        }
        if (mLoadingAnim != null) {
            mLoadingAnim!!.stop()
            mLoadingAnim = null
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        if (playerView != null && playerView!!.isDrawingCacheEnabled) {
            playerView!!.destroyDrawingCache()
        }
        if (mDanmakuView != null && mDanmakuView!!.isPaused) {
            mDanmakuView!!.release()
            mDanmakuView = null
        }
        if (mLoadingAnim != null) {
            mLoadingAnim!!.stop()
            mLoadingAnim = null
        }
    }


    /**
     * 弹幕开关回调
     */
    override fun setDanmakuShow(isShow: Boolean) {
        if (mDanmakuView != null) {
            if (isShow) {
                mDanmakuView!!.show()
            } else {
                mDanmakuView!!.hide()
            }
        }
    }


    /**
     * 退出界面回调
     */
    override fun back() {
        onBackPressed()
    }

    companion object {


        fun launch(activity: Activity, cid: Int, title: String, av: Int) {
            val mIntent = Intent(activity, VideoPlayerActivity::class.java)
            mIntent.putExtra(ConstantUtil.EXTRA_CID, cid)
            mIntent.putExtra(ConstantUtil.EXTRA_TITLE, title)
            mIntent.putExtra(ConstantUtil.EXTRA_AV, av)
            activity.startActivity(mIntent)
        }
    }
}
