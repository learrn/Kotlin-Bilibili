package com.xiangjuncheng.kotlinbilibili.module.user

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.xiangjuncheng.kotlinbilibili.R
import com.xiangjuncheng.kotlinbilibili.base.RxBaseActivity
import com.xiangjuncheng.kotlinbilibili.entity.user.*
import com.xiangjuncheng.kotlinbilibili.network.RetrofitHelper
import com.xiangjuncheng.kotlinbilibili.utils.SystemBarHelper
import com.xiangjuncheng.kotlinbilibili.widget.CircleImageView
import kotlinx.android.synthetic.main.activity_user_info.*
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action0
import rx.schedulers.Schedulers


class UserInfoDetailsActivity : RxBaseActivity() {

    private var name = ""

    private var mid: Int = 0

    private var avatar_url: String? = null

    private var mUserDetailsInfo: UserDetailsInfo? = null

    private val titles = ArrayList<String>()

    private val fragments = ArrayList<Fragment>()

    private var userContributeCount: Int = 0

    private var userFavoritesCount: Int = 0

    private var userChaseBangumiCount: Int = 0

    private var userInterestQuanCount: Int = 0

    private var userCoinsCount: Int = 0

    private var userPlayGameCount: Int = 0

    private var mUserContributeInfo: UserContributeInfo? = null

    private var mUserFavoritesInfo: UserFavoritesInfo? = null

    private var mUserChaseBangumiInfo: UserChaseBangumiInfo? = null

    private var mUserInterestQuanInfo: UserInterestQuanInfo? = null

    private var mUserCoinsInfo: UserCoinsInfo? = null

    private var mUserPlayGameInfo: UserPlayGameInfo? = null

    private var mUserLiveRoomStatusInfo: UserLiveRoomStatusInfo? = null

//    private val userContributes: ArrayList<UserContributeInfo.DataBean.VlistBean>()

    private val userCoins = ArrayList<UserCoinsInfo.DataBean.ListBean>()

    private val userFavorites = ArrayList<UserFavoritesInfo.DataBean>()

    private val userChaseBangumis = ArrayList<UserChaseBangumiInfo.DataBean.ResultBean>()

    private val userInterestQuans = ArrayList<UserInterestQuanInfo.DataBean.ResultBean>()

    private val userPlayGames = ArrayList<UserPlayGameInfo.DataBean.GamesBean>()

    private val EXTRA_USER_NAME = "extra_user_name"
    private val EXTRA_MID = "extra_mid"
    private val EXTRA_AVATAR_URL = "extra_avatar_url"

    companion object {
        private val EXTRA_USER_NAME = "extra_user_name"
        private val EXTRA_MID = "extra_mid"
        private val EXTRA_AVATAR_URL = "extra_avatar_url"
        fun launch(activity: Activity, name: String?, mid: Int?, avatar_url: String?) {
            val intent = Intent(activity, UserInfoDetailsActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra(EXTRA_USER_NAME, name)
            intent.putExtra(EXTRA_MID, mid)
            intent.putExtra(EXTRA_AVATAR_URL, avatar_url)
            activity.startActivity(intent)
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_user_info

    override fun initViews(savedInstanceState: Bundle?) {
        if (intent != null) {
            name = intent.getStringExtra(EXTRA_USER_NAME)
            mid = intent.getIntExtra(EXTRA_MID, -1)
            avatar_url = intent.getStringExtra(EXTRA_AVATAR_URL)
        }

        user_name.text = name
        if (avatar_url != null) {
            Glide.with(this@UserInfoDetailsActivity)
                    .load(avatar_url)
                    .centerCrop()
                    .dontAnimate()
                    .placeholder(R.drawable.ico_user_default)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(user_avatar_view as CircleImageView)
        }
        getUserInfo()
        view_pager.visibility = View.INVISIBLE
    }

    override fun initToolBar() {
        toolbar.title = ""
        setSupportActionBar(toolbar)
        val supportActionBar = supportActionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //设置StatusBar透明
        SystemBarHelper.immersiveStatusBar(this)
        SystemBarHelper.setHeightAndPadding(this, toolbar)

        //设置AppBar展开折叠状态监听
//        app_bar_layout.addOnOffsetChangedListener(object : AppBarStateChangeEvent() {
//
//            fun onStateChanged(appBarLayout: AppBarLayout, state: State, verticalOffset: Int) {
//
//                if (state === State.EXPANDED) {
//                    //展开状态
//                    mCollapsingToolbarLayout.setTitle("")
//                    mLineView.setVisibility(View.VISIBLE)
//                } else if (state === State.COLLAPSED) {
//                    //折叠状态
//                    mCollapsingToolbarLayout.setTitle(name)
//                    mLineView.setVisibility(View.GONE)
//                } else {
//                    mCollapsingToolbarLayout.setTitle("")
//                    mLineView.setVisibility(View.VISIBLE)
//                }
//            }
//        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId === android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    fun getUserInfo() {

        RetrofitHelper.getAccountAPI()
                .getUserInfoById(mid)
                .compose(this.bindToLifecycle<Any>())
                .doOnSubscribe(Action0 { this.showProgressBar() })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ userInfo ->
                    mUserDetailsInfo = userInfo as UserDetailsInfo?
                    finishTask()
                }) { throwable -> hideProgressBar() }
    }

    override fun finishTask() {
        //设置用户头像
        Glide.with(this@UserInfoDetailsActivity)
                .load(mUserDetailsInfo?.card?.face)
                .centerCrop()
                .dontAnimate()
                .placeholder(R.drawable.ico_user_default)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(user_avatar_view as CircleImageView)

        //设置粉丝和关注
        user_name.text = mUserDetailsInfo?.card?.name
        tv_follow_users.text = mUserDetailsInfo?.card?.attention.toString()
//        tv_fans.setText(NumberUtil.converString(mUserDetailsInfo?.card?.fans))

        //设置用户等级
//        setUserLevel(Integer.valueOf(mUserDetailsInfo?.card?.rank))

        //设置用户性别
        when (mUserDetailsInfo?.card?.sex) {
            "男" -> user_sex.setImageResource(R.drawable.ic_user_male)
            "女" -> user_sex.setImageResource(R.drawable.ic_user_female)
            else -> user_sex.setImageResource(R.drawable.ic_user_gay_border)
        }

        //设置用户签名信息
        if (!TextUtils.isEmpty(mUserDetailsInfo?.card?.sign)) {
            user_desc.text = mUserDetailsInfo?.card?.sign
        } else {
            user_desc.text = "这个人懒死了,什么都没有写(・－・。)"
        }

        //设置认证用户信息
        if (mUserDetailsInfo?.card?.isApprove!!) {
            //认证用户 显示认证资料
            author_verified_layout.visibility = View.VISIBLE
            if (!TextUtils.isEmpty(mUserDetailsInfo?.card?.description)) {
                author_verified_text.text = mUserDetailsInfo?.card?.description
            } else {
                author_verified_text.text = "这个人懒死了,什么都没有写(・－・。)"
            }
        } else {
            //普通用户
            author_verified_layout.visibility = View.GONE
        }

        //获取用户详情全部数据
        getUserAllData()
    }

    private fun getUserAllData() {

        RetrofitHelper.getUserAPI()
                .getUserContributeVideos(mid, 1, 10)
//                .compose(this.bindToLifecycle<Any>())
//                .flatMap({
//                    userContributeInfo ->
//                    mUserContributeInfo = userContributeInfo as UserContributeInfo
//                    userContributeCount = userContributeInfo.data!!.count
//                    userContributeInfo.data!!.vlist?.let { userContributes.addAll(it) }
//                    RetrofitHelper.getBiliAPI().getUserFavorites(mid)
//                })
                .compose(bindToLifecycle<Any>())
                .flatMap({ userFavoritesInfo ->
                    mUserFavoritesInfo = userFavoritesInfo as UserFavoritesInfo?
                    userFavoritesCount = userFavoritesInfo?.data!!.size
                    userFavorites.addAll(userFavoritesInfo?.data!!)
                    RetrofitHelper.getUserAPI().getUserChaseBangumis(mid)
                })
                .compose(bindToLifecycle<Any>())
                .flatMap({ userChaseBangumiInfo ->
                    mUserChaseBangumiInfo = userChaseBangumiInfo as UserChaseBangumiInfo?
                    userChaseBangumiCount = userChaseBangumiInfo?.data!!.count
                    userChaseBangumiInfo?.data?.result?.let { userChaseBangumis.addAll(it) }
                    RetrofitHelper.getIm9API().getUserInterestQuanData(mid, 1, 10)
                })
                .compose(bindToLifecycle<Any>())
                .flatMap({ userInterestQuanInfo ->
                    mUserInterestQuanInfo = userInterestQuanInfo as UserInterestQuanInfo?
                    userInterestQuanCount = userInterestQuanInfo?.data?.total_count!!
                    userInterestQuanInfo?.data?.result?.let { userInterestQuans.addAll(it) }
                    RetrofitHelper.getUserAPI().getUserCoinVideos(mid)
                })
                .compose(bindToLifecycle<Any>())
                .flatMap({ userCoinsInfo ->
                    mUserCoinsInfo = userCoinsInfo as UserCoinsInfo?
                    userCoinsCount = userCoinsInfo?.data!!.count
                    userCoinsInfo?.data!!.list?.let { userCoins.addAll(it) }
                    RetrofitHelper.getUserAPI().getUserPlayGames(mid)
                })
                .compose(bindToLifecycle<Any>())
                .flatMap({ userPlayGameInfo ->
                    mUserPlayGameInfo = userPlayGameInfo as UserPlayGameInfo?
                    userPlayGameCount = userPlayGameInfo?.data!!.count
                    userPlayGameInfo?.data!!.games?.let { userPlayGames.addAll(it) }
                    RetrofitHelper.getLiveAPI().getUserLiveRoomStatus(mid)
                })
                .compose(bindToLifecycle<Any>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ userLiveRoomStatusInfo ->
                    mUserLiveRoomStatusInfo = userLiveRoomStatusInfo as UserLiveRoomStatusInfo?
//                    initViewPager()
                }, { hideProgressBar() })
    }

}
