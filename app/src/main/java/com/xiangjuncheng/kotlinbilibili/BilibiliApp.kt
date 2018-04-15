package com.xiangjuncheng.kotlinbilibili

import android.app.Application
import android.content.Context
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import com.bilibili.magicasakura.utils.ThemeUtils
import com.squareup.leakcanary.LeakCanary
import com.xiangjuncheng.kotlinbilibili.utils.ThemeHelper


class BilibiliApp : Application(), ThemeUtils.switchColor {
    companion object {
        lateinit var instance: BilibiliApp
    }


    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        // 初始化主题切换
        ThemeUtils.setSwitchColor(this)
        instance = this
        //初始化Leak内存泄露检测工具
        LeakCanary.install(this)
        //初始化Stetho调试工具
//        Stetho.initialize(
//                Stetho.newInitializerBuilder(this)
//                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
//                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
//                        .build())
    }

    override fun replaceColorById(context: Context, @ColorRes colorId: Int): Int {
        if (ThemeHelper.isDefaultTheme(context)) return context.resources.getColor(colorId)
        val theme = getTheme(context)
        var mColorId: Int = colorId
        if (theme != null) {
            mColorId = getThemeColorId(context, colorId, theme)
        }
        return context.resources.getColor(mColorId)

    }

    override fun replaceColor(context: Context, @ColorInt color: Int): Int {
        if (ThemeHelper.isDefaultTheme(context)) return color
        var mColorId: Int = -1
        val theme = getTheme(context)
        if (theme != null) {
            mColorId = getThemeColor(context, color, theme)
        }
        return if (mColorId != -1) resources.getColor(mColorId) else color
    }

    @ColorRes
    private fun getThemeColor(context: Context, color: Int, theme: String): Int = when (color.toLong()) {
        0xfffb7299 -> context.resources.getIdentifier(theme, "color", packageName)
        0xffb85671 -> context.resources.getIdentifier(theme + "_dark", "color", packageName)
        0x99f0486c -> context.resources.getIdentifier(theme + "_trans", "color", packageName)
        else -> {
            -1
        }
    }


    private fun getTheme(context: Context): String? = when (ThemeHelper.getTheme(context)) {
        ThemeHelper.CARD_STORM -> "blue"
        ThemeHelper.CARD_HOPE -> "purple"
        ThemeHelper.CARD_WOOD -> "green"
        ThemeHelper.CARD_LIGHT -> "green_light"
        ThemeHelper.CARD_THUNDER -> "yellow"
        ThemeHelper.CARD_SAND -> "orange"
        ThemeHelper.CARD_FIREY -> "red"
        else -> {
            null
        }
    }

    @ColorRes
    private fun getThemeColorId(context: Context, colorId: Int, theme: String): Int = when (colorId) {
        R.color.theme_color_primary -> context.resources.getIdentifier(theme, "color", packageName)
        R.color.theme_color_primary_dark -> context.resources.getIdentifier(theme + "_dark", "color", packageName)
        R.color.theme_color_primary_trans -> context.resources.getIdentifier(theme + "_trans", "color", packageName)
        else -> {
            colorId
        }
    }
}
