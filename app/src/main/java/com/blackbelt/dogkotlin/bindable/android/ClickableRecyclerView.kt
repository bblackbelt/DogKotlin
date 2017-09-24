package com.blackbelt.dogkotlin.bindable.android

import android.content.Context
import android.support.v4.view.GestureDetectorCompat
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.MotionEvent

class ClickableRecyclerView(context: Context, attributes: AttributeSet) : RecyclerView(context, attributes), RecyclerView.OnItemTouchListener {

    var mRecyclerViewClickListener: RecyclerViewClickListener? = null
        set(value) {
            field = value
            mRecyclerViewGestureListener.mRecyclerViewListener = field
        }

    val mRecyclerViewGestureListener = RecyclerViewGestureListener(this)

    val mGestureDetector: GestureDetectorCompat = GestureDetectorCompat(context, mRecyclerViewGestureListener)

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        addOnItemTouchListener(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        removeOnItemTouchListener(this)
    }

    override fun onTouchEvent(rv: RecyclerView?, e: MotionEvent?) {
    }

    override fun onInterceptTouchEvent(rv: RecyclerView?, e: MotionEvent?): Boolean {
        return mGestureDetector.onTouchEvent(e)
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
    }

}