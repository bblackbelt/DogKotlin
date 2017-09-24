package com.blackbelt.dogkotlin.api

import io.reactivex.Single

interface IApiManager {

    fun listAllBreeds(): Single<DogBreeds>

    fun randomBreedImage(brandName: String): Single<DogRandomImage>;

    fun loadBreedImages(brandName: String): Single<DogBreeds>
}