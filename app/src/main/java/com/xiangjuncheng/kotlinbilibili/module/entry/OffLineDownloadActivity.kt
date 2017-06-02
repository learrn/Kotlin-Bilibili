package com.xiangjuncheng.kotlinbilibili.module.entry

import android.os.Bundle
import com.xiangjuncheng.kotlinbilibili.R
import com.xiangjuncheng.kotlinbilibili.base.RxBaseActivity
import com.xiangjuncheng.kotlinbilibili.utils.CommonUtil
import com.xiangjuncheng.kotlinbilibili.widget.progressbar.NumberProgressBar
import kotlinx.android.synthetic.main.activity_offline_download.*

class OffLineDownloadActivity : RxBaseActivity() {
    override fun getLayoutId(): Int = R.layout.activity_offline_download

    override fun initViews(savedInstanceState: Bundle?) {
        val phoneTotalSize = CommonUtil.getPhoneTotalSize()
        val phoneAvailableSize = CommonUtil.getPhoneAvailableSize()
        (progress_bar as NumberProgressBar).setpro
    }

    override fun initToolBar() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
