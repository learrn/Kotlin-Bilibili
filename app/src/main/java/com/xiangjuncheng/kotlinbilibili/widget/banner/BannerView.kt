package com.xiangjuncheng.kotlinbilibili.widget.banner

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import com.xiangjuncheng.kotlinbilibili.R
import kotlinx.android.synthetic.main.layout_custom_banner.view.*
import rx.subscriptions.CompositeSubscription
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.xiangjuncheng.kotlinbilibili.utils.DisplayUtil
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.xml.datatype.DatatypeConstants.SECONDS
import rx.internal.operators.OperatorReplay.observeOn
import rx.Subscription
import java.util.concurrent.TimeUnit


/**
 * Created by xiangjuncheng on 2017/6/13.
 */
class BannerView(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RelativeLayout(context, attrs, defStyleAttr), BannerAdapter.ViewPagerOnItemClickListener {
    private var compositeSubscription: CompositeSubscription? = null

    //默认轮播时间，10s
    private var delayTime = 10

    private val imageViewList: ArrayList<ImageView>? = null

    private var bannerList: ArrayList<BannerEntity>? = null

    //选中显示Indicator
    private var selectRes = R.drawable.shape_dots_select

    //非选中显示Indicator
    private var unSelcetRes = R.drawable.shape_dots_default

    //当前页的下标
    private var currrentPos: Int = 0

    private var isStopScroll = false

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_custom_banner, this, true)
    }

    /**
     * 设置轮播间隔时间
     *
     * @param time 轮播间隔时间，单位秒
     */
    fun delayTime(time: Int): BannerView {
        delayTime = time
        return this
    }

    /**
     * 设置Points资源 Res

     * @param selectRes 选中状态
     * *
     * @param unselcetRes 非选中状态
     */
    fun setPointsRes(selectRes: Int, unselcetRes: Int) {
        this.selectRes = selectRes
        this.unSelcetRes = unselcetRes
    }

    /**
     * 图片轮播需要传入参数
     */
    fun build(list: List<BannerEntity>) {
        destory()
        if (list.size == 0) {
            this.visibility = View.GONE
            return
        }

        bannerList = ArrayList()
        bannerList?.addAll(list)
        val pointSize: Int? = bannerList?.size
        if (pointSize == 2){
            bannerList?.addAll(list)
        }
        if (layout_banner_points_group.childCount != 0){
            layout_banner_points_group.removeAllViewsInLayout()
        }
        if (pointSize != null && bannerList != null) {
            for (i in 0..pointSize - 1) {
                val dot = View(context)
                dot.setBackgroundResource(unSelcetRes)
                val params = LinearLayout.LayoutParams(
                        DisplayUtil.dp2px(context, 5F),
                        DisplayUtil.dp2px(context, 5F))
                params.leftMargin = 10
                dot.layoutParams = params
                dot.isEnabled = false
                layout_banner_points_group.addView(dot)
            }
            layout_banner_points_group.getChildAt(0).setBackgroundResource(selectRes)
            for (i in 0..bannerList!!.size - 1) {
                val mImageView = ImageView(context)

                Glide.with(context)
                        .load(bannerList!![i].img)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.bili_default_image_tv)
                        .dontAnimate()
                        .into(mImageView)
                imageViewList?.add(mImageView)
            }
            layout_banner_viewpager.clearOnPageChangeListeners()
            layout_banner_viewpager.addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
                override fun onPageScrollStateChanged(state: Int) {
                    when(state){
                        ViewPager.SCROLL_STATE_IDLE->{
                            if (isStopScroll){
                                startScroll()
                            }
                        }
                        ViewPager.SCROLL_STATE_DRAGGING->{
                            stopScroll()
                            compositeSubscription?.unsubscribe()
                        }
                    }
                }

                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                }

                override fun onPageSelected(position: Int) {
                    val pos = position % pointSize
                    currrentPos = pos
                    for (i in 0..layout_banner_points_group.childCount - 1) {
                        layout_banner_points_group.getChildAt(i).setBackgroundResource(unSelcetRes)
                    }
                    layout_banner_points_group.getChildAt(pos).setBackgroundResource(selectRes)
                }

            })
            val bannerAdapter = BannerAdapter(imageViewList!!)
            layout_banner_viewpager.adapter = bannerAdapter
            bannerAdapter.notifyDataSetChanged()
            bannerAdapter.setmViewPagerOnItemClickListener(this)

            //图片开始轮播
            startScroll()
        }

    }

    /**
     * 图片开始轮播
     */
    private fun startScroll() {

        compositeSubscription = CompositeSubscription()
        isStopScroll = false
        val subscription = Observable.timer(delayTime.toLong(), TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ aLong ->
                    if (isStopScroll) {
                        return@subscribe
                    }
                    isStopScroll = true
                    layout_banner_viewpager.currentItem = layout_banner_viewpager.getCurrentItem() + 1
                })
        compositeSubscription?.add(subscription)
    }

    /**
     * 图片停止轮播
     */
    private fun stopScroll() {
        isStopScroll = true
    }


    fun destory() {
        compositeSubscription?.unsubscribe()
    }


    override fun onItemClick() {

    }

}