package com.xiangjuncheng.kotlinbilibili.widget.progressbar

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
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
    private var mReachedBarColor: Int = 0

    /**
     * The bar unreached area color.
     */
    private var mUnreachedBarColor: Int = 0

    /**
     * The progress text color.
     */
    private var mTextColor: Int = 0

    /**
     * The progress text size.
     */
    private var mTextSize: Float = 0.toFloat()

    /**
     * The height of the reached area.
     */
    private var mReachedBarHeight: Float = 0.toFloat()

    /**
     * The height of the unreached area.
     */
    private var mUnreachedBarHeight: Float = 0.toFloat()

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

    private var default_progress_text_offset: Float = 0.toFloat()

    private var default_text_size: Float = 0.toFloat()

    private var default_reached_bar_height: Float = 0.toFloat()

    private var default_unreached_bar_height: Float = 0.toFloat()
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
    private var mOffset: Float = 0.toFloat()

    /**
     * Determine if need to draw unreached area.
     */
    private val mDrawUnreachedBar = true

    private val mDrawReachedBar = true

    private var mIfDrawText = true

    /**
     * Listener
     */
    // private val mListener: OnProgressBarListener? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, R.attr.numberProgressBarStyle)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){
        default_reached_bar_height = dp2px(1.5f)
        default_unreached_bar_height = dp2px(1.0f)
        default_text_size = sp2px(10f)
        default_progress_text_offset = dp2px(3.0f)

        val attributes = context.theme.obtainStyledAttributes(attrs,R.styleable.NumberProgressBar,defStyleAttr,0)
        mReachedBarColor = attributes.getColor(R.styleable.NumberProgressBar_progress_reached_color,default_reached_color)
        mUnreachedBarColor = attributes.getColor(R.styleable.NumberProgressBar_progress_unreached_color,default_unreached_color)
        mTextColor = attributes.getColor(R.styleable.NumberProgressBar_progress_text_color,default_text_color)
        mTextSize = attributes.getDimension(R.styleable.NumberProgressBar_progress_text_size,default_text_size)
        mReachedBarHeight = attributes.getDimension(R.styleable.NumberProgressBar_progress_reached_bar_height,default_reached_bar_height)
        mUnreachedBarHeight = attributes.getDimension(R.styleable.NumberProgressBar_progress_unreached_bar_height,default_unreached_bar_height)
        mOffset = attributes.getDimension(R.styleable.NumberProgressBar_progress_text_offset,default_progress_text_offset)

        if (PROGRESS_TEXT_VISIBLE != attributes.getInt(R.styleable.NumberProgressBar_progress_text_visibility, PROGRESS_TEXT_VISIBLE)){
            mIfDrawText = false
        }
        setProgress(attributes.getInt(R.styleable.NumberProgressBar_progress_current,0))
        setMax(attributes.getInt(R.styleable.NumberProgressBar_progress_max,100))

        attributes.recycle()
        initializePainters()
    }

    override fun getSuggestedMinimumHeight(): Int {
        return mTextSize.toInt()
    }

    override fun getSuggestedMinimumWidth(): Int {
        return Math.max(mTextSize.toInt(),Math.max(mReachedBarHeight.toInt(),mUnreachedBarHeight.toInt()))
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(measure(widthMeasureSpec,true),measure(heightMeasureSpec,false))
    }

    fun dp2px(dp: Float): Float {
        val scale = resources.displayMetrics.density
        return dp * scale + 0.5f
    }

    fun sp2px(sp: Float): Float {
        val scale = resources.displayMetrics.scaledDensity
        return sp * scale
    }
}