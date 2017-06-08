package com.xiangjuncheng.kotlinbilibili.utils

import android.os.Build
import android.support.annotation.ColorInt
import android.app.Activity
import android.graphics.Color
import android.support.annotation.FloatRange
import android.view.Window
import android.support.v4.view.ViewCompat
import android.view.ViewGroup
import android.view.WindowManager
import android.support.v4.view.ViewCompat.setFitsSystemWindows
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
import android.support.v4.widget.DrawerLayout
import android.view.View
import android.widget.FrameLayout
import android.annotation.TargetApi
import android.content.Context
import android.util.Log
import com.xiangjuncheng.kotlinbilibili.R
import java.lang.reflect.Array.setInt
import java.lang.reflect.AccessibleObject.setAccessible
import sun.plugin.viewer.LifeCycleManager.getIdentifier
import java.util.regex.Pattern


/**
 * 状态栏工具类
 * 状态栏两种模式(Android 4.4以上)
 * 1.沉浸式全屏模式
 * 2.状态栏着色模式
 */
class SystemBarHelper {
    companion object {
        private val DEFAULT_ALPHA = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            0.2f
        else
            0.3f


        /**
         * Android4.4以上的状态栏着色
         *
         * @param activity Activity对象
         * @param statusBarColor 状态栏颜色
         * @param alpha 透明栏透明度[0.0-1.0]
         */
        fun tintStatusBar(activity: Activity,
                          @ColorInt statusBarColor: Int,
                          @FloatRange(from = 0.0, to = 1.0) alpha: Float = DEFAULT_ALPHA) {

            tintStatusBar(activity.window, statusBarColor, alpha)
        }

        /**
         * Android4.4以上的状态栏着色
         *
         * @param window 一般都是用于Activity的window,也可以是其他的例如Dialog,DialogFragment
         * @param statusBarColor 状态栏颜色
         * @param alpha 透明栏透明度[0.0-1.0]
         */
        fun tintStatusBar(window: Window,
                          @ColorInt statusBarColor: Int,
                          @FloatRange(from = 0.0, to = 1.0) alpha: Float = DEFAULT_ALPHA) {

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                return
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor = Color.TRANSPARENT
            } else {
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            }

            val decorView = window.decorView as ViewGroup
            val contentView = window.decorView
                    .findViewById(Window.ID_ANDROID_CONTENT) as ViewGroup
            val rootView = contentView.getChildAt(0)
            if (rootView != null) {
                ViewCompat.setFitsSystemWindows(rootView, true)
            }

            setStatusBar(decorView, statusBarColor, true)
            setTranslucentView(decorView, alpha)
        }

        /**
         * Android4.4以上的状态栏着色(针对于DrawerLayout)
         * 注:
         * 1.如果出现界面展示不正确,删除布局中所有fitsSystemWindows属性,尤其是DrawerLayout的fitsSystemWindows属性
         * 2.可以版本判断在5.0以上不调用该方法,使用系统自带
         *
         * @param activity Activity对象
         * @param drawerLayout DrawerLayout对象
         * @param statusBarColor 状态栏颜色
         * @param alpha 透明栏透明度[0.0-1.0]
         */
        fun tintStatusBarForDrawer(activity: Activity, drawerLayout: DrawerLayout,
                                   @ColorInt statusBarColor: Int,
                                   @FloatRange(from = 0.0, to = 1.0) alpha: Float = DEFAULT_ALPHA) {

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                return
            }

            val window = activity.window
            val decorView = window.decorView as ViewGroup
            val drawContent = drawerLayout.getChildAt(0) as ViewGroup

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor = Color.TRANSPARENT
                drawerLayout.setStatusBarBackgroundColor(statusBarColor)

                var systemUiVisibility = window.decorView.systemUiVisibility
                systemUiVisibility = systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                systemUiVisibility = systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                window.decorView.systemUiVisibility = systemUiVisibility
            } else {
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            }

            setStatusBar(decorView, statusBarColor, true, true)
            setTranslucentView(decorView, alpha)

            drawerLayout.fitsSystemWindows = false
            drawContent.fitsSystemWindows = true
            val drawer = drawerLayout.getChildAt(1) as ViewGroup
            drawer.fitsSystemWindows = false
        }

        /**
         * Android4.4以上的沉浸式全屏模式
         * 注:
         * 1.删除fitsSystemWindows属性:Android5.0以上使用该方法如果出现界面展示不正确,删除布局中所有fitsSystemWindows属性
         * 或者调用forceFitsSystemWindows方法
         * 2.不删除fitsSystemWindows属性:也可以区别处理,Android5.0以上使用自己的方式实现,不调用该方法
         *
         * @param activity Activity对象
         * @param alpha 透明栏透明度[0.0-1.0]
         */
        fun immersiveStatusBar(activity: Activity,
                               @FloatRange(from = 0.0, to = 1.0) alpha: Float = DEFAULT_ALPHA) {

            immersiveStatusBar(activity.window, alpha)
        }

        /**
         * Android4.4以上的沉浸式全屏模式
         * 注:
         * 1.删除fitsSystemWindows属性:Android5.0以上使用该方法如果出现界面展示不正确,删除布局中所有fitsSystemWindows属性
         * 或者调用forceFitsSystemWindows方法
         * 2.不删除fitsSystemWindows属性:也可以区别处理,Android5.0以上使用自己的方式实现,不调用该方法
         *
         * @param window 一般都是用于Activity的window,也可以是其他的例如Dialog,DialogFragment
         * @param alpha 透明栏透明度[0.0-1.0]
         */
        fun immersiveStatusBar(window: Window,
                               @FloatRange(from = 0.0, to = 1.0) alpha: Float = DEFAULT_ALPHA) {

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                return
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor = Color.TRANSPARENT

                var systemUiVisibility = window.decorView.systemUiVisibility
                systemUiVisibility = systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                systemUiVisibility = systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                window.decorView.systemUiVisibility = systemUiVisibility
            } else {
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            }

            val decorView = window.decorView as ViewGroup
            val contentView = window.decorView
                    .findViewById(Window.ID_ANDROID_CONTENT) as ViewGroup
            val rootView = contentView.getChildAt(0)
            val statusBarHeight = getStatusBarHeight(window.context)
            if (rootView != null) {
                val lp = rootView.layoutParams as FrameLayout.LayoutParams
                ViewCompat.setFitsSystemWindows(rootView, true)
                lp.topMargin = -statusBarHeight
                rootView.layoutParams = lp
            }

            setTranslucentView(decorView, alpha)
        }

        /**
         * 设置状态栏darkMode,字体颜色及icon变黑(目前支持MIUI6以上,Flyme4以上,Android M以上)
         */
        fun setStatusBarDarkMode(activity: Activity) {
            setStatusBarDarkMode(activity.window)
        }

        /**
         * 设置状态栏darkMode,字体颜色及icon变黑(目前支持MIUI6以上,Flyme4以上,Android M以上)
         */
        fun setStatusBarDarkMode(window: Window) {

            if (isFlyme4Later()) {
                setStatusBarDarkModeForFlyme4(window, true)
            } else if (isMIUI6Later()) {
                setStatusBarDarkModeForMIUI6(window, true)
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                setStatusBarDarkModeForM(window)
            }
        }

        /**
         * android 6.0设置字体颜色
         */
        @TargetApi(Build.VERSION_CODES.M)
        fun setStatusBarDarkModeForM(window: Window) {

            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT

            var systemUiVisibility = window.decorView.systemUiVisibility
            systemUiVisibility = systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.decorView.systemUiVisibility = systemUiVisibility
        }

        /**
         * 设置Flyme4+的darkMode,darkMode时候字体颜色及icon变黑
         * http://open-wiki.flyme.cn/index.php?title=Flyme%E7%B3%BB%E7%BB%9FAPI
         */
        fun setStatusBarDarkModeForFlyme4(window: Window?, dark: Boolean): Boolean {

            var result = false
            if (window != null) {
                try {
                    val e = window.attributes
                    val darkFlag = WindowManager.LayoutParams::class.java.getDeclaredField(
                            "MEIZU_FLAG_DARK_STATUS_BAR_ICON")
                    val meizuFlags = WindowManager.LayoutParams::class.java.getDeclaredField("meizuFlags")
                    darkFlag.isAccessible = true
                    meizuFlags.isAccessible = true
                    val bit = darkFlag.getInt(null)
                    var value = meizuFlags.getInt(e)
                    if (dark) {
                        value = value or bit
                    } else {
                        value = value and bit.inv()
                    }

                    meizuFlags.setInt(e, value)
                    window.attributes = e
                    result = true
                } catch (var8: Exception) {
                    Log.e("StatusBar", "setStatusBarDarkIcon: failed")
                }

            }
            return result
        }

        /**
         * 设置MIUI6+的状态栏是否为darkMode,darkMode时候字体颜色及icon变黑
         * http://dev.xiaomi.com/doc/p=4769/
         */
        fun setStatusBarDarkModeForMIUI6(window: Window, darkmode: Boolean) {

            val clazz = window.javaClass
            try {
                var darkModeFlag = 0
                val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
                val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
                darkModeFlag = field.getInt(layoutParams)
                val extraFlagField = clazz.getMethod("setExtraFlags", Int::class.javaPrimitiveType, Int::class.javaPrimitiveType)
                extraFlagField.invoke(window, if (darkmode) darkModeFlag else 0, darkModeFlag)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        /**
         * 创建假的状态栏View
         */
        private fun setStatusBar(container: ViewGroup, @ColorInt
        statusBarColor: Int, visible: Boolean, addToFirst: Boolean) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                var statusBarView: View? = container.findViewById(R.id.statusbar_view)
                if (statusBarView == null) {
                    statusBarView = View(container.context)
                    statusBarView.id = R.id.statusbar_view
                    val lp = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(container.context))
                    if (addToFirst) {
                        container.addView(statusBarView, 0, lp)
                    } else {
                        container.addView(statusBarView, lp)
                    }
                }

                statusBarView.setBackgroundColor(statusBarColor)
                statusBarView.visibility = if (visible) View.VISIBLE else View.GONE
            }
        }

        /**
         * 创建假的状态栏View
         */
        private fun setStatusBar(container: ViewGroup,
                                 @ColorInt statusBarColor: Int, visible: Boolean) {

            setStatusBar(container, statusBarColor, visible, false)
        }


        /**
         * 创建假的透明栏
         */
        private fun setTranslucentView(container: ViewGroup,
                                       @FloatRange(from = 0.0, to = 1.0) alpha: Float) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                var translucentView: View? = container.findViewById(R.id.translucent_view)
                if (translucentView == null) {
                    translucentView = View(container.context)
                    translucentView.id = R.id.translucent_view
                    val lp = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(container.context))
                    container.addView(translucentView, lp)
                }

                translucentView.setBackgroundColor(Color.argb((alpha * 255).toInt(), 0, 0, 0))
            }
        }


        /**
         * 获取状态栏高度
         */
        fun getStatusBarHeight(context: Context): Int {

            var result = 0
            val resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android")
            if (resId > 0) {
                result = context.getResources().getDimensionPixelSize(resId)
            }
            return result
        }

        /**
         * 判断是否Flyme4以上
         */
        fun isFlyme4Later(): Boolean {

            return Build.FINGERPRINT.contains("Flyme_OS_4")
                    || Build.VERSION.INCREMENTAL.contains("Flyme_OS_4")
                    ||
                    Pattern.compile("Flyme OS [4|5]", Pattern.CASE_INSENSITIVE).matcher(Build.DISPLAY).find()
        }


        /**
         * 判断是否为MIUI6以上
         */
        fun isMIUI6Later(): Boolean {

            try {
                val clz = Class.forName("android.os.SystemProperties")
                val mtd = clz.getMethod("get", String::class.java)
                var `val` = mtd.invoke(null, "ro.miui.ui.version.name") as String
                `val` = `val`.replace("[vV]".toRegex(), "")
                val version = Integer.parseInt(`val`)
                return version >= 6
            } catch (e: Exception) {
                return false
            }
        }

        /**
         * 增加View的高度以及paddingTop,增加的值为状态栏高度.一般是在沉浸式全屏给ToolBar用的
         */
        fun setHeightAndPadding(context: Context, view: View) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                val lp = view.layoutParams
                lp.height += getStatusBarHeight(context)//增高
                view.setPadding(view.paddingLeft, view.paddingTop + getStatusBarHeight(context),
                        view.paddingRight, view.paddingBottom)
            }
        }


        /**
         * 增加View的paddingTop,增加的值为状态栏高度
         */
        fun setPadding(context: Context, view: View) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                view.setPadding(view.paddingLeft, view.paddingTop + getStatusBarHeight(context),
                        view.paddingRight, view.paddingBottom)
            }
        }


        /**
         * 强制rootView下面的子View的FitsSystemWindows为false
         */
        fun forceFitsSystemWindows(activity: Activity) {

            forceFitsSystemWindows(activity.window)
        }


        /**
         * 强制rootView下面的子View的FitsSystemWindows为false
         */
        fun forceFitsSystemWindows(window: Window) {

            forceFitsSystemWindows(
                    window.decorView.findViewById(Window.ID_ANDROID_CONTENT) as ViewGroup)
        }

        /**
         * 强制rootView下面的子View的FitsSystemWindows为false
         */
        fun forceFitsSystemWindows(viewGroup: ViewGroup) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                val count = viewGroup.childCount
                for (i in 0..count - 1) {
                    val view = viewGroup.getChildAt(i)
                    if (view is ViewGroup) {
                        forceFitsSystemWindows(view)
                    } else if (ViewCompat.getFitsSystemWindows(view)) {
                        ViewCompat.setFitsSystemWindows(view, false)
                    }
                }
            }
        }
    }
}