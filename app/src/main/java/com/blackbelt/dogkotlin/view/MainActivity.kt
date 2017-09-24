package com.blackbelt.dogkotlin.view

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.blackbelt.dogkotlin.BR
import com.blackbelt.dogkotlin.R
import com.blackbelt.dogkotlin.view.misc.BaseBindableActivity
import javax.inject.Inject

class MainActivity : BaseBindableActivity() {

    private lateinit var mMainRecyclerView : RecyclerView

    @Inject
    lateinit var mMainViewModel : MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(BR.mainViewModel, mMainViewModel, R.layout.activity_main)

        mMainRecyclerView = findViewById(R.id.main_rv)
        mMainRecyclerView.layoutManager = GridLayoutManager(this, 3)
    }
}