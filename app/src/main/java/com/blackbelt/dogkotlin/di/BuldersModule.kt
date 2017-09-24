package com.blackbelt.dogkotlin.di

import com.blackbelt.dogkotlin.view.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {

    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity;
}