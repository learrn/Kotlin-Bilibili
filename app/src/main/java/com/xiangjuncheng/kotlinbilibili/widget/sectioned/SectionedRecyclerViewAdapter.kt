//package com.xiangjuncheng.kotlinbilibili.widget.sectioned
//
//import android.support.v7.widget.RecyclerView
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import sun.swing.SwingUtilities2.Section
//import java.util.*
//
//
///**
// * Created by xiangjuncheng on 2017/7/4.
// */
//class SectionedRecyclerViewAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>() {
//    companion object {
//        val VIEW_TYPE_HEADER = 0
//        val VIEW_TYPE_FOOTER = 1
//        val VIEW_TYPE_ITEM_LOADED = 2
//        val VIEW_TYPE_LOADING = 3
//        val VIEW_TYPE_FAILED = 4
//        private val VIEW_TYPE_QTY = 5
//    }
//
//    private var sections: LinkedHashMap<String, Section>? = null
//    private var sectionViewTypeNumbers: HashMap<String, Int>? = null
//    private var viewTypeCount = 0
//    fun SectionedRecyclerViewAdapter(): ??? {
//        sections = LinkedHashMap<String, Section>()
//        sectionViewTypeNumbers = HashMap<String, Int>()
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        var viewHolder: RecyclerView.ViewHolder? = null
//        var view: View
//        for ((key, value) in sectionViewTypeNumbers) {
//            if (viewType >= value && viewType < value + VIEW_TYPE_QTY) {
//                val section = sections[key]
//                val sectionViewType = viewType - value
//                when (sectionViewType) {
//                    VIEW_TYPE_HEADER -> {
//                        val resId = section.getHeaderResourceId() ?: throw NullPointerException("Missing 'header' resource id")
//                        view = LayoutInflater.from(parent.context).inflate(resId, parent, false)
//                        viewHolder = section.getHeaderViewHolder(view)
//                    }
//                    VIEW_TYPE_FOOTER -> {
//                        val resId = section.getFooterResourceId() ?: throw NullPointerException("Missing 'footer' resource id")
//                        view = LayoutInflater.from(parent.context).inflate(resId, parent, false)
//                        viewHolder = section.getFooterViewHolder(view)
//                    }
//                    VIEW_TYPE_ITEM_LOADED -> {
//                        view = LayoutInflater.from(parent.context)
//                                .inflate(section.getItemResourceId(), parent, false)
//                        viewHolder = section.getItemViewHolder(view)
//                    }
//                    VIEW_TYPE_LOADING -> {
//                        val resId = section.getLoadingResourceId() ?: throw NullPointerException("Missing 'loading state' resource id")
//                        view = LayoutInflater.from(parent.context).inflate(resId, parent, false)
//                        viewHolder = section.getLoadingViewHolder(view)
//                    }
//                    VIEW_TYPE_FAILED -> {
//                        val resId = section.getFailedResourceId() ?: throw NullPointerException("Missing 'failed state' resource id")
//                        view = LayoutInflater.from(parent.context).inflate(resId, parent, false)
//                        viewHolder = section.getFailedViewHolder(view)
//                    }
//                    else -> throw IllegalArgumentException("Invalid viewType")
//                }
//            }
//        }
//        return viewHolder
//    }
//
//
//    /**
//     * Add a section to this recyclerview.
//
//     * @param tag     unique identifier of the section
//     * *
//     * @param section section to be added
//     */
//    fun addSection(tag: String, section: Section) {
//        this.sections.put(tag, section)
//        this.sectionViewTypeNumbers.put(tag, viewTypeCount)
//        viewTypeCount += VIEW_TYPE_QTY
//    }
//
//
//    /**
//     * Add a section to this recyclerview with a random tag;
//
//     * @param section section to be added
//     * *
//     * @return generated tag
//     */
//    fun addSection(section: Section): String {
//        val tag = UUID.randomUUID().toString()
//        addSection(tag, section)
//        return tag
//    }
//
//
//    /**
//     * Return the section with the tag provided
//
//     * @param tag unique identifier of the section
//     * *
//     * @return section
//     */
//    fun getSection(tag: String): Section {
//        return this.sections.get(tag)
//    }
//
//
//    /**
//     * Remove section from this recyclerview.
//
//     * @param tag unique identifier of the section
//     */
//    fun removeSection(tag: String) {
//        this.sections.remove(tag)
//    }
//
//
//    /**
//     * Remove all sections from this recyclerview.
//     */
//    fun removeAllSections() {
//        this.sections.clear()
//    }
//
//
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        var currentPos = 0
//        for ((_, section) in sections) {
//            if (!section.isVisible()) continue
//            val sectionTotal = section.getSectionItemsTotal()
//            if (position >= currentPos && position <= currentPos + sectionTotal - 1) {
//                if (section.hasHeader()) {
//                    if (position == currentPos) {
//                        getSectionForPosition(position).onBindHeaderViewHolder(holder)
//                        return
//                    }
//                }
//                if (section.hasFooter()) {
//                    if (position == currentPos + sectionTotal - 1) {
//                        getSectionForPosition(position).onBindFooterViewHolder(holder)
//                        return
//                    }
//                }
//                getSectionForPosition(position).onBindContentViewHolder(holder, getSectionPosition(position))
//                return
//            }
//            currentPos += sectionTotal
//        }
//        throw IndexOutOfBoundsException("Invalid position")
//    }
//
//
//    override fun getItemCount(): Int {
//        var count = 0
//        for ((_, section) in sections) {
//            if (!section.isVisible()) continue
//            count += section.getSectionItemsTotal()
//        }
//        return count
//    }
//
//
//    override fun getItemViewType(position: Int): Int {
//        var currentPos = 0
//        for ((key, section) in sections) {
//            if (!section.isVisible()) continue
//            val sectionTotal = section.getSectionItemsTotal()
//            if (position >= currentPos && position <= currentPos + sectionTotal - 1) {
//                val viewType = sectionViewTypeNumbers[key]
//                if (section.hasHeader()) {
//                    if (position == currentPos) {
//                        return viewType
//                    }
//                }
//                if (section.hasFooter()) {
//                    if (position == currentPos + sectionTotal - 1) {
//                        return viewType + 1
//                    }
//                }
//                when (section.getState()) {
//                    LOADED -> return viewType + 2
//                    LOADING -> return viewType + 3
//                    FAILED -> return viewType + 4
//                    else -> throw IllegalStateException("Invalid state")
//                }
//            }
//            currentPos += sectionTotal
//        }
//        throw IndexOutOfBoundsException("Invalid position")
//    }
//
//
//    /**
//     * Returns the Section ViewType of an item based on the position in the adapter:
//     * - SectionedRecyclerViewAdapter.VIEW_TYPE_HEADER
//     * - SectionedRecyclerViewAdapter.VIEW_TYPE_FOOTER
//     * - SectionedRecyclerViewAdapter.VIEW_TYPE_ITEM_LOADED
//     * - SectionedRecyclerViewAdapter.VIEW_TYPE_LOADING
//     * - SectionedRecyclerViewAdapter.VIEW_TYPE_FAILED
//
//     * @param position position in the adapter
//     * *
//     * @return SectionedRecyclerViewAdapter.VIEW_TYPE_HEADER, VIEW_TYPE_FOOTER,
//     * * VIEW_TYPE_ITEM_LOADED, VIEW_TYPE_LOADING or VIEW_TYPE_FAILED
//     */
//    fun getSectionItemViewType(position: Int): Int {
//        val viewType = getItemViewType(position)
//        return viewType % VIEW_TYPE_QTY
//    }
//
//
//    /**
//     * Returns the Section object for a position in the adapter
//
//     * @param position position in the adapter
//     * *
//     * @return Section object for that position
//     */
//    fun getSectionForPosition(position: Int): Section {
//        var currentPos = 0
//        for ((_, section) in sections) {
//            if (!section.isVisible()) continue
//            val sectionTotal = section.getSectionItemsTotal()
//            if (position >= currentPos && position <= currentPos + sectionTotal - 1) {
//                return section
//            }
//            currentPos += sectionTotal
//        }
//        throw IndexOutOfBoundsException("Invalid position")
//    }
//
//
//    /**
//     * Return the item position relative to the section.
//
//     * @param position position of the item in the adapter
//     * *
//     * @return position of the item in the section
//     */
//    fun getSectionPosition(position: Int): Int {
//        var currentPos = 0
//        for ((_, section) in sections) {
//            if (!section.isVisible()) continue
//            val sectionTotal = section.getSectionItemsTotal()
//            if (position >= currentPos && position <= currentPos + sectionTotal - 1) {
//                return position - currentPos - if (section.hasHeader()) 1 else 0
//            }
//            currentPos += sectionTotal
//        }
//        throw IndexOutOfBoundsException("Invalid position")
//    }
//
//
//    /**
//     * Return a map with all sections of this adapter
//
//     * @return a map with all sections
//     */
//    fun getSectionsMap(): LinkedHashMap<String, Section> {
//        return sections
//    }
//
//
//    /**
//     * A concrete class of an empty ViewHolder.
//     * Should be used to avoid the boilerplate of creating a ViewHolder class for simple case
//     * scenarios.
//     */
//    class EmptyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
//}