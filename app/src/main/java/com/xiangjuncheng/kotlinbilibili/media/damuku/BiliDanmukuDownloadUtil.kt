package com.xiangjuncheng.kotlinbilibili.media.damuku

import org.jsoup.Jsoup
import org.jsoup.helper.HttpConnection
import android.text.TextUtils
import master.flame.danmaku.danmaku.loader.ILoader
import master.flame.danmaku.danmaku.loader.IllegalDataException
import master.flame.danmaku.danmaku.loader.android.DanmakuLoaderFactory
import master.flame.danmaku.danmaku.model.IDanmakus
import master.flame.danmaku.danmaku.model.android.Danmakus
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser
import rx.Observable
import rx.schedulers.Schedulers
import java.io.ByteArrayInputStream
import java.io.IOException
import java.util.zip.DataFormatException

/**
 * Created by xjc on 2017/10/15.
 */
object BiliDanmukuDownloadUtil {
    fun downloadXML(uri: String): Observable<BaseDanmakuParser> {
        return Observable.create(Observable.OnSubscribe<BaseDanmakuParser>{ subscriber ->
            if (TextUtils.isEmpty(uri)) {
                subscriber.onNext(object : BaseDanmakuParser() {
                    override fun parse(): IDanmakus {
                        return Danmakus()
                    }
                })
            }
            var loader: ILoader? = null
            try {
                val rsp = Jsoup.connect(uri).timeout(20000).execute() as HttpConnection.Response
                val stream = ByteArrayInputStream(BiliDanmukuCompressionTools.decompressXML(rsp.bodyAsBytes()))
                loader = DanmakuLoaderFactory.create(DanmakuLoaderFactory.TAG_BILI)
                loader!!.load(stream)
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: DataFormatException) {
                e.printStackTrace()
            } catch (e: IllegalDataException) {
                e.printStackTrace()
            }

            val parser = BiliDanmukuParser()
            assert(loader != null)
            val dataSource = loader!!.dataSource
            parser.load(dataSource)
            subscriber.onNext(parser)
        }).subscribeOn(Schedulers.io())
    }
}