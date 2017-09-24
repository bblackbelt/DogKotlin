package com.blackbelt.dogkotlin.bindable.android

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView


private val KEY_ITEMS = -1024

@SuppressWarnings("unchecked")
@BindingAdapter("items")
fun setItems(recyclerView: RecyclerView, items: Collection<Any>?) {
    recyclerView.setTag(KEY_ITEMS, items)
    if (recyclerView.adapter is AndroidBindableRecyclerViewAdapter) {
        (recyclerView.adapter as AndroidBindableRecyclerViewAdapter).setDataSet(items)
    }
}

@SuppressWarnings("unchecked")
@BindingAdapter("itemViewBinder")
fun setItemViewBinder(recyclerView: RecyclerView, templates: Map<Class<*>, AndroidBaseItemBinder>) {
    val items: Collection<Any>? = recyclerView.getTag(KEY_ITEMS) as Collection<Any>?;
    if (recyclerView.adapter is AndroidBindableRecyclerViewAdapter) {
        setItems(recyclerView, items)
        return
    }
    val adapter: AndroidBindableRecyclerViewAdapter = AndroidBindableRecyclerViewAdapter(templates);
    recyclerView.isNestedScrollingEnabled = true
    recyclerView.setHasFixedSize(true)
    recyclerView.adapter = adapter
    setItems(recyclerView, items);
}


