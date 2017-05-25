package com.xiangjuncheng.kotlinbilibili.module.common

import android.content.Intent
import android.os.Bundle
import com.trello.rxlifecycle.components.RxActivity
import com.xiangjuncheng.kotlinbilibili.R
import com.xiangjuncheng.kotlinbilibili.utils.ConstantUtil
import com.xiangjuncheng.kotlinbilibili.utils.PreferenceUtil
import com.xiangjuncheng.kotlinbilibili.utils.SystemUiVisibilityUtil
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

class SplashActivity : RxActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        SystemUiVisibilityUtil.hideStatusBar(window, true)
        setUpSplash()
    }

    private fun setUpSplash() {
        Observable.timer(2000, TimeUnit.MILLISECONDS)
                .compose(bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .map { finishtask() }
                .subscribe()
    }

    private fun finishtask() {
        val isLogin: Boolean by PreferenceUtil(name = ConstantUtil.KEY, default = false)
        if (isLogin) {
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        } else {
            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
        }
        this@SplashActivity.finish()
    }
}
