package com.blackbelt.dogkotlin.api

import com.google.gson.Gson
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiManager(baseUrl: String, gson: Gson) : IApiManager {

    var mApiService: ApiService;

    init {
        mApiService = getApiService(baseUrl, gson)
    }


    private fun getApiService(baseUrl: String, gson: Gson): ApiService {
        val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(getHttpClient())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()
        return retrofit.create<ApiService>(ApiService::class.java)
    }

    private fun getHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor();
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC;
        return OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()
    }

    override fun listAllBreeds(): Single<DogBreeds> = mApiService.listAllBreeds()

    override fun randomBreedImage(brandName: String): Single<DogRandomImage> = mApiService.randomBreedImage(brandName)
}