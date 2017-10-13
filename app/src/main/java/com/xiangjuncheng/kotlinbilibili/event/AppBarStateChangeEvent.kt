package com.xiangjuncheng.kotlinbilibili.event

import android.support.design.widget.AppBarLayout

/**
 * Created by xiangjuncheng on 2017/10/13.
 */
abstract class AppBarStateChangeEvent : AppBarLayout.OnOffsetChangedListener {

    private var mCurrentState = State.IDLE

    enum class State {
        EXPANDED,
        COLLAPSED,
        IDLE
    }


    abstract fun onStateChanged(appBarLayout: AppBarLayout, state: State, verticalOffset: Int)


    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        when {
            verticalOffset == 0 -> {
                if (mCurrentState != State.EXPANDED) {
                    onStateChanged(appBarLayout, State.EXPANDED, verticalOffset)
                }
                mCurrentState = State.EXPANDED
            }
            Math.abs(verticalOffset) >= appBarLayout.totalScrollRange -> {
                if (mCurrentState != State.COLLAPSED) {
                    onStateChanged(appBarLayout, State.COLLAPSED, verticalOffset)
                }
                mCurrentState = State.COLLAPSED
            }
            else -> {
                if (mCurrentState != State.IDLE) {
                    onStateChanged(appBarLayout, State.IDLE, verticalOffset)
                }
                mCurrentState = State.IDLE
            }
        }
    }
}
