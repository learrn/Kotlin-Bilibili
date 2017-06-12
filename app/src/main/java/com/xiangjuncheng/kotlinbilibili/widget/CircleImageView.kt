package com.xiangjuncheng.kotlinbilibili.widget

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.util.AttributeSet
import android.widget.ImageView
import com.xiangjuncheng.kotlinbilibili.R


/**
 * Created by xiangjuncheng on 2017/5/23.
 */
class CircleImageView : ImageView {
    companion object {
        private val SCALE_TYPE = ImageView.ScaleType.CENTER_CROP

        private val BITMAP_CONFIG = Bitmap.Config.ARGB_8888

        private val COLORDRAWABLE_DIMENSION = 2

        private val DEFAULT_BORDER_WIDTH = 0

        private val DEFAULT_BORDER_COLOR = Color.BLACK

        private val DEFAULT_BORDER_OVERLAY = false
    }

    private val mDrawableRect = RectF()

    private val mBorderRect = RectF()

    private val mShaderMatrix = Matrix()

    private val mBitmapPaint = Paint()

    private val mBorderPaint = Paint()

    private var mBorderColor = DEFAULT_BORDER_COLOR

    private var mBorderWidth = DEFAULT_BORDER_WIDTH

    private var mBitmap: Bitmap? = null

    private var mBitmapShader: BitmapShader? = null

    private var mBitmapWidth: Int = 0

    private var mBitmapHeight: Int = 0

    private var mDrawableRadius: Float = 0.toFloat()

    private var mBorderRadius: Float = 0.toFloat()

    private var mColorFilter: ColorFilter? = null

    private var mReady: Boolean = false

    private var mSetupPending: Boolean = false

    private var mBorderOverlay: Boolean = false

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs, 0) {
        init()
    }


    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView, defStyle, 0)

        mBorderWidth = a.getDimensionPixelSize(R.styleable.CircleImageView_border_width,
                DEFAULT_BORDER_WIDTH)
        mBorderColor = a.getColor(R.styleable.CircleImageView_border_color, DEFAULT_BORDER_COLOR)
        mBorderOverlay = a.getBoolean(R.styleable.CircleImageView_border_overlay,
                DEFAULT_BORDER_OVERLAY)
        a.recycle()
        init()
    }

    private fun init() {
        super.setScaleType(SCALE_TYPE)
        mReady = true
        if (mSetupPending) {
            setup()
            mSetupPending = false
        }
    }

    override fun getScaleType(): ScaleType {
        return SCALE_TYPE
    }

    override fun setScaleType(scaleType: ScaleType?) {
        if (scaleType != SCALE_TYPE) throw IllegalAccessException(String.format("ScaleType %s not supported.", scaleType))
    }


    override fun setAdjustViewBounds(adjustViewBounds: Boolean) {
        if (adjustViewBounds) throw IllegalAccessException("adjustViewBounds not supported.")
    }

    override fun onDraw(canvas: Canvas?) {
        if (drawable == null) return
        canvas?.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), mDrawableRadius, mBitmapPaint)
        if (mBorderWidth != 0) canvas?.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), mBorderRadius, mBorderPaint)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        setup()
    }

    fun getBorderColor(): Int = mBorderColor

    fun setBorderColor(borderColor: Int) {
        if (borderColor != mBorderColor) {
            mBorderColor = borderColor
            mBitmapPaint.color = mBorderColor
            invalidate()
        }
    }

    fun setBorderColorResource(@ColorRes bordercolorRes: Int) {
        setBorderColor(context.resources.getColor(bordercolorRes))
    }

    fun getBorderWidth(): Int = mBorderWidth

    fun setBorderWidth(borderWidth: Int) {
        if (borderWidth != mBorderWidth) {
            mBorderWidth = borderWidth
            setup()
        }
    }

    fun isBorderOverlay(): Boolean = mBorderOverlay

    fun setBorderOverlay(borderOverlay:Boolean){
        if (borderOverlay != mBorderOverlay) {
            mBorderOverlay = borderOverlay
            setup()
        }
    }

    override fun setImageBitmap(bm: Bitmap) {

        super.setImageBitmap(bm)
        mBitmap = bm
        setup()
    }


    override fun setImageDrawable(drawable: Drawable?) {

        super.setImageDrawable(drawable)
        mBitmap = getBitmapFromDrawable(drawable)
        setup()
    }


    override fun setImageResource(@DrawableRes resId: Int) {

        super.setImageResource(resId)
        mBitmap = getBitmapFromDrawable(drawable)
        setup()
    }


    override fun setImageURI(uri: Uri) {
        super.setImageURI(uri)
        mBitmap = getBitmapFromDrawable(drawable)
        setup()
    }


    override fun setColorFilter(cf: ColorFilter) {
        if (cf === mColorFilter) {
            return
        }
        mColorFilter = cf
        mBitmapPaint.colorFilter = mColorFilter
        invalidate()
    }

    private fun getBitmapFromDrawable(drawable: Drawable?): Bitmap? {

        if (drawable == null) {
            return null
        }

        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }

        try {
            val bitmap: Bitmap
            if (drawable is ColorDrawable) {
                bitmap = Bitmap.createBitmap(COLORDRAWABLE_DIMENSION, COLORDRAWABLE_DIMENSION,
                        BITMAP_CONFIG)
            } else {
                bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight,
                        BITMAP_CONFIG)
            }

            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            return bitmap
        } catch (e: OutOfMemoryError) {
            return null
        }
    }

    private fun setup() {

        if (!mReady) {
            mSetupPending = true
            return
        }

        if (mBitmap == null) {
            return
        }

        mBitmapShader = BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)

        mBitmapPaint.isAntiAlias = true
        mBitmapPaint.shader = mBitmapShader

        mBorderPaint.style = Paint.Style.STROKE
        mBorderPaint.isAntiAlias = true
        mBorderPaint.color = mBorderColor
        mBorderPaint.setStrokeWidth(mBorderWidth.toFloat())

        mBitmapHeight = mBitmap!!.getHeight()
        mBitmapWidth = mBitmap!!.getWidth()

        mBorderRect.set(0.toFloat(), 0.toFloat(), width.toFloat(), height.toFloat())
        mBorderRadius = Math.min((mBorderRect.height() - mBorderWidth) / 2,
                (mBorderRect.width() - mBorderWidth) / 2)

        mDrawableRect.set(mBorderRect)
        if (!mBorderOverlay) {
            mDrawableRect.inset(mBorderWidth.toFloat(), mBorderWidth.toFloat())
        }
        mDrawableRadius = Math.min(mDrawableRect.height() / 2, mDrawableRect.width() / 2)

        updateShaderMatrix()
        invalidate()
    }

    private fun updateShaderMatrix() {

        val scale: Float
        var dx = 0f
        var dy = 0f

        mShaderMatrix.set(null)

        if (mBitmapWidth * mDrawableRect.height() > mDrawableRect.width() * mBitmapHeight) {
            scale = (mDrawableRect.height() / mBitmapHeight) as Float
            dx = (mDrawableRect.width() - mBitmapWidth * scale) * 0.5f
        } else {
            scale = (mDrawableRect.width() / mBitmapWidth) as Float
            dy = (mDrawableRect.height() - mBitmapHeight * scale) * 0.5f
        }

        mShaderMatrix.setScale(scale, scale)
        mShaderMatrix.postTranslate((dx + 0.5f).toInt() + mDrawableRect.left,
                (dy + 0.5f).toInt() + mDrawableRect.top)

        mBitmapShader?.setLocalMatrix(mShaderMatrix)
    }
}