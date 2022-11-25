package com.application.hmkcopy.base

import android.content.Context
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.application.hmkcopy.util.extentions.CallbackObject
import com.application.hmkcopy.util.extentions.CallbackObjects

abstract class BaseAdapter<Object, ViewHolder : RecyclerView.ViewHolder> : RecyclerView.Adapter<ViewHolder>() {

    var items: List<Object>? = null
    var recyclerView: RecyclerView? = null
    var callbackDidSelectItem: CallbackObject<Object>? = null
    var callbackDidSelectItemWithPosition: CallbackObjects<Object, Int>? = null
    var context: Context? = null

    open fun configureItemSize(holder: ViewHolder, position: Int, item: Object) {}
    abstract fun bindView(holder: ViewHolder, position: Int, item: Object)
    abstract fun createView(context: Context, parent: ViewGroup, inflater: LayoutInflater, viewType: Int): ViewHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val holder = createView(parent.context, parent, LayoutInflater.from(parent.context), viewType)
        items?.getOrNull(viewType)?.let {
            configureItemSize(holder, viewType, it)
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        items?.getOrNull(position)?.let {
            bindView(holder, position, it)
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return this.items?.size ?: 0
    }

    fun set(items: List<Object>?) {
        this.items = items
        this.reload()
    }

    fun clear() {
        this.items = null
        this.reload()
    }

    fun reloadItem(position:Int, payload: Object?){
        this.notifyItemChanged(position, payload)
    }

    fun reload() {
        this.notifyDataSetChanged()
    }

    fun isLastItem(position: Int): Boolean {
        return (items?.size ?: 0) - 1 == position
    }

    fun isFirstItem(position: Int): Boolean {
        return 0 == position
    }

}


abstract class BaseListAdapter<Object: Parcelable , ViewHolder : RecyclerView.ViewHolder>(diff: DiffUtil.ItemCallback<Object>) : ListAdapter<Object, ViewHolder>(diff) {

    var recyclerView: RecyclerView? = null
    var callbackDidSelectItem: CallbackObject<Object>? = null
    var callbackDidSelectItemWithPosition: CallbackObjects<Object, Int>? = null
    var cloneList: List<Object>? = null
    var context: Context? = null

    open fun configureItemSize(holder: ViewHolder, position: Int, item: Object) {}
    abstract fun bindView(holder: ViewHolder, position: Int, item: Object)
    abstract fun createView(context: Context, parent: ViewGroup, inflater: LayoutInflater, viewType: Int): ViewHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val holder = createView(parent.context, parent, LayoutInflater.from(parent.context), viewType)
        configureItemSize(holder, viewType, getItem(viewType))
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        bindView(holder, position, getItem(position))
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun set(items: List<Object>?) {
        submitList(items)
    }

    fun reloadItem(position:Int, payload: Object?){
        this.notifyItemChanged(position, payload)
    }

    fun reload(){
        submitList(cloneList?.toList())
    }

    fun clear() {
        submitList(null)
    }

    fun isLastItem(position: Int): Boolean {
        return itemCount - 1 == position
    }

}

