package com.xiangjuncheng.kotlinbilibili.utils

import android.content.Context
import android.content.SharedPreferences


/**
 * Created by xiangjuncheng on 2017/5/23.
 */
class ThemeHelper {
    companion object{
        private val CURRENT_THEME = "theme_current"

        val CRAD_SAKURA = 0x1

        val CARD_HOPE = 0x2

        val CARD_STORM = 0x3

        val CARD_WOOD = 0x4

        val CARD_LIGHT = 0x5

        val CARD_THUNDER = 0x6

        val CARD_SAND = 0x7

        val CARD_FIREY = 0x8


        fun getSharePreference(context: Context): SharedPreferences {

            return context.getSharedPreferences("multiple_theme", Context.MODE_PRIVATE)
        }


        fun setTheme(context: Context, themeId: Int) {

            getSharePreference(context).edit()
                    .putInt(CURRENT_THEME, themeId)
                    .apply()
        }


        fun getTheme(context: Context): Int {

            return getSharePreference(context).getInt(CURRENT_THEME, CRAD_SAKURA)
        }


        fun isDefaultTheme(context: Context): Boolean {

            return getTheme(context) == CRAD_SAKURA
        }


        fun getName(currentTheme: Int): String {

            when (currentTheme) {
                CRAD_SAKURA -> return "THE SAKURA"
                CARD_STORM -> return "THE STORM"
                CARD_WOOD -> return "THE WOOD"
                CARD_LIGHT -> return "THE LIGHT"
                CARD_HOPE -> return "THE HOPE"
                CARD_THUNDER -> return "THE THUNDER"
                CARD_SAND -> return "THE SAND"
                CARD_FIREY -> return "THE FIREY"
            }
            return "THE RETURN"
        }
    }
}