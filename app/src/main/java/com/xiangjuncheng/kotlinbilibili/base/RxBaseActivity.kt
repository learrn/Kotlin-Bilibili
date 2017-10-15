package com.xiangjuncheng.kotlinbilibili.base

import android.app.ActivityManager
import android.os.Bundle
import android.os.PersistableBundle
import android.view.WindowManager
import com.bilibili.magicasakura.utils.ThemeUtils
import com.trello.rxlifecycle.components.support.RxAppCompatActivity
import com.xiangjuncheng.kotlinbilibili.R
import com.xiangjuncheng.kotlinbilibili.utils.ThemeHelper
import com.xiangjuncheng.kotlinbilibili.widget.dialog.CardPickerDialog
import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.Toast


abstract class RxBaseActivity : RxAppCompatActivity(), CardPickerDialog.ClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initViews(savedInstanceState)
        initToolBar()
    }

    abstract fun getLayoutId(): Int
    abstract fun initViews(savedInstanceState: Bundle?)
    abstract fun initToolBar()

    open fun loadData() {}
    fun showProgressBar() {}
    fun hideProgressBar() {}
    fun initRecyclerView() {}
    fun initRefreshLayout() {}
    open fun finishTask() {}

    override fun onConfirm(currentTheme: Int) {
        if (ThemeHelper.getTheme(this@RxBaseActivity) != currentTheme) {
            ThemeHelper.setTheme(this@RxBaseActivity, currentTheme)
            ThemeUtils.refreshUI(this@RxBaseActivity, object : ThemeUtils.ExtraRefreshable {

                override fun refreshGlobal(activity: Activity) {

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        val context = this@RxBaseActivity
                        val taskDescription = ActivityManager.TaskDescription(
                                null, null, ThemeUtils.getThemeAttrColor(context, android.R.attr.colorPrimary))
                        setTaskDescription(taskDescription)
                        window.statusBarColor = ThemeUtils.getColorById(context,
                                R.color.theme_color_primary_dark)
                    }
                }


                override fun refreshSpecificView(view: View) {
                }
            })
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            var window = getWindow()
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = ThemeUtils.getColorById(this, R.color.theme_color_primary_dark)
            val description = ActivityManager.TaskDescription(null, null, ThemeUtils.getThemeAttrColor(this, android.R.attr.colorPrimary))
            setTaskDescription(description)
        }
    }

    fun Context.toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, message, duration).show()
    }
}
