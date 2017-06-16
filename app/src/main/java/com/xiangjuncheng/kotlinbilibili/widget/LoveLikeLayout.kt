package com.xiangjuncheng.kotlinbilibili.widget

import android.animation.*
import android.content.Context
import android.graphics.PointF
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.view.animation.*
import android.widget.ImageView
import android.widget.RelativeLayout
import com.xiangjuncheng.kotlinbilibili.R
import java.util.*


/**
 * Created by xiangjuncheng on 2017/6/16.
 * 直播送礼物特效自定义控件
 */
class LoveLikeLayout(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : RelativeLayout(context, attrs, defStyle) {
    //线性插值器
    private val line = LinearInterpolator()

    //加速插值器
    private val acc = AccelerateInterpolator()

    //减速插值器
    private val dce = DecelerateInterpolator()

    //先加速后减速插值器
    private val accdec = AccelerateDecelerateInterpolator()

    //插值器数组
    private var interpolators: Array<Interpolator>? = null

    //随机数
    private val mRandom = Random()

    //爱心图片数组
    private var drawables: Array<Drawable>? = null

    //爱心图片的高度
    private var drawableHeight: Int = 0

    //爱心图片的宽度
    private var drawableWidth: Int = 0

    //参数值
    private var layoutParams: RelativeLayout.LayoutParams? = null

    //layout高度
    private var mMeasuredHeight: Int = 0

    //layout宽度
    private var mMeasuredWidth: Int = 0

    init {

        //初始化爱心图片
        val one = resources.getDrawable(R.drawable.ic_live_like_01)
        val two = resources.getDrawable(R.drawable.ic_live_like_02)
        val three = resources.getDrawable(R.drawable.ic_live_like_03)
        val four = resources.getDrawable(R.drawable.ic_live_like_04)
        val five = resources.getDrawable(R.drawable.ic_live_like_05)
        drawables = arrayOf(one, two, three, four, five)

        //获取爱心的宽高
        drawableHeight = one.intrinsicHeight
        drawableWidth = one.intrinsicWidth

        //设置爱心的显示位置
        layoutParams = RelativeLayout.LayoutParams(drawableWidth, drawableHeight)
        layoutParams?.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE)
        layoutParams?.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)

        //初始化动画插值器
        interpolators = arrayOf(line, acc, dce, accdec)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mMeasuredHeight = measuredHeight
        mMeasuredWidth = measuredWidth
    }

    //显示爱心
    fun addLove() {
        val imageView: ImageView = ImageView(context)
        imageView.setImageDrawable(drawables?.get(mRandom.nextInt(5)))
        imageView.layoutParams = layoutParams
        addView(imageView)
        val mAnimtor: Animator = getAnimtor(imageView)
        mAnimtor.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                removeView(imageView)
                super.onAnimationEnd(animation)
            }
        })
        mAnimtor.start()
    }

    /**
     * 爱心的显示和运行轨迹动画组合实现
     */
    private fun getAnimtor(target: View): Animator {
        val enterAnimtorSet = getEnterAnimtorSet(target)
        val bezierAnimtor = getBezierAnimtor(target)
        val animatorSet = AnimatorSet()
        animatorSet.playSequentially(enterAnimtorSet)
        animatorSet.playSequentially(enterAnimtorSet, bezierAnimtor)
        animatorSet.interpolator = interpolators!![mRandom.nextInt(4)]
        animatorSet.setTarget(target)
        return animatorSet
    }

    /**
     * 爱心的显示动画实现
     */
    private fun getEnterAnimtorSet(target: View): AnimatorSet {
        //爱心的3种动画组合 透明度 x，y轴的缩放
        val mAlpha = ObjectAnimator.ofFloat(target, View.ALPHA, 0.2f, 1f)
        val scaleX = ObjectAnimator.ofFloat(target, View.SCALE_X, 0.2f, 1f)
        val scaleY = ObjectAnimator.ofFloat(target, View.SCALE_Y, 0.2f, 1f)

        val set = AnimatorSet()
        set.duration = 500
        set.interpolator = LinearInterpolator()
        set.playTogether(mAlpha, scaleX, scaleY)
        set.setTarget(target)

        return set
    }

    /**
     * 爱心运动轨迹的动画实现
     */
    private fun getBezierAnimtor(target: View): ValueAnimator {
        val evaluator = BezierEvaluator(getPoint(2), getPoint(1))
        val animator = ValueAnimator.ofObject(evaluator, PointF(((mMeasuredWidth - drawableWidth) / 2).toFloat(), (mMeasuredHeight - drawableHeight).toFloat()), PointF(mRandom.nextInt(width).toFloat(), 0F))
        animator.duration = 3000
        animator.setTarget(target)
        animator.addUpdateListener { valueAnimator ->
            //获取贝塞尔曲线的运动轨迹 让爱心跟随着移动
            val animatedValue = valueAnimator.animatedValue as PointF
            target.x = animatedValue.x
            target.y = animatedValue.y
            //增加透明度的变化
            target.alpha = 1 - valueAnimator.animatedFraction
        }

        return animator
    }

    private fun getPoint(scale: Int): PointF {

        val pointF = PointF()
        pointF.x = mRandom.nextInt(measuredWidth - 100).toFloat()
        pointF.y = (mRandom.nextInt(measuredHeight - 100) / scale).toFloat()
        return pointF
    }

    inner class BezierEvaluator(private val pointF1: PointF//途径的两个点
                                , private val pointF2: PointF) : TypeEvaluator<PointF> {
        override fun evaluate(time: Float, startValue: PointF,
                              endValue: PointF): PointF {

            val timeLeft = 1.0f - time
            val point = PointF()//结果

            val point0 = startValue//起点

            val point3 = endValue//终点
            //代入公式
            point.x = timeLeft * timeLeft * timeLeft * point0.x
            +3f * timeLeft * timeLeft * time * pointF1.x
            +3f * timeLeft * time * time * pointF2.x
            +time * time * time * point3.x

            point.y = timeLeft * timeLeft * timeLeft * point0.y
            +3f * timeLeft * timeLeft * time * pointF1.y
            +3f * timeLeft * time * time * pointF2.y
            +time * time * time * point3.y
            return point
        }
    }


}