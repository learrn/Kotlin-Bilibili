package com.xiangjuncheng.kotlinbilibili.media

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.media.AudioManager
import android.os.Build
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.view.*
import android.widget.*
import com.xiangjuncheng.kotlinbilibili.R
import com.xiangjuncheng.kotlinbilibili.media.callback.DanmukuSwitchListener
import com.xiangjuncheng.kotlinbilibili.media.callback.MediaPlayerListener
import com.xiangjuncheng.kotlinbilibili.media.callback.VideoBackListener
import com.xiangjuncheng.kotlinbilibili.utils.LogUtil
import java.util.*

/**
 * Created by xjc on 2017/10/15.
 */
class MediaController : FrameLayout {
    private var mPlayer: MediaPlayerListener? = null
    private var mContext: Context? = null
    private var mWindow: PopupWindow? = null
    private var mAnimStyle: Int = 0
    private var mAnchor: View? = null
    private var mRoot: View? = null
    private var mProgress: ProgressBar? = null
    private var mEndTime: TextView? = null
    private var mCurrentTime: TextView? = null
    private var mTitleView: TextView? = null
    private var mInfoView: OutlineTextView? = null
    private var mTitle: String? = null
    private var mDuration: Long = 0
    var isShowing: Boolean = false
        private set
    private var mDragging: Boolean = false
    private var mInstantSeeking = true
    private var mFromXml = false
    private var mPauseButton: ImageButton? = null
    private var mAM: AudioManager? = null
    private var mShownListener: OnShownListener? = null
    private var mHiddenListener: OnHiddenListener? = null
    private var mDanmakuShow = false
    private var mDanmukuSwitchListener: DanmukuSwitchListener? = null
    private var mBack: ImageView? = null
    private var mVideoBackListener: VideoBackListener? = null
    private var mTvPlay: ImageView? = null

    fun setDanmakuSwitchListener(danmukuSwitchListener: DanmukuSwitchListener) {
        this.mDanmukuSwitchListener = danmukuSwitchListener
    }

    fun setVideoBackEvent(videoBackListener: VideoBackListener) {
        this.mVideoBackListener = videoBackListener
    }

    @SuppressLint("HandlerLeak")
    private val mHandler = object : Handler() {

        override fun handleMessage(msg: Message) {
            var msg = msg
            val pos: Long
            when (msg.what) {
                FADE_OUT -> hide()
                SHOW_PROGRESS -> {
                    pos = setProgress()
                    if (!mDragging && isShowing) {
                        msg = obtainMessage(SHOW_PROGRESS)
                        sendMessageDelayed(msg, 1000 - pos % 1000)
                        updatePausePlay()
                    }
                }
            }
        }
    }

    private var mPauseListener = OnClickListener{
        doPauseResume()
        show(sDefaultTimeout)
    }

    private var lastRunnable: Runnable? = null
    private val mSeekListener = object : SeekBar.OnSeekBarChangeListener {
        override fun onStartTrackingTouch(bar: SeekBar) {
            mDragging = true
            show(3600000)
            mHandler.removeMessages(SHOW_PROGRESS)
            if (mInstantSeeking) {
                mAM!!.setStreamMute(AudioManager.STREAM_MUSIC, true)
            }
            if (mInfoView != null) {
                mInfoView!!.setText("")
                mInfoView!!.visibility = View.VISIBLE
            }
        }


        override fun onProgressChanged(bar: SeekBar, progress: Int, fromuser: Boolean) {
            if (!fromuser) {
                return
            }
            val newposition = mDuration * progress / 1000
            val time = generateTime(newposition)
            if (mInstantSeeking) {
                mHandler.removeCallbacks(lastRunnable)
                lastRunnable = Runnable { mPlayer?.seekTo(newposition) }
                mHandler.postDelayed(lastRunnable, 200)
            }
            if (mInfoView != null) {
                mInfoView!!.setText(time)
            }
            if (mCurrentTime != null) {
                mCurrentTime!!.text = time
            }
        }


        override fun onStopTrackingTouch(bar: SeekBar) {
            if (!mInstantSeeking) {
                mPlayer!!.seekTo(mDuration * bar.progress / 1000)
            }
            if (mInfoView != null) {
                mInfoView!!.setText("")
                mInfoView!!.visibility = View.GONE
            }
            show(sDefaultTimeout)
            mHandler.removeMessages(SHOW_PROGRESS)
            mAM!!.setStreamMute(AudioManager.STREAM_MUSIC, false)
            mDragging = false
            mHandler.sendEmptyMessageDelayed(SHOW_PROGRESS, 1000)
        }
    }


    constructor(context: Context, attrs: AttributeSet, mPauseListener: OnClickListener) : super(context, attrs) {
        mRoot = this
        mFromXml = true
        initController(context)
        this.mPauseListener = mPauseListener
    }

    constructor(context: Context) : super(context) {
        if (!mFromXml && initController(context)) {
            initFloatingWindow()
        }
    }

    private fun initController(context: Context): Boolean {
        mContext = context
        mAM = mContext!!.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        return true
    }


    public override fun onFinishInflate() {
        super.onFinishInflate()
        if (mRoot != null) {
            initControllerView(mRoot!!)
        }
    }


    private fun initFloatingWindow() {
        mWindow = PopupWindow(mContext)
        mWindow!!.isFocusable = false
        mWindow!!.setBackgroundDrawable(null)
        mWindow!!.isOutsideTouchable = true
        mAnimStyle = android.R.style.Animation
    }


    /**
     * 设置VideoView
     */
    fun setAnchorView(view: View) {
        mAnchor = view
        if (!mFromXml) {
            removeAllViews()
            mRoot = makeControllerView()
            mWindow!!.contentView = mRoot
            mWindow!!.width = FrameLayout.LayoutParams.MATCH_PARENT
            mWindow!!.height = FrameLayout.LayoutParams.WRAP_CONTENT
        }
        initControllerView(mRoot!!)
    }


    /**
     * 创建视图包含小部件,控制回放
     */
    protected fun makeControllerView(): View {
        return (mContext!!
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
                R.layout.layout_media_controller, this)
    }


    private fun initControllerView(v: View) {
        mPauseButton = v.findViewById(R.id.media_controller_play_pause) as ImageButton
        mTvPlay = v.findViewById(R.id.media_controller_tv_play) as ImageView
        if (mPauseButton != null && mTvPlay != null) {
            mPauseButton!!.requestFocus()
            mPauseButton!!.setOnClickListener(mPauseListener)
            mTvPlay!!.requestFocus()
            mTvPlay!!.setOnClickListener { v13 ->
                doPauseResume()
                show(sDefaultTimeout)
            }
        }
        mProgress = v.findViewById(R.id.media_controller_seekbar) as SeekBar
        if (mProgress != null) {
            if (mProgress is SeekBar) {
                val seeker = mProgress as SeekBar?
                seeker!!.setOnSeekBarChangeListener(mSeekListener)
                seeker.thumbOffset = 1
            }
            mProgress!!.max = 1000
        }
        mEndTime = v.findViewById(R.id.media_controller_time_total) as TextView
        mCurrentTime = v
                .findViewById(R.id.media_controller_time_current) as TextView
        mTitleView = v.findViewById(R.id.media_controller_title) as TextView
        if (mTitleView != null) {
            mTitleView!!.text = mTitle
        }
        val mDanmakuLayout = v.findViewById(
                R.id.media_controller_danmaku_layout) as LinearLayout
        val mDanmakuImage = v.findViewById(R.id.media_controller_danmaku_switch) as ImageView
        val mDanmakuText = v.findViewById(R.id.media_controller_danmaku_text) as TextView
        mDanmakuLayout.setOnClickListener { v1 ->
            if (mDanmakuShow) {
                mDanmakuImage.setImageResource(R.drawable.bili_player_danmaku_is_open)
                mDanmakuText.text = "弹幕开"
                mDanmukuSwitchListener!!.setDanmakuShow(true)
                mDanmakuShow = false
            } else {
                mDanmakuImage.setImageResource(R.drawable.bili_player_danmaku_is_closed)
                mDanmakuText.text = "弹幕关"
                mDanmukuSwitchListener!!.setDanmakuShow(false)
                mDanmakuShow = true
            }
        }
        mBack = v.findViewById(R.id.media_controller_back) as ImageView
        mBack!!.setOnClickListener { v12 -> mVideoBackListener!!.back() }
    }


    fun setMediaPlayer(player: MediaPlayerListener) {
        mPlayer = player
        updatePausePlay()
    }


    /**
     * 拖动seekBar时回调
     */
    fun setInstantSeeking(seekWhenDragging: Boolean) {
        mInstantSeeking = seekWhenDragging
    }


    fun show() {
        show(sDefaultTimeout)
    }


    /**
     * 设置播放的文件名称
     */
    fun setTitle(name: String) {
        mTitle = name
        if (mTitleView != null) {
            mTitleView!!.text = mTitle
        }
    }


    /**
     * 设置MediaController持有的View
     */
    fun setInfoView(v: OutlineTextView) {
        mInfoView = v
    }


    private fun disableUnsupportedButtons() {
        if (mPauseButton != null && mTvPlay != null && !mPlayer!!.canPause()) {
            mPauseButton!!.isEnabled = false
            mTvPlay!!.isEnabled = false
        }
    }


    /**
     * 改变控制器的动画风格的资源
     */
    fun setAnimationStyle(animationStyle: Int) {
        mAnimStyle = animationStyle
    }


    /**
     * 在屏幕上显示控制器
     */
    @SuppressLint("InlinedApi")
    fun show(timeout: Int) {
        if (!isShowing && mAnchor != null && mAnchor!!.windowToken != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                mAnchor!!.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
            }
            if (mPauseButton != null && mTvPlay != null) {
                mPauseButton!!.requestFocus()
                mTvPlay!!.requestFocus()
            }
            disableUnsupportedButtons()
            if (mFromXml) {
                visibility = View.VISIBLE
            } else {
                val location = IntArray(2)
                mAnchor!!.getLocationOnScreen(location)
                val anchorRect = Rect(location[0], location[1],
                        location[0] + mAnchor!!.width, location[1] + mAnchor!!.height)
                mWindow!!.animationStyle = mAnimStyle
                mWindow!!.showAtLocation(mAnchor, Gravity.BOTTOM,
                        anchorRect.left, 0)
            }
            isShowing = true
            if (mShownListener != null) {
                mShownListener!!.onShown()
            }
        }
        updatePausePlay()
        mHandler.sendEmptyMessage(SHOW_PROGRESS)
        if (timeout != 0) {
            mHandler.removeMessages(FADE_OUT)
            mHandler.sendMessageDelayed(mHandler.obtainMessage(FADE_OUT),
                    timeout.toLong())
        }
    }


    @SuppressLint("InlinedApi")
    fun hide() {
        if (mAnchor == null) {
            return
        }
        if (isShowing) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                mAnchor!!.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            }
            try {
                mHandler.removeMessages(SHOW_PROGRESS)
                if (mFromXml) {
                    visibility = View.GONE
                } else {
                    mWindow!!.dismiss()
                }
            } catch (ex: IllegalArgumentException) {
                LogUtil.all("MediaController already removed")
            }

            isShowing = false
            if (mHiddenListener != null) {
                mHiddenListener!!.onHidden()
            }
        }
    }

    fun setOnShownListener(l: OnShownListener) {
        mShownListener = l
    }


    fun setOnHiddenListener(l: OnHiddenListener) {
        mHiddenListener = l
    }


    private fun setProgress(): Long {
        if (mPlayer == null || mDragging) {
            return 0
        }
        val position = mPlayer!!.currentPosition
        val duration = mPlayer!!.duration
        if (mProgress != null) {
            if (duration > 0) {
                val pos = 1000L * position / duration
                mProgress!!.progress = pos.toInt()
            }
            val percent = mPlayer!!.bufferPercentage
            mProgress!!.secondaryProgress = percent * 10
        }
        mDuration = duration.toLong()
        if (mEndTime != null) {
            mEndTime!!.text = generateTime(mDuration)
        }
        if (mCurrentTime != null) {
            mCurrentTime!!.text = generateTime(position.toLong())
        }
        return position.toLong()
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        show(sDefaultTimeout)
        return true
    }


    override fun onTrackballEvent(ev: MotionEvent): Boolean {
        show(sDefaultTimeout)
        return false
    }


    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        val keyCode = event.keyCode
        if (event.repeatCount == 0 && (keyCode == KeyEvent.KEYCODE_HEADSETHOOK
                || keyCode == KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE || keyCode == KeyEvent.KEYCODE_SPACE)) {
            doPauseResume()
            show(sDefaultTimeout)
            if (mPauseButton != null && mTvPlay != null) {
                mPauseButton!!.requestFocus()
                mTvPlay!!.requestFocus()
            }
            return true
        } else if (keyCode == KeyEvent.KEYCODE_MEDIA_STOP) {
            if (mPlayer!!.isPlaying) {
                mPlayer!!.pause()
                updatePausePlay()
            }
            return true
        } else if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_MENU) {
            hide()
            return true
        } else {
            show(sDefaultTimeout)
        }
        return super.dispatchKeyEvent(event)
    }


    private fun updatePausePlay() {
        if (mRoot == null || mPauseButton == null || mTvPlay == null) {
            return
        }
        if (mPlayer!!.isPlaying) {
            mPauseButton!!.setImageResource(R.drawable.bili_player_play_can_pause)
            mTvPlay!!.setImageResource(R.drawable.ic_tv_stop)
        } else {
            mPauseButton!!.setImageResource(R.drawable.bili_player_play_can_play)
            mTvPlay!!.setImageResource(R.drawable.ic_tv_play)
        }
    }


    private fun doPauseResume() {
        if (mPlayer!!.isPlaying) {
            mPlayer!!.pause()
        } else {
            mPlayer!!.start()
        }
        updatePausePlay()
    }


    override fun setEnabled(enabled: Boolean) {
        if (mPauseButton != null) {
            mPauseButton!!.isEnabled = enabled
        }
        if (mTvPlay != null) {
            mTvPlay!!.isEnabled = enabled
        }
        if (mProgress != null) {
            mProgress!!.isEnabled = enabled
        }
        disableUnsupportedButtons()
        super.setEnabled(enabled)
    }


    interface OnShownListener {
        fun onShown()
    }

    interface OnHiddenListener {
        fun onHidden()
    }

    companion object {
        private val sDefaultTimeout = 3000
        private val FADE_OUT = 1
        private val SHOW_PROGRESS = 2


        private fun generateTime(position: Long): String {
            val totalSeconds = (position / 1000.0 + 0.5).toInt()
            val seconds = totalSeconds % 60
            val minutes = totalSeconds / 60 % 60
            val hours = totalSeconds / 3600
            if (hours > 0) {
                return String.format(Locale.US, "%02d:%02d:%02d", hours, minutes,
                        seconds)
            } else {
                return String.format(Locale.US, "%02d:%02d", minutes, seconds)
            }
        }
    }
}