package com.xiangjuncheng.kotlinbilibili.media

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.net.Uri
import android.util.AttributeSet
import android.view.*
import com.xiangjuncheng.kotlinbilibili.R
import com.xiangjuncheng.kotlinbilibili.media.callback.MediaPlayerListener
import tv.danmaku.ijk.media.player.IMediaPlayer
import tv.danmaku.ijk.media.player.IjkMediaPlayer
import tv.danmaku.ijk.media.player.pragma.DebugLog
import java.io.IOException

/**
 * Created by xjc on 2017/10/15.
 */
class VideoPlayerView : SurfaceView, MediaPlayerListener {
    private var mUri: Uri? = null
    private var mDuration: Long = 0
    private var mUserAgent: String? = null
    private var mCurrentState = STATE_IDLE
    private var mTargetState = STATE_IDLE
    private var mVideoLayout = VIDEO_LAYOUT_SCALE
    private var mSurfaceHolder: SurfaceHolder? = null
    private var mMediaPlayer: IMediaPlayer? = null
    var videoWidth: Int = 0
        private set
    var videoHeight: Int = 0
        private set
    private var mVideoSarNum: Int = 0
    private var mVideoSarDen: Int = 0
    private var mSurfaceWidth: Int = 0
    private var mSurfaceHeight: Int = 0
    private var mMediaController: MediaController? = null
    private var mMediaBufferingIndicator: View? = null
    private var mOnCompletionListener: IMediaPlayer.OnCompletionListener? = null
    private var mOnPreparedListener: IMediaPlayer.OnPreparedListener? = null
    private var mOnErrorListener: IMediaPlayer.OnErrorListener? = null
    private var mOnSeekCompleteListener: IMediaPlayer.OnSeekCompleteListener? = null
    private var mOnInfoListener: IMediaPlayer.OnInfoListener? = null
    private var mOnBufferingUpdateListener: IMediaPlayer.OnBufferingUpdateListener? = null
    private var mOnControllerEventsListener: OnControllerEventsListener? = null
    private var mCurrentBufferPercentage: Int = 0
    private var mSeekWhenPrepared: Long = 0
    private val mCanPause = true
    private val mCanSeekBack = true
    private val mCanSeekForward = true
    private var mContext: Context? = null

    private var mSizeChangedListener: IMediaPlayer.OnVideoSizeChangedListener = IMediaPlayer.OnVideoSizeChangedListener { mp, width, height, sarNum, sarDen ->
        DebugLog.dfmt(TAG, "onVideoSizeChanged: (%dx%d)", width, height)
        videoWidth = mp.videoWidth
        videoHeight = mp.videoHeight
        mVideoSarNum = sarNum
        mVideoSarDen = sarDen
        if (videoWidth != 0 && videoHeight != 0) {
            setVideoLayout(mVideoLayout)
        }
    }

    private var mPreparedListener: IMediaPlayer.OnPreparedListener = IMediaPlayer.OnPreparedListener { mp ->
        DebugLog.d(TAG, "onPrepared")
        mCurrentState = STATE_PREPARED
        mTargetState = STATE_PLAYING
        if (mOnPreparedListener != null) {
            mOnPreparedListener!!.onPrepared(mMediaPlayer)
        }
        if (mMediaController != null) {
            mMediaController!!.isEnabled = true
        }
        videoWidth = mp.videoWidth
        videoHeight = mp.videoHeight
        val seekToPosition = mSeekWhenPrepared
        if (seekToPosition != 0L) {
            seekTo(seekToPosition)
        }
        if (videoWidth != 0 && videoHeight != 0) {
            setVideoLayout(mVideoLayout)
            if (mSurfaceWidth == videoWidth && mSurfaceHeight == videoHeight) {
                if (mTargetState == STATE_PLAYING) {
                    start()
                    if (mMediaController != null) {
                        mMediaController!!.show()
                    }
                } else if (!isPlaying && (seekToPosition != 0L || currentPosition > 0)) {
                    if (mMediaController != null) {
                        mMediaController!!.show(0)
                    }
                }
            }
        } else if (mTargetState == STATE_PLAYING) {
            start()
        }
    }

    private val mCompletionListener = IMediaPlayer.OnCompletionListener {
        DebugLog.d(TAG, "onCompletion")
        mCurrentState = STATE_PLAYBACK_COMPLETED
        mTargetState = STATE_PLAYBACK_COMPLETED
        if (mMediaController != null) {
            mMediaController!!.hide()
        }
        if (mOnCompletionListener != null) {
            mOnCompletionListener!!.onCompletion(mMediaPlayer)
        }
    }

    private val mErrorListener = IMediaPlayer.OnErrorListener { mp, framework_err, impl_err ->
        DebugLog.dfmt(TAG, "Error: %d, %d", framework_err, impl_err)
        mCurrentState = STATE_ERROR
        mTargetState = STATE_ERROR
        if (mMediaController != null) {
            mMediaController!!.hide()
        }
        if (mOnErrorListener != null) {
            if (mOnErrorListener!!.onError(mMediaPlayer, framework_err,
                    impl_err)) {
                return@OnErrorListener true
            }
        }
        if (windowToken != null) {
            val message = if (framework_err == IMediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK)
                R.string.video_error_text_invalid_progressive_playback
            else
                R.string.video_error_text_unknown
            AlertDialog.Builder(mContext)
                    .setTitle(R.string.video_error_title)
                    .setMessage(message)
                    .setPositiveButton(
                            R.string.video_error_button,
                            { dialog, whichButton ->

                                if (mOnCompletionListener != null) {
                                    mOnCompletionListener!!
                                            .onCompletion(mMediaPlayer)
                                }
                            }).setCancelable(false).show()
        }
        true
    }

    private val mBufferingUpdateListener = IMediaPlayer.OnBufferingUpdateListener { mp, percent ->
        mCurrentBufferPercentage = percent
        if (mOnBufferingUpdateListener != null) {
            mOnBufferingUpdateListener!!.onBufferingUpdate(mp, percent)
        }
    }

    private val mInfoListener = IMediaPlayer.OnInfoListener { mp, what, extra ->
        DebugLog.dfmt(TAG, "onInfo: (%d, %d)", what, extra)
        if (mOnInfoListener != null) {
            mOnInfoListener!!.onInfo(mp, what, extra)
        } else if (mMediaPlayer != null) {
            if (what == IMediaPlayer.MEDIA_INFO_BUFFERING_START) {
                DebugLog.dfmt(TAG, "onInfo: (MEDIA_INFO_BUFFERING_START)")
                if (mMediaBufferingIndicator != null) {
                    mMediaBufferingIndicator!!.visibility = View.VISIBLE
                }
            } else if (what == IMediaPlayer.MEDIA_INFO_BUFFERING_END) {
                DebugLog.dfmt(TAG, "onInfo: (MEDIA_INFO_BUFFERING_END)")
                if (mMediaBufferingIndicator != null) {
                    mMediaBufferingIndicator!!.visibility = View.GONE
                }
            }
        }
        true
    }

    private val mSeekCompleteListener = IMediaPlayer.OnSeekCompleteListener { mp ->
        DebugLog.d(TAG, "onSeekComplete")
        if (mOnSeekCompleteListener != null) {
            mOnSeekCompleteListener!!.onSeekComplete(mp)
        }
    }

    internal var mSHCallback: SurfaceHolder.Callback = object : SurfaceHolder.Callback {
        override fun surfaceChanged(holder: SurfaceHolder, format: Int, w: Int, h: Int) {
            mSurfaceHolder = holder
            if (mMediaPlayer != null) {
                mMediaPlayer!!.setDisplay(mSurfaceHolder)
            }
            mSurfaceWidth = w
            mSurfaceHeight = h
            val isValidState = mTargetState == STATE_PLAYING
            val hasValidSize = videoWidth == w && videoHeight == h
            if (mMediaPlayer != null && isValidState && hasValidSize) {
                if (mSeekWhenPrepared != 0L) {
                    seekTo(mSeekWhenPrepared)
                }
                start()
                if (mMediaController != null) {
                    if (mMediaController!!.isShowing) {
                        mMediaController!!.hide()
                    }
                    mMediaController!!.show()
                }
            }
        }


        override fun surfaceCreated(holder: SurfaceHolder) {
            mSurfaceHolder = holder
            if (mMediaPlayer != null && mCurrentState == STATE_SUSPEND
                    && mTargetState == STATE_RESUME) {
                mMediaPlayer!!.setDisplay(mSurfaceHolder)
                resume()
            } else {
                openVideo()
            }
        }


        override fun surfaceDestroyed(holder: SurfaceHolder) {
            mSurfaceHolder = null
            if (mMediaController != null) {
                mMediaController!!.hide()
            }
            if (mCurrentState != STATE_SUSPEND) {
                release(true)
            }
        }
    }


    constructor(context: Context) : super(context) {
        initVideoView(context)
    }


    @JvmOverloads constructor(context: Context, attrs: AttributeSet, defStyle: Int = 0) : super(context, attrs, defStyle) {
        initVideoView(context)
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = View.getDefaultSize(videoWidth, widthMeasureSpec)
        val height = View.getDefaultSize(videoHeight, heightMeasureSpec)
        setMeasuredDimension(width, height)
    }


    fun setVideoLayout(layout: Int) {
        val lp = layoutParams
        val res = ScreenResolution.getResolution(mContext!!)
        val windowWidth = res.first
        val windowHeight = res.second
        val windowRatio = windowWidth / windowHeight.toFloat()
        val sarNum = mVideoSarNum
        val sarDen = mVideoSarDen
        if (videoHeight > 0 && videoWidth > 0) {
            var videoRatio = videoWidth.toFloat() / videoHeight
            if (sarNum > 0 && sarDen > 0) {
                videoRatio = videoRatio * sarNum / sarDen
            }
            mSurfaceHeight = videoHeight
            mSurfaceWidth = videoWidth
            if (VIDEO_LAYOUT_ORIGIN == layout && mSurfaceWidth < windowWidth
                    && mSurfaceHeight < windowHeight) {
                lp.width = (mSurfaceHeight * videoRatio).toInt()
                lp.height = mSurfaceHeight
            } else if (layout == VIDEO_LAYOUT_ZOOM) {
                lp.width = if (windowRatio > videoRatio)
                    windowWidth
                else
                    (videoRatio * windowHeight).toInt()
                lp.height = if (windowRatio < videoRatio)
                    windowHeight
                else
                    (windowWidth / videoRatio).toInt()
            } else {
                val full = layout == VIDEO_LAYOUT_STRETCH
                lp.width = if (full || windowRatio < videoRatio)
                    windowWidth
                else
                    (videoRatio * windowHeight).toInt()
                lp.height = if (full || windowRatio > videoRatio)
                    windowHeight
                else
                    (windowWidth / videoRatio).toInt()
            }
            layoutParams = lp
            holder.setFixedSize(mSurfaceWidth, mSurfaceHeight)
            DebugLog.dfmt(
                    TAG,
                    "VIDEO: %dx%dx%f[SAR:%d:%d], Surface: %dx%d, LP: %dx%d, Window: %dx%dx%f",
                    videoWidth, videoHeight, videoRatio, mVideoSarNum,
                    mVideoSarDen, mSurfaceWidth, mSurfaceHeight, lp.width,
                    lp.height, windowWidth, windowHeight, windowRatio)
        }
        mVideoLayout = layout
    }


    private fun initVideoView(ctx: Context) {
        mContext = ctx
        videoWidth = 0
        videoHeight = 0
        mVideoSarNum = 0
        mVideoSarDen = 0
        holder.addCallback(mSHCallback)
        isFocusable = true
        isFocusableInTouchMode = true
        requestFocus()
        mCurrentState = STATE_IDLE
        mTargetState = STATE_IDLE
        if (ctx is Activity) {
            ctx.volumeControlStream = AudioManager.STREAM_MUSIC
        }
    }


    val isValid: Boolean
        get() = mSurfaceHolder != null && mSurfaceHolder!!.surface.isValid


    fun setVideoPath(path: String) {
        setVideoURI(Uri.parse(path))
    }


    fun setVideoURI(uri: Uri) {
        mUri = uri
        mSeekWhenPrepared = 0
        openVideo()
        requestLayout()
        invalidate()
    }

    fun setUserAgent(ua: String) {
        mUserAgent = ua
    }


    fun stopPlayback() {
        if (mMediaPlayer != null) {
            mMediaPlayer!!.stop()
            mMediaPlayer!!.release()
            mMediaPlayer = null
            mCurrentState = STATE_IDLE
            mTargetState = STATE_IDLE
        }
    }


    private fun openVideo() {
        if (mUri == null || mSurfaceHolder == null) {
            return
        }
        val i = Intent("com.android.music.musicservicecommand")
        i.putExtra("command", "pause")
        mContext!!.sendBroadcast(i)
        release(false)
        try {
            mDuration = -1
            mCurrentBufferPercentage = 0
            // mMediaPlayer = new AndroidMediaPlayer();
            var ijkMediaPlayer: IjkMediaPlayer? = null
            if (mUri != null) {
                ijkMediaPlayer = IjkMediaPlayer()
                ijkMediaPlayer.setLogEnabled(false)
                ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_CODEC, "skip_loop_filter", "48")
            }
            mMediaPlayer = ijkMediaPlayer
            assert(mMediaPlayer != null)
            mMediaPlayer!!.setOnPreparedListener(mPreparedListener)
            mMediaPlayer!!.setOnVideoSizeChangedListener(mSizeChangedListener)
            mMediaPlayer!!.setOnCompletionListener(mCompletionListener)
            mMediaPlayer!!.setOnErrorListener(mErrorListener)
            mMediaPlayer!!.setOnBufferingUpdateListener(mBufferingUpdateListener)
            mMediaPlayer!!.setOnInfoListener(mInfoListener)
            mMediaPlayer!!.setOnSeekCompleteListener(mSeekCompleteListener)
            if (mUri != null) {
                mMediaPlayer!!.dataSource = mUri!!.toString()
            }
            mMediaPlayer!!.setDisplay(mSurfaceHolder)
            mMediaPlayer!!.setScreenOnWhilePlaying(true)
            mMediaPlayer!!.prepareAsync()
            mCurrentState = STATE_PREPARING
            attachMediaController()
        } catch (ex: IOException) {
            DebugLog.e(TAG, "Unable to open content: " + mUri!!, ex)
            mCurrentState = STATE_ERROR
            mTargetState = STATE_ERROR
            mErrorListener.onError(mMediaPlayer,
                    IMediaPlayer.MEDIA_ERROR_UNKNOWN, 0)
        } catch (ex: IllegalArgumentException) {
            DebugLog.e(TAG, "Unable to open content: " + mUri!!, ex)
            mCurrentState = STATE_ERROR
            mTargetState = STATE_ERROR
            mErrorListener.onError(mMediaPlayer, IMediaPlayer.MEDIA_ERROR_UNKNOWN, 0)
        }

    }


    fun setMediaController(controller: MediaController) {
        if (mMediaController != null) {
            mMediaController!!.hide()
        }
        mMediaController = controller
        attachMediaController()
    }


    fun setMediaBufferingIndicator(mediaBufferingIndicator: View) {
        if (mMediaBufferingIndicator != null) {
            mMediaBufferingIndicator!!.visibility = View.GONE
        }
        mMediaBufferingIndicator = mediaBufferingIndicator
    }


    private fun attachMediaController() {
        if (mMediaPlayer != null && mMediaController != null) {
            mMediaController!!.setMediaPlayer(this)
            val anchorView = if (this.parent is View)
                this.parent as View
            else
                this
            mMediaController!!.setAnchorView(anchorView)
            mMediaController!!.isEnabled = isInPlaybackState
        }
    }

    fun setOnPreparedListener(l: IMediaPlayer.OnPreparedListener) {
        mOnPreparedListener = l
    }

    fun setOnCompletionListener(l: IMediaPlayer.OnCompletionListener) {
        mOnCompletionListener = l
    }

    fun setOnErrorListener(l: IMediaPlayer.OnErrorListener) {
        mOnErrorListener = l
    }

    fun setOnBufferingUpdateListener(l: IMediaPlayer.OnBufferingUpdateListener) {
        mOnBufferingUpdateListener = l
    }

    fun setOnSeekCompleteListener(l: IMediaPlayer.OnSeekCompleteListener) {
        mOnSeekCompleteListener = l
    }

    fun setOnInfoListener(l: IMediaPlayer.OnInfoListener) {
        mOnInfoListener = l
    }

    fun setOnControllerEventsListener(l: OnControllerEventsListener) {
        mOnControllerEventsListener = l
    }

    private fun release(cleartargetstate: Boolean) {
        if (mMediaPlayer != null) {
            mMediaPlayer!!.reset()
            mMediaPlayer!!.release()
            mMediaPlayer = null
            mCurrentState = STATE_IDLE
            if (cleartargetstate) {
                mTargetState = STATE_IDLE
            }
        }
    }


    override fun onTouchEvent(ev: MotionEvent): Boolean {
        if (isInPlaybackState && mMediaController != null) {
            toggleMediaControlsVisiblity()
        }
        return false
    }


    override fun onTrackballEvent(ev: MotionEvent): Boolean {
        if (isInPlaybackState && mMediaController != null) {
            toggleMediaControlsVisiblity()
        }
        return false
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        val isKeyCodeSupported = keyCode != KeyEvent.KEYCODE_BACK
                && keyCode != KeyEvent.KEYCODE_VOLUME_UP
                && keyCode != KeyEvent.KEYCODE_VOLUME_DOWN
                && keyCode != KeyEvent.KEYCODE_MENU
                && keyCode != KeyEvent.KEYCODE_CALL
                && keyCode != KeyEvent.KEYCODE_ENDCALL
        if (isInPlaybackState && isKeyCodeSupported
                && mMediaController != null) {
            if (keyCode == KeyEvent.KEYCODE_HEADSETHOOK
                    || keyCode == KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE
                    || keyCode == KeyEvent.KEYCODE_SPACE) {
                if (mMediaPlayer!!.isPlaying) {
                    pause()
                    mMediaController!!.show()
                } else {
                    start()
                    mMediaController!!.hide()
                }
                return true
            } else if (keyCode == KeyEvent.KEYCODE_MEDIA_STOP && mMediaPlayer!!.isPlaying) {
                pause()
                mMediaController!!.show()
            } else {
                toggleMediaControlsVisiblity()
            }
        }
        return super.onKeyDown(keyCode, event)
    }


    private fun toggleMediaControlsVisiblity() {
        if (mMediaController!!.isShowing) {
            mMediaController!!.hide()
        } else {
            mMediaController!!.show()
        }
    }


    override fun start() {
        if (isInPlaybackState) {
            mMediaPlayer!!.start()
            mCurrentState = STATE_PLAYING
        }
        mTargetState = STATE_PLAYING
        mOnControllerEventsListener!!.OnVideoResume()
    }

    override fun pause() {
        if (isInPlaybackState) {
            if (mMediaPlayer!!.isPlaying) {
                mMediaPlayer!!.pause()
                mCurrentState = STATE_PAUSED
            }
        }
        mTargetState = STATE_PAUSED
        mOnControllerEventsListener!!.onVideoPause()
    }


    fun resume() {
        if (mSurfaceHolder == null && mCurrentState == STATE_SUSPEND) {
            mTargetState = STATE_RESUME
        } else if (mCurrentState == STATE_SUSPEND_UNSUPPORTED) {
            openVideo()
        }
    }


    override val duration: Int
        get() {
            if (isInPlaybackState) {
                if (mDuration > 0) {
                    return mDuration.toInt()
                }
                mDuration = mMediaPlayer!!.duration
                return mDuration.toInt()
            }
            mDuration = -1
            return mDuration.toInt()
        }

    override val currentPosition: Int
        get() {
            if (isInPlaybackState) {
                val position = mMediaPlayer!!.currentPosition
                return position.toInt()
            }
            return 0
        }


    override fun seekTo(msec: Long) {
        if (isInPlaybackState) {
            mMediaPlayer!!.seekTo(msec)
            mSeekWhenPrepared = 0
        } else {
            mSeekWhenPrepared = msec
        }
    }


    override val isPlaying: Boolean
        get() = isInPlaybackState && mMediaPlayer!!.isPlaying


    override val bufferPercentage: Int
        get() {
            if (mMediaPlayer != null) {
                return mCurrentBufferPercentage
            }
            return 0
        }

    private val isInPlaybackState: Boolean
        get() = mMediaPlayer != null && mCurrentState != STATE_ERROR
                && mCurrentState != STATE_IDLE && mCurrentState != STATE_PREPARING

    override fun canPause(): Boolean {
        return mCanPause
    }

    fun canSeekBackward(): Boolean {
        return mCanSeekBack
    }

    fun canSeekForward(): Boolean {
        return mCanSeekForward
    }

    interface OnControllerEventsListener {
        fun onVideoPause()

        fun OnVideoResume()
    }

    companion object {
        private val TAG = VideoPlayerView::class.java.name
        val VIDEO_LAYOUT_ORIGIN = 0
        val VIDEO_LAYOUT_SCALE = 1
        val VIDEO_LAYOUT_STRETCH = 2
        val VIDEO_LAYOUT_ZOOM = 3
        private val STATE_ERROR = -1
        private val STATE_IDLE = 0
        private val STATE_PREPARING = 1
        private val STATE_PREPARED = 2
        private val STATE_PLAYING = 3
        private val STATE_PAUSED = 4
        private val STATE_PLAYBACK_COMPLETED = 5
        private val STATE_SUSPEND = 6
        private val STATE_RESUME = 7
        private val STATE_SUSPEND_UNSUPPORTED = 8
    }
}
