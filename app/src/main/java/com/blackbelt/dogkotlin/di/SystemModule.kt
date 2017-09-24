package com.blackbelt.dogkotlin.di

import android.content.Context
import android.content.res.Resources
import com.blackbelt.dogkotlin.DogApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SystemModule(private val dogApp: DogApp) {

    @Singleton
    @Provides
    fun provideContext(): Context = dogApp

    @Singleton
    @Provides
    fun provideResources(context: Context): Resources = context.getResources()
}