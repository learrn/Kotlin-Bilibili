package com.xiangjuncheng.kotlinbilibili.network

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.xiangjuncheng.kotlinbilibili.BilibiliApp
import com.xiangjuncheng.kotlinbilibili.network.api.*
import com.xiangjuncheng.kotlinbilibili.network.auxiliary.ApiConstants
import com.xiangjuncheng.kotlinbilibili.utils.CommonUtil
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit


/**
 * Created by xiangjuncheng on 2017/6/13.
 * Retrofit帮助类
 */
object RetrofitHelper {
    private var mOkHttpClient: OkHttpClient? = null

    init {
        initOkHttpClient()
    }

    fun getLiveAPI(): LiveService = createApi(LiveService::class.java, ApiConstants.LIVE_BASE_URL)

    fun getBiliAppAPI(): BiliAppService =
            createApi(BiliAppService::class.java, ApiConstants.APP_BASE_URL)

    fun getBiliAPI(): BiliApiService =
            createApi(BiliApiService::class.java, ApiConstants.API_BASE_URL)

    fun getBiliGoAPI(): BiliGoService =
            createApi(BiliGoService::class.java, ApiConstants.BILI_GO_BASE_URL)

    fun getRankAPI(): RankService = createApi(RankService::class.java, ApiConstants.RANK_BASE_URL)

    fun getUserAPI(): UserService = createApi(UserService::class.java, ApiConstants.USER_BASE_URL)

    fun getVipAPI(): VipService = createApi(VipService::class.java, ApiConstants.VIP_BASE_URL)

    fun getBangumiAPI(): BangumiService = createApi(BangumiService::class.java, ApiConstants.BANGUMI_BASE_URL)

    fun getSearchAPI(): SearchService = createApi(SearchService::class.java, ApiConstants.SEARCH_BASE_URL)

    fun getAccountAPI(): AccountService = createApi(AccountService::class.java, ApiConstants.ACCOUNT_BASE_URL)

    fun getIm9API(): Im9Service = createApi(Im9Service::class.java, ApiConstants.IM9_BASE_URL)

    /**
     * 根据传入的baseUrl，和api创建retrofit
     */
    private fun <T> createApi(clazz: Class<T>, baseUrl: String): T {

        val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(mOkHttpClient!!)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        return retrofit.create(clazz)
    }


    /**
     * 初始化OKHttpClient,设置缓存,设置超时时间,设置打印日志,设置UA拦截器
     */
    private fun initOkHttpClient() {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        if (mOkHttpClient == null) {
            synchronized(RetrofitHelper::class.java) {
                if (mOkHttpClient == null) {
                    //设置Http缓存
                    val cache = Cache(File(BilibiliApp.instance
                            .cacheDir, "HttpCache"), 1024 * 1024 * 10)

                    mOkHttpClient = OkHttpClient.Builder()
                            .cache(cache)
                            .addInterceptor(interceptor)
                            .addNetworkInterceptor(CacheInterceptor())
                            .addNetworkInterceptor(StethoInterceptor())
                            .retryOnConnectionFailure(true)
                            .connectTimeout(30, TimeUnit.SECONDS)
                            .writeTimeout(20, TimeUnit.SECONDS)
                            .readTimeout(20, TimeUnit.SECONDS)
                            .addInterceptor(UserAgentInterceptor())
                            .build()
                }
            }
        }
    }


    /**
     * 添加UA拦截器，B站请求API需要加上UA才能正常使用
     */
    private class UserAgentInterceptor : Interceptor {

        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {

            val originalRequest = chain.request()
            val requestWithUserAgent = originalRequest.newBuilder()
                    .removeHeader("User-Agent")
                    .addHeader("User-Agent", ApiConstants.COMMON_UA_STR)
                    .build()
            return chain.proceed(requestWithUserAgent)
        }
    }

    /**
     * 为okhttp添加缓存，这里是考虑到服务器不支持缓存时，从而让okhttp支持缓存
     */
    private class CacheInterceptor : Interceptor {

        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {

            // 有网络时 设置缓存超时时间1个小时
            val maxAge = 60 * 60
            // 无网络时，设置超时为1天
            val maxStale = 60 * 60 * 24
            var request = chain.request()
            if (CommonUtil.isNetworkAvailable(BilibiliApp.instance)) {
                //有网络时只从网络获取
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_NETWORK)
                        .build()
            } else {
                //无网络时只从缓存中读取
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build()
            }
            var response = chain.proceed(request)
            response = if (CommonUtil.isNetworkAvailable(BilibiliApp.instance)) {
                response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build()
            } else {
                response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build()
            }
            return response
        }
    }
}