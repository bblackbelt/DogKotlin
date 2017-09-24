package com.blackbelt.dogkotlin.view.breeddetails.viewmodel

import android.databinding.Bindable
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import android.os.Bundle
import android.widget.Toast
import com.blackbelt.dogkotlin.BR
import com.blackbelt.dogkotlin.R
import com.blackbelt.dogkotlin.api.DogBreed
import com.blackbelt.dogkotlin.api.DogBreeds
import com.blackbelt.dogkotlin.api.IApiManager
import com.blackbelt.dogkotlin.bindable.android.AndroidBaseItemBinder
import com.blackbelt.dogkotlin.bindable.android.DogBaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposables
import javax.inject.Inject

class BreedDetailsViewModel @Inject constructor(apiManager: IApiManager, breed: DogBreed?) : DogBaseViewModel() {

    val mApiManager = apiManager

    val mDogBreed = breed

    val mItems: ObservableList<DogBreed> = ObservableArrayList();

    val mTemplates = hashMapOf<Class<*>, AndroidBaseItemBinder>(DogBreed::class.java
            to AndroidBaseItemBinder(BR.dogBreed, R.layout.breed_item))
        @Bindable get

    var mBreedImagesDisposable = Disposables.disposed()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (mDogBreed == null && getParentActivity() != null) {
            Toast.makeText(getParentActivity(), R.string.oops_something_went_wrong, Toast.LENGTH_SHORT).show()
            getParentActivity()!!.finish()
            return
        }
        loadBreedImages()
    }

    fun loadBreedImages() {
        mBreedImagesDisposable = mApiManager
                .loadBreedImages(mDogBreed!!.mBreedName)
                .map { t: DogBreeds ->
                    t.mBreeds.mapTo(mItems) { DogBreed("", it) }
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ _ ->
                    notifyPropertyChanged(BR.breedDetailsViewModel)
                }, { t: Throwable ->
                    if (getParentActivity() != null) {
                        Toast.makeText(getParentActivity(), R.string.oops_something_went_wrong, Toast.LENGTH_SHORT).show()
                        getParentActivity()!!.finish()
                    }
                })
    }

    override fun onDestroy() {
        super.onDestroy()
        mBreedImagesDisposable.dispose()
    }
}