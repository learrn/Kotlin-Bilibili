package com.xiangjuncheng.kotlinbilibili.utils

import android.content.Context
import android.util.DisplayMetrics



/**
 * Created by xiangjuncheng on 2017/6/13.
 * 屏幕像素转换工具类
 */
object DisplayUtil {

    fun px2dp(context: Context, pxValue: Float): Int {

        val scale = context.getResources().getDisplayMetrics().density
        return (pxValue / scale + 0.5f).toInt()
    }


    fun dp2px(context: Context, dipValue: Float): Int {

        val scale = context.getResources().getDisplayMetrics().density
        return (dipValue * scale + 0.5f).toInt()
    }


    fun px2sp(context: Context, pxValue: Float): Int {

        val fontScale = context.getResources().getDisplayMetrics().scaledDensity
        return (pxValue / fontScale + 0.5f).toInt()
    }


    fun sp2px(context: Context, spValue: Float): Int {

        val fontScale = context.getResources().getDisplayMetrics().scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }


    fun getScreenWidth(context: Context): Int {

        val dm = context.getResources().getDisplayMetrics()
        return dm.widthPixels
    }


    fun getScreenHeight(context: Context): Int {

        val dm = context.getResources().getDisplayMetrics()
        return dm.heightPixels
    }


    fun getDisplayDensity(context: Context?): Float {

        if (context == null) {
            return -1f
        }
        return context!!.getResources().getDisplayMetrics().density
    }
}