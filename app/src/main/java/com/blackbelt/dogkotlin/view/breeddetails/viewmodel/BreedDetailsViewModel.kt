package com.blackbelt.dogkotlin.view.breeddetails.viewmodel

import android.content.Intent
import android.databinding.Bindable
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.view.ViewCompat
import android.view.View
import android.widget.Toast
import com.blackbelt.dogkotlin.BR
import com.blackbelt.dogkotlin.R
import com.blackbelt.dogkotlin.api.DogBreed
import com.blackbelt.dogkotlin.api.DogBreeds
import com.blackbelt.dogkotlin.api.IApiManager
import com.blackbelt.dogkotlin.bindable.android.AndroidBaseItemBinder
import com.blackbelt.dogkotlin.bindable.android.DogBaseViewModel
import com.blackbelt.dogkotlin.bindable.android.RecyclerViewClickListener
import com.blackbelt.dogkotlin.view.photo.FULL_SCREEN_PHOTO_INDEX
import com.blackbelt.dogkotlin.view.photo.FULL_SCREEN_PHOTO_KEY
import com.blackbelt.dogkotlin.view.photo.PhotoActivity
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

    fun getItemClickListener(): RecyclerViewClickListener {
        return object : RecyclerViewClickListener {
            override fun onItemClick(view: View, any: Any) {
                ViewCompat.setTransitionName(view, mItems.indexOf(any).toString())

                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getParentActivity(),
                        view,
                        mItems.indexOf(any).toString())

                val intent = Intent(getParentActivity(), PhotoActivity::class.java)
                intent.putExtra(FULL_SCREEN_PHOTO_INDEX, mItems.indexOf(any).toString())
                intent.putExtra(FULL_SCREEN_PHOTO_KEY, (any as DogBreed).breedImageUrl)
                getParentActivity()?.startActivity(intent, options.toBundle())
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        mBreedImagesDisposable.dispose()
    }
}