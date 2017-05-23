package com.xiangjuncheng.kotlinbilibili.widget.dialog

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.xiangjuncheng.kotlinbilibili.R
import com.xiangjuncheng.kotlinbilibili.utils.ThemeHelper


/**
 * Created by xiangjuncheng on 2017/5/23.
 */
class CardPickerDialog : DialogFragment(), View.OnClickListener {
    var mCards = arrayOfNulls<ImageView>(8)
    var mConfirm: Button? = null
    var mCancel: Button? = null
    private var mCurrentTheme: Int = 0
    private var mClickListener: ClickListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppTheme_AppCompat_Dialog_Alert)
        mCurrentTheme = ThemeHelper.getTheme(activity)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_theme_picker, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mCancel = view.findViewById(android.R.id.button2) as Button
        mCards[0] = view.findViewById(R.id.theme_pink) as ImageView
        mCards[1] = view.findViewById(R.id.theme_purple) as ImageView
        mCards[2] = view.findViewById(R.id.theme_blue) as ImageView
        mCards[3] = view.findViewById(R.id.theme_green) as ImageView
        mCards[4] = view.findViewById(R.id.theme_green_light) as ImageView
        mCards[5] = view.findViewById(R.id.theme_yellow) as ImageView
        mCards[6] = view.findViewById(R.id.theme_orange) as ImageView
        mCards[7] = view.findViewById(R.id.theme_red) as ImageView
        setImageButtons(mCurrentTheme)
        for (card in mCards) {
            card?.setOnClickListener(this)
        }
        mCancel?.setOnClickListener(this)
        mConfirm?.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            android.R.id.button1 -> {
                (mClickListener)?.onConfirm(mCurrentTheme)
                dismiss()
            }
            android.R.id.button2 -> dismiss()
            R.id.theme_pink -> {
                mCurrentTheme = ThemeHelper.CRAD_SAKURA
                setImageButtons(mCurrentTheme)
            }
            R.id.theme_purple -> {
                mCurrentTheme = ThemeHelper.CARD_HOPE
                setImageButtons(mCurrentTheme)
            }
            R.id.theme_blue -> {
                mCurrentTheme = ThemeHelper.CARD_STORM
                setImageButtons(mCurrentTheme)
            }
            R.id.theme_green -> {
                mCurrentTheme = ThemeHelper.CARD_WOOD
                setImageButtons(mCurrentTheme)
            }
            R.id.theme_green_light -> {
                mCurrentTheme = ThemeHelper.CARD_LIGHT
                setImageButtons(mCurrentTheme)
            }
            R.id.theme_yellow -> {
                mCurrentTheme = ThemeHelper.CARD_THUNDER
                setImageButtons(mCurrentTheme)
            }
            R.id.theme_orange -> {
                mCurrentTheme = ThemeHelper.CARD_SAND
                setImageButtons(mCurrentTheme)
            }
            R.id.theme_red -> {
                mCurrentTheme = ThemeHelper.CARD_FIREY
                setImageButtons(mCurrentTheme)
            }
            else -> {
            }
        }
    }

    private fun setImageButtons(currentTheme: Int) {

        mCards[0]?.isSelected = currentTheme == ThemeHelper.CRAD_SAKURA
        mCards[1]?.isSelected = currentTheme == ThemeHelper.CARD_HOPE
        mCards[2]?.isSelected = currentTheme == ThemeHelper.CARD_STORM
        mCards[3]?.isSelected = currentTheme == ThemeHelper.CARD_WOOD
        mCards[4]?.isSelected = currentTheme == ThemeHelper.CARD_LIGHT
        mCards[5]?.isSelected = currentTheme == ThemeHelper.CARD_THUNDER
        mCards[6]?.isSelected = currentTheme == ThemeHelper.CARD_SAND
        mCards[7]?.isSelected = currentTheme == ThemeHelper.CARD_FIREY
    }

    interface ClickListener {
        fun onConfirm(currentTheme: Int)
    }

}