package com.xiangjuncheng.kotlinbilibili.module.common

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import com.xiangjuncheng.kotlinbilibili.R
import com.xiangjuncheng.kotlinbilibili.base.RxBaseActivity
import com.xiangjuncheng.kotlinbilibili.utils.CommonUtil
import com.xiangjuncheng.kotlinbilibili.utils.ConstantUtil
import com.xiangjuncheng.kotlinbilibili.utils.PreferenceUtil
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : RxBaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun initViews(savedInstanceState: Bundle?) {
        et_username.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus && et_username.text.isNotEmpty()) {
                delete_username.visibility = View.VISIBLE
            } else {
                delete_username.visibility = View.GONE
            }

            iv_icon_left.setImageResource(R.drawable.ic_22)
            iv_icon_right.setImageResource(R.drawable.ic_33)
        }

        et_password.setOnFocusChangeListener { v, hasFocus ->
            iv_icon_left.setImageResource(R.drawable.ic_22_hide)
            iv_icon_right.setImageResource(R.drawable.ic_33_hide)
        }

        et_username.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                et_password.text = null
                if (s?.length!! > 0) delete_username.visibility = View.VISIBLE else delete_username.visibility = View.GONE
            }

        })
        delete_username.setOnClickListener {
            et_username.setText("")
            et_password.setText("")
            delete_username.visibility = View.GONE
            et_username.isFocusable = true
            et_username.isFocusableInTouchMode = true
            et_username.requestFocus()
        }

        btn_login.setOnClickListener {
            if (!CommonUtil.isNetworkAvailable(this)) toast("当前网络不可用,请检查网络设置") else login()
        }
    }

    private fun login() {

        val name = et_username.text.toString()
        val password = et_password.text.toString()

        if (TextUtils.isEmpty(name)) {
            toast("账号不能为空")
            return
        }

        if (TextUtils.isEmpty(password)) {
            toast("密码不能为空")
            return
        }

        PreferenceUtil(name = ConstantUtil.KEY, default = true)
        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        finish()
    }

    override fun initToolBar() {
        toolbar.setNavigationIcon(R.drawable.ic_cancle)
        toolbar.title = "登录"
        toolbar.setNavigationOnClickListener({ v -> finish() })
    }
}
