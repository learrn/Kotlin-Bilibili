package com.xiangjuncheng.kotlinbilibili.utils

import android.view.View
import android.view.Window
import android.view.WindowManager

/**
 * Created by xiangjuncheng on 2017/5/25.
 */
class SystemUiVisibilityUtil {
    companion object {
        fun addFlags(view: View, flags: Int) {
            view.systemUiVisibility = view.systemUiVisibility or flags
        }

        fun clearFlags(view: View, flags: Int) {
            view.systemUiVisibility = view.systemUiVisibility and flags.inv()
        }

        fun hasFlags(view: View, flags: Int): Boolean {
            return (view.systemUiVisibility and flags) == flags
        }

        fun hideStatusBar(window: Window, enable: Boolean) {
            val p = window.attributes
            if (enable) {
                p.flags = p.flags or WindowManager.LayoutParams.FLAG_FULLSCREEN
            } else {
                p.flags = p.flags and WindowManager.LayoutParams.FLAG_FULLSCREEN.inv()
            }
            window.attributes = p
            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }
    }
}