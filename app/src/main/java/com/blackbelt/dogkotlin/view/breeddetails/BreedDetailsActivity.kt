package com.blackbelt.dogkotlin.view.breeddetails

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.blackbelt.dogkotlin.BR
import com.blackbelt.dogkotlin.R
import com.blackbelt.dogkotlin.view.breeddetails.viewmodel.BreedDetailsViewModel
import com.blackbelt.dogkotlin.view.misc.BaseBindableActivity
import javax.inject.Inject

const val DOG_BREED_KEY = "DOG_BREED_KEY"

class BreedDetailsActivity : BaseBindableActivity() {

    private lateinit var mMainRecyclerView: RecyclerView

    @Inject
    lateinit var mBreedDetailsViewModel: BreedDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(BR.breedDetailsViewModel, mBreedDetailsViewModel, R.layout.activity_breed_details)

        mMainRecyclerView = findViewById(R.id.main_rv)
        mMainRecyclerView.layoutManager = GridLayoutManager(this, 3)
    }

}
