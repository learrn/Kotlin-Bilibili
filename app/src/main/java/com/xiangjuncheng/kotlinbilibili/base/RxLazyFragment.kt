package com.xiangjuncheng.kotlinbilibili.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.trello.rxlifecycle.components.support.RxFragment
import android.support.v4.app.FragmentActivity
import android.support.annotation.LayoutRes


abstract class RxLazyFragment : RxFragment() {
    private var parentView: View? = null

    private var mActivity: FragmentActivity? = null

    // 标志位 标志已经初始化完成。
    protected var isPrepared: Boolean = false

    //标志位 fragment是否可见
    protected var isFragmentVisible: Boolean = false

    @LayoutRes
    abstract fun getLayoutResId(): Int

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        parentView = inflater?.inflate(getLayoutResId(), container, false)
        mActivity = getSupportActivity()
        return parentView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        finishCreateView(savedInstanceState)
    }

    abstract fun finishCreateView(state: Bundle?)

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        this.mActivity = activity as FragmentActivity
    }

    override fun onDetach() {
        super.onDetach()
        this.mActivity = null
    }

    fun getSupportActivity(): FragmentActivity {
        return super.getActivity()
    }


    fun getSupportActionBar(): android.app.ActionBar {
        return getSupportActivity().actionBar
    }


    fun getApplicationContext(): Context? {
        if (this.mActivity == null)
            if (activity == null)
                return null
            else
                return activity.applicationContext
        else
            return this.mActivity!!.applicationContext
    }


    /**
     * Fragment数据的懒加载
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {

        super.setUserVisibleHint(isVisibleToUser)
        if (userVisibleHint) {
            isFragmentVisible = true
            onVisible()
        } else {
            isFragmentVisible = false
            onInvisible()
        }
    }

    protected fun onVisible() {
        lazyLoad()
    }


    protected open fun lazyLoad() {}


    protected open fun onInvisible() {}


    protected open fun loadData() {}


    protected open fun showProgressBar() {}


    protected open fun hideProgressBar() {}


    protected open fun initRecyclerView() {}


    protected open fun initRefreshLayout() {}


    protected open fun finishTask() {}

    fun <T : View> `$`(id: Int): T {
        return parentView?.findViewById(id) as T
    }
}
