package com.xiangjuncheng.kotlinbilibili.media

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import android.widget.TextView

/**
 * Created by xjc on 2017/10/15.
 */
class OutlineTextView : TextView {
    private var mTextPaint: TextPaint? = null
    private var mTextPaintOutline: TextPaint? = null
    private var mText = ""
    private var mBorderSize: Float = 0.toFloat()
    private var mBorderColor: Int = 0
    private var mColor: Int = 0
    private val mSpacingMult = 1.0f
    private val mSpacingAdd = 0f
    private val mIncludePad = true

    constructor(context: Context) : super(context) {
        initPaint()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initPaint()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        initPaint()
    }

    private fun initPaint() {
        mTextPaint = TextPaint()
        mTextPaint!!.isAntiAlias = true
        mTextPaint!!.textSize = textSize
        mTextPaint!!.color = mColor
        mTextPaint!!.style = Paint.Style.FILL
        mTextPaint!!.typeface = typeface
        mTextPaintOutline = TextPaint()
        mTextPaintOutline!!.isAntiAlias = true
        mTextPaintOutline!!.textSize = textSize
        mTextPaintOutline!!.color = mBorderColor
        mTextPaintOutline!!.style = Paint.Style.STROKE
        mTextPaintOutline!!.typeface = typeface
        mTextPaintOutline!!.strokeWidth = mBorderSize
    }


    fun setText(text: String) {
        super.setText(text)
        mText = text
        requestLayout()
        invalidate()
    }

    override fun setTextSize(size: Float) {
        super.setTextSize(size)
        requestLayout()
        invalidate()
        initPaint()
    }

    override fun setTextColor(color: Int) {
        super.setTextColor(color)
        mColor = color
        invalidate()
        initPaint()
    }

    override fun setShadowLayer(radius: Float, dx: Float, dy: Float, color: Int) {
        super.setShadowLayer(radius, dx, dy, color)
        mBorderSize = radius
        mBorderColor = color
        requestLayout()
        invalidate()
        initPaint()
    }

    override fun setTypeface(tf: Typeface, style: Int) {
        super.setTypeface(tf, style)
        requestLayout()
        invalidate()
        initPaint()
    }


    override fun setTypeface(tf: Typeface) {
        super.setTypeface(tf)
        requestLayout()
        invalidate()
        initPaint()
    }


    override fun onDraw(canvas: Canvas) {
        var layout: Layout = StaticLayout(text, mTextPaintOutline,
                width, Layout.Alignment.ALIGN_CENTER, mSpacingMult,
                mSpacingAdd, mIncludePad)
        layout.draw(canvas)
        layout = StaticLayout(text, mTextPaint, width,
                Layout.Alignment.ALIGN_CENTER, mSpacingMult, mSpacingAdd,
                mIncludePad)
        layout.draw(canvas)
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val layout = StaticLayout(text, mTextPaintOutline,
                measureWidth(widthMeasureSpec), Layout.Alignment.ALIGN_CENTER,
                mSpacingMult, mSpacingAdd, mIncludePad)
        val ex = (mBorderSize * 2 + 1).toInt()
        setMeasuredDimension(measureWidth(widthMeasureSpec) + ex,
                measureHeight(heightMeasureSpec) * layout.lineCount + ex)
    }


    private fun measureWidth(measureSpec: Int): Int {
        var result: Int
        val specMode = View.MeasureSpec.getMode(measureSpec)
        val specSize = View.MeasureSpec.getSize(measureSpec)
        if (specMode == View.MeasureSpec.EXACTLY) {
            result = specSize
        } else {
            result = mTextPaintOutline!!.measureText(mText).toInt()
            +paddingLeft + paddingRight
            if (specMode == View.MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize)
            }
        }
        return result
    }


    private fun measureHeight(measureSpec: Int): Int {
        var result = 0
        val specMode = View.MeasureSpec.getMode(measureSpec)
        val specSize = View.MeasureSpec.getSize(measureSpec)
        val mAscent = mTextPaintOutline!!.ascent().toInt()
        if (specMode == View.MeasureSpec.EXACTLY) {
            result = specSize
        } else {
            result = (-mAscent + mTextPaintOutline!!.descent()).toInt()
            +paddingTop + paddingBottom
            if (specMode == View.MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize)
            }
        }
        return result
    }
}