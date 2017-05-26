package com.xiangjuncheng.kotlinbilibili.widget.progressbar

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.view.View
import android.graphics.RectF
import android.util.AttributeSet
import com.xiangjuncheng.kotlinbilibili.R


/**
 * Created by xiangjuncheng on 2017/5/26.
 */
class NumberProgressBar : View {


    companion object {
        /**
         * For save and restore instance of progressbar.
         */
        private val INSTANCE_STATE = "saved_instance"

        private val INSTANCE_TEXT_COLOR = "text_color"

        private val INSTANCE_TEXT_SIZE = "text_size"

        private val INSTANCE_REACHED_BAR_HEIGHT = "reached_bar_height"

        private val INSTANCE_REACHED_BAR_COLOR = "reached_bar_color"

        private val INSTANCE_UNREACHED_BAR_HEIGHT = "unreached_bar_height"

        private val INSTANCE_UNREACHED_BAR_COLOR = "unreached_bar_color"

        private val INSTANCE_MAX = "max"

        private val INSTANCE_PROGRESS = "progress"

        private val INSTANCE_SUFFIX = "suffix"

        private val INSTANCE_PREFIX = "prefix"

        private val INSTANCE_TEXT_VISIBILITY = "text_visibility"

        private val PROGRESS_TEXT_VISIBLE = 0

    }

    private val mMaxProgress = 100

    /**
     * Current progress, can not exceed the max progress.
     */
    private val mCurrentProgress = 0

    /**
     * The progress area bar color.
     */
    private val mReachedBarColor: Int = 0

    /**
     * The bar unreached area color.
     */
    private val mUnreachedBarColor: Int = 0

    /**
     * The progress text color.
     */
    private val mTextColor: Int = 0

    /**
     * The progress text size.
     */
    private val mTextSize: Float = 0.toFloat()

    /**
     * The height of the reached area.
     */
    private val mReachedBarHeight: Float = 0.toFloat()

    /**
     * The height of the unreached area.
     */
    private val mUnreachedBarHeight: Float = 0.toFloat()

    /**
     * The suffix of the number.
     */
    private val mSuffix = "%"

    /**
     * The prefix.
     */
    private val mPrefix = ""

    private val default_text_color = Color.rgb(66, 145, 241)

    private val default_reached_color = Color.rgb(66, 145, 241)

    private val default_unreached_color = Color.rgb(204, 204, 204)

    private val default_progress_text_offset: Float = 0.toFloat()

    private val default_text_size: Float = 0.toFloat()

    private val default_reached_bar_height: Float = 0.toFloat()

    private val default_unreached_bar_height: Float = 0.toFloat()
    /**
     * The width of the text that to be drawn.
     */
    private val mDrawTextWidth: Float = 0.toFloat()

    /**
     * The drawn text start.
     */
    private val mDrawTextStart: Float = 0.toFloat()

    /**
     * The drawn text end.
     */
    private val mDrawTextEnd: Float = 0.toFloat()

    /**
     * The text that to be drawn in onDraw().
     */
    private val mCurrentDrawText: String? = null

    /**
     * The Paint of the reached area.
     */
    private val mReachedBarPaint: Paint? = null

    /**
     * The Paint of the unreached area.
     */
    private val mUnreachedBarPaint: Paint? = null

    /**
     * The Paint of the progress text.
     */
    private val mTextPaint: Paint? = null

    /**
     * Unreached bar area to draw rect.
     */
    private val mUnreachedRectF = RectF(0f, 0f, 0f, 0f)

    /**
     * Reached bar area rect.
     */
    private val mReachedRectF = RectF(0f, 0f, 0f, 0f)

    /**
     * The progress text offset.
     */
    private val mOffset: Float = 0.toFloat()

    /**
     * Determine if need to draw unreached area.
     */
    private val mDrawUnreachedBar = true

    private val mDrawReachedBar = true

    private val mIfDrawText = true

    /**
     * Listener
     */
    // private val mListener: OnProgressBarListener? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, R.attr.numberProgressBarStyle)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){

    }
}