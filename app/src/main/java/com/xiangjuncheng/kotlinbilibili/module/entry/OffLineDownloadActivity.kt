package com.xiangjuncheng.kotlinbilibili.module.entry

import android.os.Bundle
import android.text.format.Formatter
import android.view.Menu
import android.view.MenuItem
import com.xiangjuncheng.kotlinbilibili.R
import com.xiangjuncheng.kotlinbilibili.base.RxBaseActivity
import com.xiangjuncheng.kotlinbilibili.utils.CommonUtil
import com.xiangjuncheng.kotlinbilibili.widget.CustomEmptyView
import com.xiangjuncheng.kotlinbilibili.widget.progressbar.NumberProgressBar
import kotlinx.android.synthetic.main.activity_offline_download.*
import kotlinx.android.synthetic.main.app_bar_main.*

class OffLineDownloadActivity : RxBaseActivity() {
    override fun getLayoutId(): Int = R.layout.activity_offline_download

    override fun initViews(savedInstanceState: Bundle?) {
        val phoneTotalSize = CommonUtil.getPhoneTotalSize()
        val phoneAvailableSize = CommonUtil.getPhoneAvailableSize()
        (progress_bar as NumberProgressBar).setProgress(countProgress(phoneTotalSize, phoneAvailableSize))
        val totalSizeStr = Formatter.formatFileSize(this, phoneTotalSize)
        val availabSizeStr = Formatter.formatFileSize(this, phoneAvailableSize)
        cache_size_text.text = "主存储:$totalSizeStr/可用:$availabSizeStr"
        (empty_layout as CustomEmptyView).setEmptyImage(R.drawable.img_tips_error_no_downloads)
        (empty_layout as CustomEmptyView).setEmptyText("没有找到你的缓存呦")
    }

    override fun initToolBar() {
        toolbar.title = "离线缓存"
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.action_button_back_pressed_light)
        toolbar.setNavigationOnClickListener { v -> finish() }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_recommend, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.menu_more)
            toast("离线设置")
        return super.onOptionsItemSelected(item)
    }

    private fun countProgress(phoneTotalSize: Long, phoneAvailableSize: Long): Int {
        val totalSize: Double = (phoneTotalSize / (1024 * 3)).toDouble()
        val availabsize: Double = (phoneAvailableSize / (1024 * 3)).toDouble()
        val size: Int = (Math.floor(totalSize) - Math.floor(availabsize)).toInt()
        return Math.floor((size / Math.floor(totalSize)) * 100).toInt()
    }
}
