package com.blackbelt.dogkotlin.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("breeds/list")
    fun listAllBreeds(): Single<DogBreeds>

    @GET("breed/{breed_name}/images/random")
    fun randomBreedImage(@Path("breed_name") brandName: String): Single<DogRandomImage>;
}