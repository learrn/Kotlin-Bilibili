package com.xiangjuncheng.kotlinbilibili.module.user

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.xiangjuncheng.kotlinbilibili.R
import com.xiangjuncheng.kotlinbilibili.base.RxBaseActivity
import com.xiangjuncheng.kotlinbilibili.entity.user.UserPlayGameInfo
import com.xiangjuncheng.kotlinbilibili.entity.user.UserInterestQuanInfo
import com.xiangjuncheng.kotlinbilibili.entity.user.UserChaseBangumiInfo
import com.xiangjuncheng.kotlinbilibili.entity.user.UserFavoritesInfo
import com.xiangjuncheng.kotlinbilibili.entity.user.UserCoinsInfo
import com.xiangjuncheng.kotlinbilibili.entity.user.UserContributeInfo
import com.xiangjuncheng.kotlinbilibili.entity.user.UserLiveRoomStatusInfo
import com.xiangjuncheng.kotlinbilibili.entity.user.UserDetailsInfo
import com.xiangjuncheng.kotlinbilibili.widget.CircleImageView
import kotlinx.android.synthetic.main.activity_user_info.*
import android.R.attr.name
import android.support.design.widget.AppBarLayout
import android.view.MenuItem
import com.xiangjuncheng.kotlinbilibili.utils.SystemBarHelper
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import com.xiangjuncheng.kotlinbilibili.network.RetrofitHelper
import rx.functions.Action0
import android.support.v4.accessibilityservice.AccessibilityServiceInfoCompat.getDescription
import android.text.TextUtils
import com.bumptech.glide.load.resource.bitmap.TransformationUtils.centerCrop




class UserInfoDetailsActivity : RxBaseActivity() {

    private var name = ""

    private var mid: Int = 0

    private var avatar_url: String? = null

    private var mUserDetailsInfo: UserDetailsInfo? = null

    private val titles = ArrayList<String>()

    private val fragments = ArrayList<Fragment>()

    private val userContributeCount: Int = 0

    private val userFavoritesCount: Int = 0

    private val userChaseBangumiCount: Int = 0

    private val userInterestQuanCount: Int = 0

    private val userCoinsCount: Int = 0

    private val userPlayGameCount: Int = 0

    private val mUserContributeInfo: UserContributeInfo? = null

    private val mUserFavoritesInfo: UserFavoritesInfo? = null

    private val mUserChaseBangumiInfo: UserChaseBangumiInfo? = null

    private val mUserInterestQuanInfo: UserInterestQuanInfo? = null

    private val mUserCoinsInfo: UserCoinsInfo? = null

    private val mUserPlayGameInfo: UserPlayGameInfo? = null

    private val mUserLiveRoomStatusInfo: UserLiveRoomStatusInfo? = null

    private val userContributes: ArrayList<UserContributeInfo.DataBean.VlistBean>()

    private val userCoins = ArrayList<UserCoinsInfo.DataBean.ListBean>()

    private val userFavorites = ArrayList<UserFavoritesInfo.DataBean>()

    private val userChaseBangumis = ArrayList<UserChaseBangumiInfo.DataBean.ResultBean>()

    private val userInterestQuans = ArrayList<UserInterestQuanInfo.DataBean.ResultBean>()

    private val userPlayGames = ArrayList<UserPlayGameInfo.DataBean.GamesBean>()

    private val EXTRA_USER_NAME = "extra_user_name"
    private val EXTRA_MID = "extra_mid"
    private val EXTRA_AVATAR_URL = "extra_avatar_url"

    override fun getLayoutId(): Int = R.layout.activity_user_info

    override fun initViews(savedInstanceState: Bundle?) {
        if (intent != null) {
            name = intent.getStringExtra(EXTRA_USER_NAME)
            mid = intent.getIntExtra(EXTRA_MID,-1)
            avatar_url = intent.getStringExtra(EXTRA_AVATAR_URL)
        }

        user_name.text = name
        if (avatar_url != null){
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
        tv_fans.setText(NumberUtil.converString(mUserDetailsInfo?.card?.fans))

        //设置用户等级
        setUserLevel(Integer.valueOf(mUserDetailsInfo?.card?.getRank()))

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

}
