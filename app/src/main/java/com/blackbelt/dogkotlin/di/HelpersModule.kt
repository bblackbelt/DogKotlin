package com.blackbelt.dogkotlin.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class HelpersModule {

    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
}