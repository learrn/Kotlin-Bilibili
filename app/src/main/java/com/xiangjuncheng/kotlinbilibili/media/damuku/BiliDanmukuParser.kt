package com.xiangjuncheng.kotlinbilibili.media.damuku

import android.graphics.Color
import android.text.TextUtils
import master.flame.danmaku.danmaku.model.AlphaValue
import master.flame.danmaku.danmaku.model.BaseDanmaku
import master.flame.danmaku.danmaku.model.Duration
import master.flame.danmaku.danmaku.model.IDisplayer
import master.flame.danmaku.danmaku.model.android.DanmakuFactory
import master.flame.danmaku.danmaku.model.android.Danmakus
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser
import master.flame.danmaku.danmaku.parser.android.AndroidFileSource
import master.flame.danmaku.danmaku.util.DanmakuUtils
import org.json.JSONArray
import org.json.JSONException
import org.xml.sax.Attributes
import org.xml.sax.InputSource
import org.xml.sax.SAXException
import org.xml.sax.helpers.DefaultHandler
import org.xml.sax.helpers.XMLReaderFactory
import java.io.IOException
import java.util.*

/**
 * Created by xjc on 2017/10/15.
 */
class BiliDanmukuParser : BaseDanmakuParser() {
    private var mDispScaleX: Float = 0.toFloat()
    private var mDispScaleY: Float = 0.toFloat()

    public override fun parse(): Danmakus? {
        if (mDataSource != null) {
            val source = mDataSource as AndroidFileSource
            try {
                val xmlReader = XMLReaderFactory.createXMLReader()
                val contentHandler = XmlContentHandler()
                xmlReader.contentHandler = contentHandler
                xmlReader.parse(InputSource(source.data()))
                return contentHandler.result
            } catch (e: SAXException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        return null
    }


    inner class XmlContentHandler : DefaultHandler() {
        var result: Danmakus? = null
        var item: BaseDanmaku? = null
        var completed = false
        var index = 0

        @Throws(SAXException::class)
        override fun startDocument() {
            result = Danmakus()
        }

        @Throws(SAXException::class)
        override fun endDocument() {
            completed = true
        }

        @Throws(SAXException::class)
        override fun startElement(uri: String, localName: String, qName: String, attributes: Attributes) {
            var tagName = if (localName.length != 0) localName else qName
            tagName = tagName.toLowerCase(Locale.getDefault()).trim { it <= ' ' }
            if (tagName == "d") {
                // <d p="23.826000213623,1,25,16777215,1422201084,0,057075e9,757076900">我从未见过如此厚颜无耻之猴</d>
                // 0:时间(弹幕出现时间)
                // 1:类型(1从右至左滚动弹幕|6从左至右滚动弹幕|5顶端固定弹幕|4底端固定弹幕|7高级弹幕|8脚本弹幕)
                // 2:字号
                // 3:颜色
                // 4:时间戳 ?
                // 5:弹幕池id
                // 6:用户hash
                // 7:弹幕id
                val pValue = attributes.getValue("p")
                // parse p value to danmaku
                val values = pValue.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                if (values.size > 0) {
                    val time = (java.lang.Float.parseFloat(values[0]) * 1000).toLong() // 出现时间
                    val type = Integer.parseInt(values[1]) // 弹幕类型
                    val textSize = java.lang.Float.parseFloat(values[2]) // 字体大小
                    val color = (0x00000000ff000000.toInt() or values[3].toInt() and 0x00000000ffffffff.toInt()).toInt() // 颜色
                    // int poolType = Integer.parseInt(values[5]); // 弹幕池类型 忽略
                    item = mContext.mDanmakuFactory.createDanmaku(type, mContext)
                    if (item != null) {
                        item!!.time = time
                        item!!.textSize = textSize * (mDispDensity - 0.6f)
                        item!!.textColor = color
                        item!!.textShadowColor = if (color <= Color.BLACK) Color.WHITE else Color.BLACK
                    }
                }
            }
        }


        @Throws(SAXException::class)
        override fun endElement(uri: String, localName: String, qName: String) {
            if (item != null) {
                if (item!!.duration != null) {
                    val tagName = if (localName.length != 0) localName else qName
                    if (tagName.equals("d", ignoreCase = true)) {
                        item!!.timer = mTimer
                        result!!.addItem(item)
                    }
                }
                item = null
            }
        }


        override fun characters(ch: CharArray, start: Int, length: Int) {
            if (item != null) {
                DanmakuUtils.fillText(item!!, decodeXmlString(String(ch, start, length)))
                item!!.index = index++
                // initial specail danmaku data
                val text = item!!.text.toString().trim { it <= ' ' }
                if (item!!.type == BaseDanmaku.TYPE_SPECIAL && text.startsWith("[")
                        && text.endsWith("]")) {
                    //text = text.substring(1, text.length() - 1);
                    var textArr: Array<String?>? = null//text.split(",", -1);
                    try {
                        val jsonArray = JSONArray(text)
                        textArr = arrayOfNulls<String>(jsonArray.length())
                        for (i in textArr.indices) {
                            textArr[i] = jsonArray.getString(i)
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }

                    if (textArr == null || textArr.size < 5) {
                        item = null
                        return
                    }
                    item!!.text = textArr[4]
                    var beginX = java.lang.Float.parseFloat(textArr[0])
                    var beginY = java.lang.Float.parseFloat(textArr[1])
                    var endX = beginX
                    var endY = beginY
                    val alphaArr = textArr[2]?.split("-".toRegex())?.dropLastWhile { it.isEmpty() }?.toTypedArray()
                    val beginAlpha = (AlphaValue.MAX * java.lang.Float.parseFloat(alphaArr?.get(0))).toInt()
                    var endAlpha = beginAlpha
                    if (alphaArr?.size!!> 1) {
                        endAlpha = (AlphaValue.MAX * java.lang.Float.parseFloat(alphaArr[1])).toInt()
                    }
                    val alphaDuraion = (java.lang.Float.parseFloat(textArr[3]) * 1000).toLong()
                    var translationDuration = alphaDuraion
                    var translationStartDelay: Long = 0
                    var rotateY = 0f
                    var rotateZ = 0f
                    if (textArr.size >= 7) {
                        rotateZ = java.lang.Float.parseFloat(textArr[5])
                        rotateY = java.lang.Float.parseFloat(textArr[6])
                    }
                    if (textArr.size >= 11) {
                        endX = java.lang.Float.parseFloat(textArr[7])
                        endY = java.lang.Float.parseFloat(textArr[8])
                        if ("" != textArr[9]) {
                            translationDuration = Integer.parseInt(textArr[9]).toLong()
                        }
                        if ("" != textArr[10]) {
                            translationStartDelay = java.lang.Float.parseFloat(textArr[10]).toLong()
                        }
                    }
                    if (isPercentageNumber(beginX)) {
                        beginX *= DanmakuFactory.BILI_PLAYER_WIDTH
                    }
                    if (isPercentageNumber(beginY)) {
                        beginY *= DanmakuFactory.BILI_PLAYER_HEIGHT
                    }
                    if (isPercentageNumber(endX)) {
                        endX *= DanmakuFactory.BILI_PLAYER_WIDTH
                    }
                    if (isPercentageNumber(endY)) {
                        endY *= DanmakuFactory.BILI_PLAYER_HEIGHT
                    }
                    item!!.duration = Duration(alphaDuraion)
                    item!!.rotationZ = rotateZ
                    item!!.rotationY = rotateY
                    mContext.mDanmakuFactory.fillTranslationData(item!!, beginX,
                            beginY, endX, endY, translationDuration, translationStartDelay, mDispScaleX,
                            mDispScaleY)
                    mContext.mDanmakuFactory.fillAlphaData(item!!, beginAlpha, endAlpha, alphaDuraion)

                    if (textArr.size >= 12) {
                        // 是否有描边
                        if (!TextUtils.isEmpty(textArr[11]) && TRUE_STRING == textArr[11]) {
                            item!!.textShadowColor = Color.TRANSPARENT
                        }
                    }
                    if (textArr.size >= 13) {
                        //TODO 字体 textArr[12]
                    }
                    if (textArr.size >= 14) {
                        //TODO 是否有动画缓冲(easing)
                    }
                    if (textArr.size >= 15) {
                        // 路径数据
                        if ("" != textArr[14]) {
                            val motionPathString = textArr[14]?.substring(1)
                            val pointStrArray = motionPathString?.split("L".toRegex())?.dropLastWhile { it.isEmpty() }?.toTypedArray()
                            if (pointStrArray != null && pointStrArray.isNotEmpty()) {
                                val points = Array(pointStrArray.size) { FloatArray(2) }
                                for (i in pointStrArray.indices) {
                                    val pointArray = pointStrArray[i].split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                                    points[i][0] = java.lang.Float.parseFloat(pointArray[0])
                                    points[i][1] = java.lang.Float.parseFloat(pointArray[1])
                                }
//                                mContext.mDanmakuFactory.fillLinePathData(item!!, points, mDispScaleX,
//                                        mDispScaleY)
                            }
                        }
                    }
                }
            }
        }

        private fun decodeXmlString(title: String): String {
            var title = title
            if (title.contains("&amp;")) {
                title = title.replace("&amp;", "&")
            }
            if (title.contains("&quot;")) {
                title = title.replace("&quot;", "\"")
            }
            if (title.contains("&gt;")) {
                title = title.replace("&gt;", ">")
            }
            if (title.contains("&lt;")) {
                title = title.replace("&lt;", "<")
            }
            return title
        }


            private val TRUE_STRING:String = "true"

    }


    private fun isPercentageNumber(number: Float): Boolean {
        return number >= 0f && number <= 1f
    }


    override fun setDisplayer(disp: IDisplayer): BaseDanmakuParser {
        super.setDisplayer(disp)
        mDispScaleX = mDispWidth / DanmakuFactory.BILI_PLAYER_WIDTH
        mDispScaleY = mDispHeight / DanmakuFactory.BILI_PLAYER_HEIGHT
        return this
    }

    companion object {

        init {
            System.setProperty("org.xml.sax.driver", "org.xmlpull.v1.sax2.Driver")
        }
    }
}