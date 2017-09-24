package com.blackbelt.dogkotlin.bindable.android

import android.app.Activity
import android.databinding.BaseObservable
import java.lang.ref.WeakReference

open class DogBaseViewModel : BaseObservable(), IActivityLifecycle {

    private var mParentActivity: WeakReference<Activity>? = null

    fun setParentActivity(activity: Activity?) {
        if (mParentActivity != null) {
            mParentActivity?.clear()
        }
        if (activity != null) {
            mParentActivity = WeakReference(activity)
        }
    }

    fun getParentActivity(): Activity? {
        if (mParentActivity == null) {
            return null
        }
        return mParentActivity?.get()
    }
}