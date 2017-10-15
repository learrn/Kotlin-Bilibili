package com.xiangjuncheng.kotlinbilibili.media

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.util.Pair
import android.view.Display
import android.view.WindowManager

/**
 * Created by xjc on 2017/10/15.
 */
object ScreenResolution {

    /**
     * Gets the resolution,

     * @return a pair to return the width and height
     */
    fun getResolution(ctx: Context): Pair<Int, Int> {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return getRealResolution(ctx)
        } else {
            return getRealResolutionOnOldDevice(ctx)
        }
    }


    /**
     * Gets resolution on old devices.
     * Tries the reflection to get the real resolution first.
     * Fall back to getDisplayMetrics if the above method failed.
     */
    private fun getRealResolutionOnOldDevice(ctx: Context): Pair<Int, Int> {
        try {
            val wm = ctx.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = wm.defaultDisplay
            val mGetRawWidth = Display::class.java.getMethod("getRawWidth")
            val mGetRawHeight = Display::class.java.getMethod("getRawHeight")
            val realWidth = mGetRawWidth.invoke(display) as Int
            val realHeight = mGetRawHeight.invoke(display) as Int
            return Pair(realWidth, realHeight)
        } catch (e: Exception) {
            val disp = ctx.resources.displayMetrics
            return Pair(disp.widthPixels, disp.heightPixels)
        }

    }


    /**
     * Gets real resolution via the new getRealMetrics API.
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private fun getRealResolution(ctx: Context): Pair<Int, Int> {
        val wm = ctx.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val metrics = DisplayMetrics()
        display.getRealMetrics(metrics)
        return Pair(metrics.widthPixels, metrics.heightPixels)
    }
}