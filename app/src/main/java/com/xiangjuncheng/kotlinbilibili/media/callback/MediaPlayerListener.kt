package com.xiangjuncheng.kotlinbilibili.media.callback

/**
 * Created by xjc on 2017/10/15.
 */
interface MediaPlayerListener {
    fun start()

    fun pause()

    val duration: Int

    val currentPosition: Int

    fun seekTo(pos: Long)

    val isPlaying: Boolean

    val bufferPercentage: Int

    fun canPause(): Boolean
}