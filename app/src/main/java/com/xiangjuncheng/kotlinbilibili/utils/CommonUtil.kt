package com.xiangjuncheng.kotlinbilibili.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Environment
import android.os.StatFs
import java.io.File


/**
 * Created by xiangjuncheng on 2017/5/24.
 * 公用工具类
 */
class CommonUtil {
    companion object {
        /**
         * 检查是否有网络
         */
        fun isNetworkAvailable(context: Context): Boolean {
            val info = getNetworkInfo(context)
            return info!!.isAvailable
        }

        /**
         * 检查是否是WIFI
         */
        fun isWifi(context: Context): Boolean {
            val info = getNetworkInfo(context)
            return info?.type == ConnectivityManager.TYPE_WIFI
        }

        /**
         * 检查是否是移动网络
         */
        fun isMobile(context: Context): Boolean {
            val info = getNetworkInfo(context)
            return info?.type == ConnectivityManager.TYPE_MOBILE
        }

        private fun getNetworkInfo(context: Context): NetworkInfo? {

            val cm = context.getSystemService(
                    Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return cm.activeNetworkInfo
        }

        /**
         * 获取手机内部存储总空间
         */
        fun getPhoneTotalSize(): Long {
            val path: File
            if (checkSDcard()) {
                path = Environment.getExternalStorageDirectory()
            } else {
                path = Environment.getDataDirectory()
            }
            val mStatFs = StatFs(path.path)
            return mStatFs.blockSizeLong * mStatFs.blockCountLong
        }

        /**
         * 获取手机内存存储可用空间
         */
        fun getPhoneAvailableSize(): Long {
            val path: File
            if (checkSDcard()) {
                path = Environment.getExternalStorageDirectory()
            } else {
                path = Environment.getDataDirectory()
            }
            val mStatFs = StatFs(path.path)
            return mStatFs.blockSizeLong * mStatFs.availableBlocksLong
        }

        /**
         * 检查SD卡是否存在
         */
        private fun checkSDcard(): Boolean {
            return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)
        }
    }
}