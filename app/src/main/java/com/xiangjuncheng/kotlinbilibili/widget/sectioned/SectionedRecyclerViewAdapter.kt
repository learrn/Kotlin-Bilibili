package com.xiangjuncheng.kotlinbilibili.widget.sectioned

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import sun.swing.SwingUtilities2.Section



/**
 * Created by xiangjuncheng on 2017/7/4.
 */
class SectionedRecyclerViewAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        val VIEW_TYPE_HEADER = 0
        val VIEW_TYPE_FOOTER = 1
        val VIEW_TYPE_ITEM_LOADED = 2
        val VIEW_TYPE_LOADING = 3
        val VIEW_TYPE_FAILED = 4
        private val VIEW_TYPE_QTY = 5
    }

    private val sections: LinkedHashMap<String, Section>? = null
    private val sectionViewTypeNumbers: HashMap<String, Int>? = null
    private val viewTypeCount = 0
    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}