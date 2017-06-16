package com.xiangjuncheng.kotlinbilibili.utils

import android.util.Log

/**
 * Created by xiangjuncheng on 2017/6/16.
 */
object LogUtil {

    private val TAG = "LogUtil"

    var isShow = true


    fun i(tag: String, msg: String) {

        if (isShow) {
            Log.i(tag, msg)
        }
    }


    fun w(tag: String, msg: String) {

        if (isShow) {
            Log.w(tag, msg)
        }
    }


    fun e(tag: String, msg: String) {

        if (isShow) {
            Log.e(tag, msg)
        }
    }


    fun all(msg: String) {

        if (isShow) {
            Log.e("all", msg)
        }
    }


    fun i(msg: String) {

        if (isShow) {
            Log.i(TAG, msg)
        }
    }


    fun w(msg: String) {

        if (isShow) {
            Log.w(TAG, msg)
        }
    }


    fun e(msg: String) {

        if (isShow) {
            Log.e(TAG, msg)
        }
    }


    fun v(msg: String) {
        e(msg)
    }


    fun d(msg: String) {
        v(msg)
    }
}