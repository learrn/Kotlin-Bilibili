package com.xiangjuncheng.kotlinbilibili.module.home.recommend

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.text.TextUtils
import android.view.View
import com.xiangjuncheng.kotlinbilibili.R
import com.xiangjuncheng.kotlinbilibili.base.RxLazyFragment
import com.xiangjuncheng.kotlinbilibili.entity.recommend.RecommendBannerInfo
import com.xiangjuncheng.kotlinbilibili.entity.recommend.RecommendInfo
import com.xiangjuncheng.kotlinbilibili.network.RetrofitHelper
import com.xiangjuncheng.kotlinbilibili.utils.ConstantUtil
import com.xiangjuncheng.kotlinbilibili.widget.banner.BannerEntity
//import com.xiangjuncheng.kotlinbilibili.widget.sectioned.SectionedRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_home_recommended.*
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


/**
 * Created by xiangjuncheng on 2017/7/4.
 */
object HomeRecommendedFragment : RxLazyFragment() {
    private var mIsRefreshing = false
//    private var mSectionedAdapter: SectionedRecyclerViewAdapter? = null
    private val banners: ArrayList<BannerEntity>? = null
    private val results: ArrayList<RecommendInfo.ResultBean>? = null
    private val recommendBanners: ArrayList<RecommendBannerInfo.DataBean>? = null


    override fun getLayoutResId(): Int = R.layout.fragment_home_recommended

    override fun finishCreateView(state: Bundle?) {
        isPrepared = true
        lazyLoad()
    }

    override fun lazyLoad() {
        if (!isPrepared || !isVisible) {
            return
        }
        initRefreshLayout()
        initRecyclerView()
        isPrepared = false
    }

    override fun initRecyclerView() {
//        mSectionedAdapter = SectionedRecyclerViewAdapter()
        val mLayoutManager = GridLayoutManager(activity, 2)
//        mLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
//            override fun getSpanSize(position: Int): Int {
//                when (mSectionedAdapter.getSectionItemViewType(position)) {
//                    SectionedRecyclerViewAdapter.VIEW_TYPE_HEADER -> return 2
//                    SectionedRecyclerViewAdapter.VIEW_TYPE_FOOTER -> return 2
//                    else -> return 1
//                }
//            }
//        }
        recycle.layoutManager = mLayoutManager
//        recycle.adapter = mSectionedAdapter
        setRecycleNoScroll()
    }

    override fun initRefreshLayout() {
        swipe_refresh_layout.setColorSchemeResources(R.color.colorPrimary)
        swipe_refresh_layout.post {
            swipe_refresh_layout.isRefreshing = true
            mIsRefreshing = true
            loadData()
        }
        swipe_refresh_layout.setOnRefreshListener {
            clearData()
            loadData()
        }
    }

    override fun loadData() {
        RetrofitHelper.getBiliAppAPI()
                .recommendedBannerInfo
                .compose(bindToLifecycle())
                .map( { i: RecommendBannerInfo -> i.data } )
                .flatMap({ dataBeans ->
                    dataBeans?.let { recommendBanners?.addAll(it)}
                    RetrofitHelper.getBiliAppAPI().recommendedInfo
                })
                .compose(bindToLifecycle())
                .map({ { i: RecommendInfo -> i.result } })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ resultBeans ->
                    run {
                        results?.addAll(resultBeans as ArrayList<RecommendInfo.ResultBean>)
                        finishTask()
                    }
                }, { initEmptyView() })
    }

    /**
     * 设置轮播banners
     */
    private fun convertBanner() {
        Observable.from(recommendBanners)
                .compose(bindToLifecycle())
                .forEach { dataBean ->
                    banners?.add(BannerEntity(dataBean.value,
                            dataBean.title, dataBean.image))
                }
    }

//    override fun finishTask() {
//        swipe_refresh_layout.isRefreshing = false
//        mIsRefreshing = false
//        hideEmptyView()
//        convertBanner()
//        mSectionedAdapter.addSection(HomeRecommendBannerSection(banners))
//        val size = results?.size
//        for (i in 0..(size - 1)) {
//            val type = results?.get(i)?.type
//            if (!TextUtils.isEmpty(type)) {
//                when (type) {
//                    ConstantUtil.TYPE_TOPIC ->
//                        //话题
//                        mSectionedAdapter.addSection(HomeRecommendTopicSection(activity,
//                                results?.get(i)?.body?.get(0)?.cover,
//                                results?.get(i)?.body?.get(0)?.title,
//                                results?.get(i)?.body?.get(0)?.param))
//                    ConstantUtil.TYPE_ACTIVITY_CENTER ->
//                        //活动中心
//                        mSectionedAdapter.addSection(HomeRecommendActivityCenterSection(
//                                activity,
//                                results?.get(i)?.body))
//                    else -> mSectionedAdapter.addSection(HomeRecommendedSection(
//                            activity,
//                            results?.get(i)?.head?.title,
//                            results?.get(i)?.type,
//                            results?.get(1)?.head?.count,
//                            results?.get(i)?.body))
//                }
//            }
//            val style = results?.get(i)?.head?.style
//            if (style == ConstantUtil.STYLE_PIC) {
//                mSectionedAdapter.addSection(HomeRecommendPicSection(activity,
//                        results?.get(i)?.body?.get(0)?.cover,
//                        results?.get(i)?.body?.get(0)?.param))
//            }
//        }
//        mSectionedAdapter?.notifyDataSetChanged()
//    }

    fun initEmptyView() {
        swipe_refresh_layout.isRefreshing = false
        empty_layout.visibility = View.VISIBLE
        recycle.visibility = View.GONE
        empty_layout.setEmptyImage(R.drawable.img_tips_error_load_error)
        empty_layout.setEmptyText("加载失败~(≧▽≦)~啦啦啦.")
//        SnackbarUtil.showMessage(recycle, "数据加载失败,请重新加载或者检查网络是否链接")
    }


    fun hideEmptyView() {
        empty_layout.visibility = View.GONE
        recycle.visibility = View.VISIBLE
    }


    private fun clearData() {
        banners?.clear()
        recommendBanners?.clear()
        results?.clear()
        mIsRefreshing = true
//        mSectionedAdapter.removeAllSections()
    }


    private fun setRecycleNoScroll() {
        recycle.setOnTouchListener({ v, event -> mIsRefreshing })
    }

}