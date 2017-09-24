package com.blackbelt.dogkotlin.di

import com.blackbelt.dogkotlin.api.ApiManager
import com.blackbelt.dogkotlin.api.IApiManager
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ManagersModule {

    @Singleton
    @Provides
    fun provideApiManager(gson: Gson) : IApiManager = ApiManager("https://dog.ceo/api/", gson)
}