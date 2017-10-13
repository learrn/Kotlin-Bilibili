package com.xiangjuncheng.kotlinbilibili.utils

/**
 * Created by xiangjuncheng on 2017/10/13.
 */

object NumberUtil {

    fun converString(num: Int): String {

        if (num < 100000) {
            return num.toString()
        }
        val unit = "万"
        val newNum = num / 10000.0

        val numStr = String.format("%." + 1 + "f", newNum)
        return numStr + unit
    }
}