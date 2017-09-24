package com.blackbelt.dogkotlin.bindable.android

import android.graphics.PointF
import android.support.v7.widget.RecyclerView
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import java.lang.ref.WeakReference

open class RecyclerViewGestureListener(recyclerView: RecyclerView) : GestureDetector.SimpleOnGestureListener() {

    var mRecyclerViewListener: RecyclerViewClickListener? = null
        set

    val mRecyclerView = WeakReference<RecyclerView>(recyclerView)

    protected fun getChildAt(event: MotionEvent?): View? {
        if (event == null) {
            return null
        }
        return mRecyclerView.get()?.findChildViewUnder(event.x, event.y)
    }

    override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
        val event = e ?: return false
        val recyclerView = mRecyclerView.get() ?: return false
        val childView = getChildAt(event) ?: return false
        val position = recyclerView.getChildAdapterPosition(childView)
        val childAt = getChildOfAt(childView, event.x.toInt(), event.y.toInt())
        if (childAt != null && childAt.hasOnClickListeners()) {
            return true
        }
        if (position == RecyclerView.NO_POSITION) {
            return false
        }
        var items: List<Any>? = null
        if (recyclerView.adapter is AndroidBindableRecyclerViewAdapter) {
            items = (recyclerView.adapter as AndroidBindableRecyclerViewAdapter).mDataSet
        }

        if (items == null) {
            return false
        }

        if (mRecyclerViewListener != null) {
            mRecyclerViewListener!!.onItemClick(childView, items.get(position))
            return true
        }
        return false
    }

    protected fun getChildOfAt(view: View, x: Int, y: Int): View? {
        return hit(view, x - view.x, y - view.y)
    }

    protected fun pointInView(view: View, localX: Float, localY: Float): Boolean {
        return localX >= 0 && localX < view.right - view.left
                && localY >= 0 && localY < view.bottom - view.top
    }

    protected fun isHittable(view: View): Boolean {
        return view.visibility == View.VISIBLE && view.alpha >= 0.001f
    }

    protected fun isTransformedPointInView(
            parent: ViewGroup?,
            child: View?,
            x: Float,
            y: Float, outLocalPoint: PointF?): Boolean {

        if (parent == null || child == null) {
            return false
        }

        val localX = x + parent.scrollX - child.left
        val localY = y + parent.scrollY - child.top

        val isInView = pointInView(child, localX, localY)
        if (isInView && outLocalPoint != null) {
            outLocalPoint.set(localX, localY)
        }
        return isInView
    }

    protected fun hit(
            view: View,
            x: Float,
            y: Float): View? {

        if (!isHittable(view)) {
            return null
        }

        if (!pointInView(view, x, y)) {
            return null
        }

        if (view !is ViewGroup) {
            return view
        }

        val viewGroup = view

        // TODO: get list of Views that are sorted in Z- and draw-order, e.g. buildOrderedChildList()
        if (viewGroup.childCount > 0) {
            val localPoint = PointF()

            for (i in viewGroup.childCount - 1 downTo 0) {
                val child = viewGroup.getChildAt(i)

                if (isTransformedPointInView(viewGroup, child, x, y, localPoint)) {
                    val childResult = hit(
                            child,
                            localPoint.x,
                            localPoint.y)

                    if (childResult != null) {
                        return childResult
                    }
                }
            }
        }

        return viewGroup
    }

}