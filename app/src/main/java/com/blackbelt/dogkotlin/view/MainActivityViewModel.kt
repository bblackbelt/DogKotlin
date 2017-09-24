package com.blackbelt.dogkotlin.view

import android.databinding.Bindable
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import android.os.Bundle
import com.blackbelt.dogkotlin.BR
import com.blackbelt.dogkotlin.R
import com.blackbelt.dogkotlin.api.DogBreed
import com.blackbelt.dogkotlin.api.DogBreeds
import com.blackbelt.dogkotlin.api.IApiManager
import com.blackbelt.dogkotlin.bindable.android.AndroidBaseItemBinder
import com.blackbelt.dogkotlin.bindable.android.DogBaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(apiManager: IApiManager) : DogBaseViewModel() {

    val mApiManager = apiManager

    var mItems: ObservableList<DogBreed> = ObservableArrayList()

    var mCompositeDisposable: CompositeDisposable = CompositeDisposable();

    val mTemplates: Map<Class<*>, AndroidBaseItemBinder>
        @Bindable get() = field

    private var mBreedsDisposable: Disposable = Disposables.disposed()

    init {
        mTemplates = hashMapOf(DogBreed::class.java to AndroidBaseItemBinder(BR.dogBreed, R.layout.breed_item))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadBreeds()
    }

    private fun loadBreeds() {
        mBreedsDisposable = mApiManager.listAllBreeds()
                .map { t: DogBreeds ->
                    for (breed: String in t.mBreeds) {
                        val dogBreed = DogBreed(breed)
                        decorateWithImage(dogBreed)
                        mItems.add(dogBreed)
                    }
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ t ->
                    run {
                        notifyPropertyChanged(BR.mainViewModel)
                    }
                }, { throwable ->
                    run {
                        throwable.printStackTrace();
                    }
                })
    }

    @Synchronized
    fun decorateWithImage(dogBreed: DogBreed) {
        mCompositeDisposable.add(mApiManager.randomBreedImage(dogBreed.mBreedName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ t ->
                    run {
                        dogBreed.mBreedImageUrl = t.mBreedImageUrl
                        notifyPropertyChanged(BR.mainViewModel)
                    }
                }, { throwable ->
                    run {
                        throwable.printStackTrace();
                    }
                }))
    }
}
