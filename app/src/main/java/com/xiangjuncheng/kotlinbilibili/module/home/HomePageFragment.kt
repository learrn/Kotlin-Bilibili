package com.xiangjuncheng.kotlinbilibili.module.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.TextUtils
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.miguelcatalan.materialsearchview.MaterialSearchView
import com.xiangjuncheng.kotlinbilibili.R
import com.xiangjuncheng.kotlinbilibili.adapter.pager.HomePagerAdapter
import com.xiangjuncheng.kotlinbilibili.base.RxLazyFragment
import com.xiangjuncheng.kotlinbilibili.module.common.MainActivity
import com.xiangjuncheng.kotlinbilibili.widget.CircleImageView
import kotlinx.android.synthetic.main.fragment_home_pager.*


/**
 * Created by xiangjuncheng on 2017/5/25.
 * 主页面fragment
 */
object HomePageFragment : RxLazyFragment() {
    override fun getLayoutResId(): Int = R.layout.fragment_home_pager

    override fun finishCreateView(state: Bundle?) {
        setHasOptionsMenu(true)
        initToolBar()
        initSearchView()
        initViewPager()
    }

    private fun initToolBar() {
        toolbar.title = ""
        (activity as MainActivity).setSupportActionBar(toolbar)
        (toolbar_user_avatar as CircleImageView).setImageResource(R.drawable.ic_hotbitmapgg_avatar)
    }

    private fun initSearchView() {
        val mSearchView = search_view
        mSearchView.setVoiceSearch(false)
        mSearchView.setCursorDrawable(R.drawable.custom_cursor)
        mSearchView.setEllipsize(true)
        mSearchView.setSuggestions(resources.getStringArray(R.array.query_suggestions))
        mSearchView.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                //TotalStationSearchActivity.launch(activity, query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun initViewPager() {
        view_pager.offscreenPageLimit = 5
        view_pager.adapter = HomePagerAdapter(childFragmentManager, getApplicationContext())
        sliding_tabs.setViewPager(view_pager)
        //view_pager.currentItem = 0
        navigation_layout.setOnClickListener {
            if (activity is MainActivity){
                (activity as MainActivity).toggleDrawer()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        menu?.clear()
        inflater?.inflate(R.menu.menu_main, menu)
        search_view.setMenuItem(menu?.findItem(R.id.id_action_search))
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
//        when(item?.itemId){
//            R.id.id_action_game -> startActivity(Intent(activity, GameCentreActivity::class.java))
//            R.id.id_action_download -> startActivity(Intent(activity,OffLineDownloadActivity::class.java))
//        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && requestCode == Activity.RESULT_OK) {
            val matches = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            if (matches != null && matches.size > 0 && !TextUtils.isEmpty(matches[0])) {
                search_view.setQuery(matches[0], false)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun isOpenSearchView(): Boolean = search_view.isSearchOpen

    fun closeSearchView() {
        search_view.closeSearch()
    }
}