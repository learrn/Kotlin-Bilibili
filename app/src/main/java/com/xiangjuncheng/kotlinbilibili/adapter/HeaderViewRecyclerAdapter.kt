package com.xiangjuncheng.kotlinbilibili.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import java.util.ArrayList
import java.util.HashMap

/**
 * Created by xiangjuncheng on 2017/10/13.
 */
class HeaderViewRecyclerAdapter(adapter: RecyclerView.Adapter<*>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mWrappedAdapter: RecyclerView.Adapter<*>? = null

    private val mHeaderViews: MutableList<View>
    private val mFooterViews: MutableList<View>

    private val mItemTypesOffset: MutableMap<Class<*>, Int>


    private val wrappedItemCount: Int
        get() = mWrappedAdapter!!.itemCount


    private val headerCount: Int
        get() = mHeaderViews.size


    private val footerCount: Int
        get() = mFooterViews.size


    private val adapterTypeOffset: Int?
        get() = mItemTypesOffset[mWrappedAdapter::class.java]


    private val mDataObserver = object : RecyclerView.AdapterDataObserver() {

        override fun onChanged() {

            super.onChanged()
            notifyDataSetChanged()
        }


        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {

            super.onItemRangeChanged(positionStart, itemCount)
            notifyItemRangeChanged(positionStart + headerCount, itemCount)
        }


        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {

            super.onItemRangeInserted(positionStart, itemCount)
            notifyItemRangeInserted(positionStart + headerCount, itemCount)
        }


        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {

            super.onItemRangeRemoved(positionStart, itemCount)
            notifyItemRangeRemoved(positionStart + headerCount, itemCount)
        }


        override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {

            super.onItemRangeMoved(fromPosition, toPosition, itemCount)
            val hCount = headerCount
            // TODO: No notifyItemRangeMoved method?
            notifyItemRangeChanged(fromPosition + hCount, toPosition + hCount + itemCount)
        }
    }


    init {
        mHeaderViews = ArrayList()
        mFooterViews = ArrayList()
        mItemTypesOffset = HashMap<Class<*>,Integer>
        setWrappedAdapter(adapter)
    }


    fun setAdapter(adapter: RecyclerView.Adapter<*>) {

        if (mWrappedAdapter != null && mWrappedAdapter!!.itemCount > 0) {
            notifyItemRangeRemoved(headerCount, mWrappedAdapter!!.itemCount)
        }
        setWrappedAdapter(adapter)
        notifyItemRangeInserted(headerCount, mWrappedAdapter!!.itemCount)
    }


    override fun getItemViewType(position: Int): Int {

        val hCount = headerCount
        if (position < hCount) {
            return HEADERS_START + position
        } else {
            val itemCount = mWrappedAdapter!!.itemCount
            return if (position < hCount + itemCount) {
                adapterTypeOffset + mWrappedAdapter!!.getItemViewType(position - hCount)
            } else {
                FOOTERS_START + position - hCount - itemCount
            }
        }
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType < HEADERS_START + headerCount) {
            StaticViewHolder(mHeaderViews[viewType - HEADERS_START])
        } else if (viewType < FOOTERS_START + footerCount) {
            StaticViewHolder(mFooterViews[viewType - FOOTERS_START])
        } else {
            mWrappedAdapter!!.onCreateViewHolder(viewGroup, viewType - adapterTypeOffset)
        }
    }


    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

        val hCount = headerCount
        if (position >= hCount && position < hCount + mWrappedAdapter!!.itemCount) {
            mWrappedAdapter!!.onBindViewHolder(viewHolder, position - hCount)
        }
    }


    fun addHeaderView(view: View) {

        mHeaderViews.add(view)
    }


    fun addFooterView(view: View) {

        mFooterViews.add(view)
    }


    fun removeHeadView() {

        mHeaderViews.clear()
    }


    fun removeFootView() {
        mFooterViews.clear()
    }


    override fun getItemCount(): Int {

        return headerCount + footerCount + wrappedItemCount
    }


    private fun setWrappedAdapter(adapter: RecyclerView.Adapter<*>) {

        if (mWrappedAdapter != null) mWrappedAdapter!!.unregisterAdapterDataObserver(mDataObserver)
        mWrappedAdapter = adapter
        val adapterClass = mWrappedAdapter!!.javaClass
        if (!mItemTypesOffset.containsKey(adapterClass)) putAdapterTypeOffset(adapterClass)
        mWrappedAdapter!!.registerAdapterDataObserver(mDataObserver)
    }


    private fun putAdapterTypeOffset(adapterClass: Class<*>) {

        mItemTypesOffset.put(adapterClass, ITEMS_START + mItemTypesOffset.size * ADAPTER_MAX_TYPES)
    }

    private class StaticViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView)

    companion object {

        private val HEADERS_START = Integer.MIN_VALUE

        private val FOOTERS_START = Integer.MIN_VALUE + 10

        private val ITEMS_START = Integer.MIN_VALUE + 20

        private val ADAPTER_MAX_TYPES = 100
    }
}