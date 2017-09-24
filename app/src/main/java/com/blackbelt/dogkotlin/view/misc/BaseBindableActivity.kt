package com.blackbelt.dogkotlin.view.misc

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.LayoutRes
import com.blackbelt.dogkotlin.bindable.android.DogBaseViewModel
import com.blackbelt.dogkotlin.bindable.android.IActivityLifecycle

abstract class BaseBindableActivity : BaseInjectableActivity() {

    private var mDogBaseViewModel: DogBaseViewModel? = null

    private lateinit var mBinding: ViewDataBinding;

    fun setContentView(bindingVariable: Int, viewModel: DogBaseViewModel, @LayoutRes layoutRes: Int) {
        mDogBaseViewModel = viewModel
        mBinding = DataBindingUtil.setContentView<ViewDataBinding>(this, layoutRes);
        mBinding.setVariable(bindingVariable, viewModel)
        viewModel.onCreate(null)
    }

    override fun onStart() {
        super.onStart()
        if (mDogBaseViewModel is IActivityLifecycle) {
            (mDogBaseViewModel as IActivityLifecycle).onStart()
        }
    }

    override fun onStop() {
        super.onStop()
        if (mDogBaseViewModel is IActivityLifecycle) {
            (mDogBaseViewModel as IActivityLifecycle).onStop()
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        if (mDogBaseViewModel is IActivityLifecycle) {
            (mDogBaseViewModel as IActivityLifecycle).onPostCreate(savedInstanceState)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mDogBaseViewModel is IActivityLifecycle) {
            (mDogBaseViewModel as IActivityLifecycle).onDestroy()
        }
    }

}
