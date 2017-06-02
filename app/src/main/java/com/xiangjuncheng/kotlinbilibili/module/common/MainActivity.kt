package com.xiangjuncheng.kotlinbilibili.module.common

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.AppCompatDelegate
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import com.xiangjuncheng.kotlinbilibili.R
import com.xiangjuncheng.kotlinbilibili.base.RxBaseActivity
import com.xiangjuncheng.kotlinbilibili.module.entry.OffLineDownloadActivity
import com.xiangjuncheng.kotlinbilibili.module.home.HomePageFragment
import com.xiangjuncheng.kotlinbilibili.utils.ConstantUtil
import com.xiangjuncheng.kotlinbilibili.utils.PreferenceUtil
import com.xiangjuncheng.kotlinbilibili.widget.CircleImageView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*


class MainActivity : RxBaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    private val fragments: Array<Fragment>? = null

    private var currentTabIndex: Int = 0

    private var index: Int = 0

    private var exitTime: Long = 0

    private val mHomePageFragment: HomePageFragment? = null

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initViews(savedInstanceState: Bundle?) {
        initFragements()
        initNavigationView()
    }

    override fun initToolBar() {}

    private fun initFragements() {

    }

    private fun initNavigationView() {
        navigationView.setNavigationItemSelectedListener(this)
        val headerView: View = navigationView.getHeaderView(0)
        (user_avatar_view as CircleImageView).setImageResource(R.drawable.ic_hotbitmapgg_avatar)
        headerView.user_name.text = resources.getText(R.string.hotbitmapgg)
        headerView.user_other_info.text = resources.getText(R.string.about_user_head_layout)
        headerView.iv_head_switch_mode.setOnClickListener { switchNightMode() }
    }

    private fun switchNightMode() {
        var isNight: Boolean by PreferenceUtil(name = ConstantUtil.SWITCH_MODE_KEY, default = false)
        if (isNight)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        isNight = !isNight
        recreate()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        drawer_layout.closeDrawer(GravityCompat.START)
        when (item.itemId) {
        // 主页
            R.id.item_home -> changeFragmentIndex(item, 0)
        // 离线缓存
            R.id.item_download -> startActivity(Intent(this@MainActivity, OffLineDownloadActivity::class.java))
        // 大会员
            R.id.item_vip ,//-> startActivity(Intent(this@MainActivity, VipActivity::class.java))
        // 我的收藏
            R.id.item_favourite -> changeFragmentIndex(item, 1)
        // 历史记录
            R.id.item_history -> changeFragmentIndex(item, 2)
        // 关注的人
            R.id.item_group -> changeFragmentIndex(item, 3)
        // 我的钱包
            R.id.item_tracker -> changeFragmentIndex(item, 4)
        // 主题选择
            R.id.item_theme -> {
                // CardPickerDialog dialog = new CardPickerDialog();
                // dialog.setClickListener(this);
                // dialog.show(getSupportFragmentManager(), CardPickerDialog.TAG);
            }
        // 设置中心
            R.id.item_settings -> changeFragmentIndex(item, 5)
            else -> return false

        }
        return true
    }

    /**
     * Fragment切换
     */
    private fun switchFragment() {
        val trx = supportFragmentManager.beginTransaction()
        trx.hide(fragments?.get(currentTabIndex))
        if (!fragments!![index].isAdded()) {
            trx.add(R.id.container, fragments[index])
        }
        trx.show(fragments[index]).commit()
        currentTabIndex = index
    }

    /**
     * 切换fragment下标
     */
    private fun changeFragmentIndex(item: MenuItem, currentIndex: Int) {
        index = currentIndex
        switchFragment()
        item.isChecked = true
    }

    /**
     * DrawerLayout侧滑菜单开关
     */
    fun toggleDrawer() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            drawer_layout.openDrawer(GravityCompat.START)
        }
    }

    /**
     * 监听back键处理DrawerLayout和SearchView
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (drawer_layout.isDrawerOpen(drawer_layout.getChildAt(1))) {
                drawer_layout.closeDrawers()
            } else {
                if (mHomePageFragment != null && mHomePageFragment.isOpenSearchView()) {
                        mHomePageFragment.closeSearchView()
                } else {
                    exitApp()
                }
            }
        }
        return true
    }

    /**
     * 双击退出App
     */
    private fun exitApp() {

        if (System.currentTimeMillis() - exitTime > 2000) {
            toast("再按一次退出")
            exitTime = System.currentTimeMillis()
        } else {
            PreferenceUtil.clearPreference(ConstantUtil.SWITCH_MODE_KEY)
            finish()
        }
    }

    /**
     * 解决App重启后导致Fragment重叠的问题
     */
    override fun onSaveInstanceState(outState: Bundle) {
        //super.onSaveInstanceState(outState);
    }


}
