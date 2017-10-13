package com.xiangjuncheng.kotlinbilibili.adapter.helper

import android.content.Context
import android.support.annotation.IdRes
import android.support.v7.widget.RecyclerView
import android.view.View
import java.util.ArrayList

/**
 * Created by xiangjuncheng on 2017/10/13.
 */
abstract class AbsRecyclerViewAdapter(protected var mRecyclerView: RecyclerView) : RecyclerView.Adapter<AbsRecyclerViewAdapter.ClickableViewHolder>() {

    var context: Context? = null
        private set

    private val mListeners = ArrayList<RecyclerView.OnScrollListener>()

    private var itemClickListener: OnItemClickListener? = null

    private var itemLongClickListener: OnItemLongClickListener? = null


    init {
        this.mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(rv: RecyclerView?, newState: Int) {

                for (listener in mListeners) {
                    listener.onScrollStateChanged(rv, newState)
                }
            }


            override fun onScrolled(rv: RecyclerView?, dx: Int, dy: Int) {

                for (listener in mListeners) {
                    listener.onScrolled(rv, dx, dy)
                }
            }
        })
    }


    fun addOnScrollListener(listener: RecyclerView.OnScrollListener) {

        mListeners.add(listener)
    }


    interface OnItemClickListener {

        fun onItemClick(position: Int, holder: ClickableViewHolder)
    }

    interface OnItemLongClickListener {

        fun onItemLongClick(position: Int, holder: ClickableViewHolder): Boolean
    }


    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.itemClickListener = listener
    }


    fun setOnItemLongClickListener(listener: OnItemLongClickListener) {

        this.itemLongClickListener = listener
    }


    fun bindContext(context: Context) {

        this.context = context
    }


    override fun onBindViewHolder(holder: ClickableViewHolder, position: Int) {

        holder.parentView.setOnClickListener { v ->

            if (itemClickListener != null) {
                itemClickListener!!.onItemClick(position, holder)
            }
        }
        holder.parentView.setOnLongClickListener { v -> itemLongClickListener != null && itemLongClickListener!!.onItemLongClick(position, holder) }
    }


    open class ClickableViewHolder(internal val parentView: View) : RecyclerView.ViewHolder(parentView) {


        fun <T : View> `$`(@IdRes id: Int): T {

            return parentView.findViewById(id) as T
        }
    }
}