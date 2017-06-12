package com.xiangjuncheng.kotlinbilibili.module.search

import android.os.Bundle
import com.xiangjuncheng.kotlinbilibili.R
import com.xiangjuncheng.kotlinbilibili.base.RxBaseActivity
import android.graphics.drawable.AnimationDrawable
import android.text.TextUtils
import android.view.View
import com.jakewharton.rxbinding.view.RxView
import com.jakewharton.rxbinding.widget.RxTextView
import com.xiangjuncheng.kotlinbilibili.utils.ConstantUtil
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.layout_card_search.*
import rx.android.schedulers.AndroidSchedulers


class TotalStationSearchActivity : RxBaseActivity() {
    private var content: String? = null

    private var mAnimationDrawable: AnimationDrawable? = null

    override fun getLayoutId(): Int = R.layout.activity_search

    override fun initViews(savedInstanceState: Bundle?) {
        if (intent != null) {
            content = intent.getStringExtra(ConstantUtil.EXTRA_CONTENT)
        }
        iv_search_loading.setImageResource(R.drawable.anim_search_loading)
        mAnimationDrawable = iv_search_loading.drawable as AnimationDrawable?
//        showSearchAnim()
        search_edit.clearFocus()
        search_edit.setText(content)
//        getSearchData()
//        search()
        setUpEditText()
    }

    override fun initToolBar() {
    }

    private fun setUpEditText() {
//        RxTextView.textChanges(search_edit)
//                .compose(this.bindToLifecycle())
//                .map(CharSequence::toString)
//                .observeOn(AndroidSchedulers.mainThread())
//
//        }
        RxTextView.textChanges(search_edit)
                .compose(this.bindToLifecycle())
                .map(CharSequence::toString)
                .observeOn(AndroidSchedulers.mainThread())
//        RxView.clicks(search_text_clear).observeOn(AndroidSchedulers.mainThread()).subscribe(aVoid -> {search_edit.text = ""})
    }



}
