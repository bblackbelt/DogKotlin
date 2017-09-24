package com.blackbelt.dogkotlin.api

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.blackbelt.dogkotlin.BR
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DogBreed(breedName: String, breedImageUrl: String = "") : BaseObservable() {

    val mBreedName = breedName

    var mBreedImageUrl: String = breedImageUrl
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.mBreedImageUrl)
        }
}

class DogBreeds {

    @Expose
    @SerializedName("message")
    lateinit var mBreeds: List<String>
}

class DogRandomImage {
    @Expose
    @SerializedName("message")
    lateinit var mBreedImageUrl: String
}