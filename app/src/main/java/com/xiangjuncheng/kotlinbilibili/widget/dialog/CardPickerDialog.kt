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
import kotlinx.android.synthetic.main.dialog_theme_picker.*


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
        mCancel = button2
        mCards[0] = theme_pink
        mCards[1] = theme_purple
        mCards[2] = theme_blue
        mCards[3] = theme_green
        mCards[4] = theme_green_light
        mCards[5] = theme_yellow
        mCards[6] = theme_orange
        mCards[7] = theme_red
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

    fun setClickListener(clickListener: ClickListener) {
        mClickListener = clickListener
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