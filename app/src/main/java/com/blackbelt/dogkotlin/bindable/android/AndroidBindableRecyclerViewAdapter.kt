package com.blackbelt.dogkotlin.bindable.android

import android.databinding.DataBindingUtil
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup


class AndroidBindableRecyclerViewAdapter(templates: Map<Class<*>, AndroidBaseItemBinder>) : RecyclerView.Adapter<AndroidBindableRecyclerViewAdapter.BindableViewHolder>() {

    var mDataSet: ObservableList<Any> = ObservableArrayList<Any>()

    var mTemplatesForObjects: Map<Class<*>, AndroidBaseItemBinder>? = templates

    class BindableViewHolder(binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        val mViewDataBinding: ViewDataBinding = binding;
    }

    override fun onBindViewHolder(holder: BindableViewHolder?, position: Int) {
        val item: Any = mDataSet[position]
        val clazz = item.javaClass
        val itemBinder: AndroidBaseItemBinder = mTemplatesForObjects?.get(clazz) ?: throw RuntimeException()
        holder?.mViewDataBinding?.setVariable(itemBinder.brVariable, item)
        holder?.mViewDataBinding?.executePendingBindings()
    }

    fun setDataSet(items: Collection<Any>?) {
        if (mDataSet == items) {
            notifyDataSetChanged()
            return
        }
        notifyItemRangeRemoved(0, mDataSet.size)
        mDataSet.clear()
        if (items == null) {
            return
        }
        if (items is ObservableArrayList) {
            mDataSet = items
        } else {
            mDataSet.addAll(items)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BindableViewHolder {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(parent?.context),
                viewType, parent, false)
        return BindableViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        val clazz = mDataSet[position].javaClass
        val itemBinder: AndroidBaseItemBinder = mTemplatesForObjects?.get(clazz) ?: throw RuntimeException()
        return itemBinder.layoutId
    }

    override fun getItemCount(): Int = mDataSet.size
}