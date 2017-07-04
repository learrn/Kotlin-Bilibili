package com.xiangjuncheng.kotlinbilibili.widget.sectioned

import android.support.v7.widget.RecyclerView
import android.view.View


/**
 * Created by xiangjuncheng on 2017/7/4.
 */
abstract class Section{
    enum class State {
        LOADING, LOADED, FAILED
    }

    private var state = State.LOADED
    var visible = true
    var hasHeader = false
    var hasFooter = false
    var headerResourceId: Int? = null
    var footerResourceId: Int? = null
    var itemResourceId: Int = 0
    private var loadingResourceId: Int?
    private var failedResourceId: Int?

    /**
     * Package-level constructor
     */
    fun Section(): ??? {}

    /**
     * Create a Section object with loading/failed states but no header and footer

     * @param itemResourceId layout resource for its items
     * *
     * @param loadingResourceId layout resource for its loading state
     * *
     * @param failedResourceId layout resource for its failed state
     */
    fun Section(itemResourceId: Int, loadingResourceId: Int, failedResourceId: Int): ??? {
        this.itemResourceId = itemResourceId
        this.loadingResourceId = loadingResourceId
        this.failedResourceId = failedResourceId
    }


    /**
     * Create a Section object with loading/failed states, a custom header but no footer

     * @param headerResourceId layout resource for its header
     * *
     * @param itemResourceId layout resource for its items
     * *
     * @param loadingResourceId layout resource for its loading state
     * *
     * @param failedResourceId layout resource for its failed state
     */
    fun Section(headerResourceId: Int, itemResourceId: Int, loadingResourceId: Int, failedResourceId: Int): ??? {
        this(itemResourceId, loadingResourceId, failedResourceId)
        this.headerResourceId = headerResourceId
        hasHeader = true
    }


    /**
     * Create a Section object with loading/failed states, a custom header and footer

     * @param headerResourceId layout resource for its header
     * *
     * @param footerResourceId layout resource for its footer
     * *
     * @param itemResourceId layout resource for its items
     * *
     * @param loadingResourceId layout resource for its loading state
     * *
     * @param failedResourceId layout resource for its failed state
     */
    fun Section(headerResourceId: Int, footerResourceId: Int, itemResourceId: Int, loadingResourceId: Int, failedResourceId: Int): ??? {
        this(headerResourceId, itemResourceId, loadingResourceId, failedResourceId)
        this.footerResourceId = footerResourceId
        hasFooter = true
    }


    /**
     * Set the State of this Section

     * @param state state of this section
     */
    fun setState(state: State) {
        this.state = state
    }


    /**
     * Return the current State of this Section

     * @return current state of this section
     */
    fun getState(): State {
        return state
    }


    /**
     * Check if this Section is visible

     * @return true if this Section is vibisle
     */
    fun isVisible(): Boolean {
        return visible
    }


    /**
     * Set if this Section is visible

     * @param visible true if this Section is visible
     */
    fun setVisible(visible: Boolean) {
        this.visible = visible
    }


    /**
     * Check if this Section has a header

     * @return true if this Section has a header
     */
    fun hasHeader(): Boolean {
        return hasHeader
    }


    /**
     * Set if this Section has header

     * @param hasHeader true if this Section has a header
     */
    fun setHasHeader(hasHeader: Boolean) {
        this.hasHeader = hasHeader
    }


    /**
     * Check if this Section has a footer

     * @return true if this Section has a footer
     */
    fun hasFooter(): Boolean {
        return hasFooter
    }


    /**
     * Set if this Section has footer

     * @param hasFooter true if this Section has a footer
     */
    fun setHasFooter(hasFooter: Boolean) {
        this.hasFooter = hasFooter
    }


    /**
     * Return the layout resource id of the header

     * @return layout resource id of the header
     */
    fun getHeaderResourceId(): Int? {
        return headerResourceId
    }


    /**
     * Return the layout resource id of the footer

     * @return layout resource id of the footer
     */
    fun getFooterResourceId(): Int? {
        return footerResourceId
    }


    /**
     * Return the layout resource id of the item

     * @return layout resource id of the item
     */
    fun getItemResourceId(): Int {
        return itemResourceId
    }


    /**
     * Return the layout resource id of the loading view

     * @return layout resource id of the loading view
     */
    fun getLoadingResourceId(): Int? {
        return loadingResourceId
    }


    /**
     * Return the layout resource id of the failed view

     * @return layout resource id of the failed view
     */
    fun getFailedResourceId(): Int? {
        return failedResourceId
    }


    /**
     * Bind the data to the ViewHolder for the Content of this Section, that can be the Items,
     * Loading view or Failed view, depending on the current state of the section

     * @param holder ViewHolder for the Content of this Section
     * *
     * @param position position of the item in the Section, not in the RecyclerView
     */
    fun onBindContentViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (state) {
            Section.State.LOADING -> onBindLoadingViewHolder(holder)
            Section.State.LOADED -> onBindItemViewHolder(holder, position)
            Section.State.FAILED -> onBindFailedViewHolder(holder)
            else -> throw IllegalStateException("Invalid state")
        }
    }


    /**
     * Return the total of items of this Section, including content items (according to the section
     * state) plus header and footer

     * @return total of items of this section
     */
    fun getSectionItemsTotal(): Int {
        val contentItemsTotal: Int
        when (state) {
            Section.State.LOADING -> contentItemsTotal = 1
            Section.State.LOADED -> contentItemsTotal = getContentItemsTotal()
            Section.State.FAILED -> contentItemsTotal = 1
            else -> throw IllegalStateException("Invalid state")
        }
        return contentItemsTotal + (if (hasHeader) 1 else 0) + if (hasFooter) 1 else 0
    }


    /**
     * Return the total of items of this Section

     * @return total of items of this Section
     */
    abstract fun getContentItemsTotal(): Int


    /**
     * Return the ViewHolder for the Header of this Section

     * @param view View inflated by resource returned by getHeaderResourceId
     * *
     * @return ViewHolder for the Header of this Section
     */
    fun getHeaderViewHolder(view: View): RecyclerView.ViewHolder {
        return SectionedRecyclerViewAdapter.EmptyViewHolder(view)
    }


    /**
     * Bind the data to the ViewHolder for the Header of this Section

     * @param holder ViewHolder for the Header of this Section
     */
    fun onBindHeaderViewHolder(holder: RecyclerView.ViewHolder) {
        // Nothing to bind here.
    }


    /**
     * Return the ViewHolder for the Footer of this Section

     * @param view View inflated by resource returned by getFooterResourceId
     * *
     * @return ViewHolder for the Footer of this Section
     */
    fun getFooterViewHolder(view: View): RecyclerView.ViewHolder {
        return SectionedRecyclerViewAdapter.EmptyViewHolder(view)
    }


    /**
     * Bind the data to the ViewHolder for the Footer of this Section

     * @param holder ViewHolder for the Footer of this Section
     */
    fun onBindFooterViewHolder(holder: RecyclerView.ViewHolder) {
        // Nothing to bind here.
    }


    /**
     * Return the ViewHolder for a single Item of this Section

     * @param view View inflated by resource returned by getItemResourceId
     * *
     * @return ViewHolder for the Item of this Section
     */
    abstract fun getItemViewHolder(view: View): RecyclerView.ViewHolder

    /**
     * Bind the data to the ViewHolder for an Item of this Section

     * @param holder ViewHolder for the Item of this Section
     * *
     * @param position position of the item in the Section, not in the RecyclerView
     */
    abstract fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, position: Int)


    /**
     * Return the ViewHolder for the Loading state of this Section

     * @param view View inflated by resource returned by getItemResourceId
     * *
     * @return ViewHolder for the Loading state of this Section
     */
    fun getLoadingViewHolder(view: View): RecyclerView.ViewHolder {
        return SectionedRecyclerViewAdapter.EmptyViewHolder(view)
    }


    /**
     * Bind the data to the ViewHolder for Loading state of this Section

     * @param holder ViewHolder for the Loading state of this Section
     */
    fun onBindLoadingViewHolder(holder: RecyclerView.ViewHolder) {
        // Nothing to bind here.
    }


    /**
     * Return the ViewHolder for the Failed state of this Section

     * @param view View inflated by resource returned by getItemResourceId
     * *
     * @return ViewHolder for the Failed of this Section
     */
    fun getFailedViewHolder(view: View): RecyclerView.ViewHolder {
        return SectionedRecyclerViewAdapter.EmptyViewHolder(view)
    }


    /**
     * Bind the data to the ViewHolder for the Failed state of this Section

     * @param holder ViewHolder for the Failed state of this Section
     */
    fun onBindFailedViewHolder(holder: RecyclerView.ViewHolder) {
        // Nothing to bind here.
    }
}