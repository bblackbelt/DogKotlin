package com.blackbelt.dogkotlin.api

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.os.Parcel
import android.os.Parcelable
import android.text.TextUtils
import android.view.View
import com.blackbelt.dogkotlin.BR
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DogBreed(val breedName: String, var breedImageUrl: String = "") : BaseObservable(), Parcelable {
    val mBreedName = breedName

    var mBreedImageUrl: String = breedImageUrl
        @Bindable get
        set(value) {
            breedImageUrl = value
            field = value
            notifyPropertyChanged(BR.mBreedImageUrl)
        }

    fun isTitleVisible() : Int {
        if (TextUtils.isEmpty(mBreedName)) {
            return View.GONE
        }
        return View.VISIBLE
    }

    constructor(source: Parcel) : this(
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(breedName)
        writeString(breedImageUrl)
    }

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<DogBreed> = object : Parcelable.Creator<DogBreed> {
            override fun createFromParcel(source: Parcel): DogBreed = DogBreed(source)
            override fun newArray(size: Int): Array<DogBreed?> = arrayOfNulls(size)
        }
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