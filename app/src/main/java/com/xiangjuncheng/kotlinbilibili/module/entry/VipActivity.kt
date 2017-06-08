package com.xiangjuncheng.kotlinbilibili.module.entry

import android.os.Bundle
import android.view.MenuItem
import com.xiangjuncheng.kotlinbilibili.R
import com.xiangjuncheng.kotlinbilibili.base.RxBaseActivity
import com.xiangjuncheng.kotlinbilibili.utils.ConstantUtil
import com.xiangjuncheng.kotlinbilibili.utils.SystemBarHelper
import kotlinx.android.synthetic.main.activity_vip.*

class VipActivity : RxBaseActivity() {
    override fun getLayoutId(): Int = R.layout.activity_vip

    override fun initViews(savedInstanceState: Bundle?) {
        webView.loadUrl(ConstantUtil.VIP_URL)
    }

    override fun initToolBar() {
        toolbar.title = ""
        setSupportActionBar(toolbar)
        if(supportActionBar != null){
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }
        SystemBarHelper.immersiveStatusBar(this)
        SystemBarHelper.setHeightAndPadding(this,toolbar)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home){
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
