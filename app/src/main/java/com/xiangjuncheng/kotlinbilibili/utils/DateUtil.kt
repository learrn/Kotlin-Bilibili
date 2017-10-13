package com.xiangjuncheng.kotlinbilibili.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by xiangjuncheng on 2017/10/13.
 */
object DateUtil {

    val FORMAT_YEAR = "yyyy"

    val FORMAT_MONTH_DAY = "MM月dd日"

    val FORMAT_DATE = "yyyy-MM-dd"

    val FORMAT_TIME = "HH:mm"

    val FORMAT_MONTH_DAY_TIME = "MM月dd日  hh:mm"

    val FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm"

    val FORMAT_DATE1_TIME = "yyyy/MM/dd HH:mm"

    val FORMAT_DATE_TIME_SECOND = "yyyy/MM/dd HH:mm:ss"

    private val sdf = SimpleDateFormat()

    private val YEAR = 365 * 24 * 60 * 60// 年

    private val MONTH = 30 * 24 * 60 * 60// 月

    private val DAY = 24 * 60 * 60// 天

    private val HOUR = 60 * 60// 小时

    private val MINUTE = 60// 分钟


    /**
     * 根据时间戳获取描述性时间，如3分钟前，1天前
     *
     * @param timestamp 时间戳 单位为毫秒
     * @return 时间字符串
     */

    fun getDescriptionTimeFromTimestamp(timestamp: Long): String {

        val currentTime = System.currentTimeMillis()
        // 与现在时间相差秒数
        val timeGap = (currentTime - timestamp) / 1000
        println("timeGap: " + timeGap)
        val timeStr: String
        if (timeGap > YEAR) {
            timeStr = (timeGap / YEAR).toString() + "年前"
        } else if (timeGap > MONTH) {
            timeStr = (timeGap / MONTH).toString() + "个月前"
        } else if (timeGap > DAY) {// 1天以上
            timeStr = (timeGap / DAY).toString() + "天前"
        } else if (timeGap > HOUR) {// 1小时-24小时
            timeStr = (timeGap / HOUR).toString() + "小时前"
        } else if (timeGap > MINUTE) {// 1分钟-59分钟
            timeStr = (timeGap / MINUTE).toString() + "分钟前"
        } else {// 1秒钟-59秒钟
            timeStr = "刚刚"
        }
        return timeStr
    }


    /**
     * 获取当前日期的指定格式的字符串
     *
     * @param format 指定的日期时间格式，若为null或""则使用指定的格式"yyyy-MM-dd HH:MM"
     */

    fun getCurrentTime(format: String?): String {

        if (format == null || format.trim { it <= ' ' } == "") {
            sdf.applyPattern(FORMAT_DATE_TIME)
        } else {
            sdf.applyPattern(format)
        }
        return sdf.format(Date())
    }


    /**
     * date类型转换为String类型
     * formatType格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
     * data Date类型的时间
     */

    fun dateToString(data: Date?, formatType: String): String {

        return SimpleDateFormat(formatType).format(data)
    }


    /**
     * long类型转换为String类型
     * currentTime要转换的long类型的时间
     * formatType要转换的string类型的时间格式
     */

    fun longToString(currentTime: Long, formatType: String): String {

        val strTime: String
        // long类型转成Date类型
        val date = longToDate(currentTime, formatType)
        // date类型转成String
        strTime = dateToString(date, formatType)
        return strTime
    }


    /**
     * string类型转换为date类型
     * strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
     * HH时mm分ss秒，
     * strTime的时间格式必须要与formatType的时间格式相同
     */

    fun stringToDate(strTime: String, formatType: String): Date? {

        val formatter = SimpleDateFormat(formatType)
        var date: Date? = null
        try {
            date = formatter.parse(strTime)
        } catch (e: ParseException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

        return date
    }


    /**
     * long转换为Date类型
     * currentTime要转换的long类型的时间
     * formatType要转换的时间格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
     */

    fun longToDate(currentTime: Long, formatType: String): Date? {

        // 根据long类型的毫秒数生命一个date类型的时间
        val dateOld = Date(currentTime)
        // 把date类型的时间转换为string
        val sDateTime = dateToString(dateOld, formatType)
        // 把String类型转换为Date类型
        return stringToDate(sDateTime, formatType)
    }


    /**
     * string类型转换为long类型
     * strTime要转换的String类型的时间
     * formatType时间格式
     * strTime的时间格式和formatType的时间格式必须相同
     */

    fun stringToLong(strTime: String, formatType: String): Long {

        // String类型转成date类型
        val date = stringToDate(strTime, formatType)
        return if (date == null) {
            0
        } else {
            // date类型转成long类型
            dateToLong(date)
        }
    }


    /**
     * date类型转换为long类型
     * date要转换的date类型的时间
     */

    fun dateToLong(date: Date): Long {
        return date.time
    }
}