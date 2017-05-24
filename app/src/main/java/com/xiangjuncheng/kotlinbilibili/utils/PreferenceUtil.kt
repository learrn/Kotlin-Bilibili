package com.xiangjuncheng.kotlinbilibili.utils

import android.content.Context
import com.xiangjuncheng.kotlinbilibili.BilibiliApp
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by xiangjuncheng on 2017/5/24.
 * SP存储工具类
 */


class PreferenceUtil<T>(val context: Context = BilibiliApp.instance, val name: String, val default: T)
    : ReadWriteProperty<Any?, T> {
    val prefs by lazy {
        context.getSharedPreferences("default", Context.MODE_PRIVATE)
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return findPreference(name, default)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        putPreference(name, value)
    }

    private fun <T> findPreference(name: String, default: T): T = with(prefs) {
        val res: Any = when (default) {
            is Long -> getLong(name, default)
            is String -> getString(name, default)
            is Int -> getInt(name, default)
            is Boolean -> getBoolean(name, default)
            is Float -> getFloat(name, default)
            else -> throw IllegalArgumentException(
                    "This type can be saved into Preferences")
        }

        res as T
    }

    private fun <U> putPreference(name: String, value: U) = with(prefs.edit()) {
        when (value) {
            is Long -> putLong(name, value)
            is String -> putString(name, value)
            is Int -> putInt(name, value)
            is Boolean -> putBoolean(name, value)
            is Float -> putFloat(name, value)
            else -> throw IllegalArgumentException("This type can be saved into Preferences ")
        }.apply()
    }

    /**
     * 删除全部数据
     */
    fun clearPreference() {
        prefs.edit().clear().commit()
    }

    /**
     * 根据key删除存储数据
     */
    fun clearPreference(key: String) {
        prefs.edit().remove(key).commit()
    }

}

