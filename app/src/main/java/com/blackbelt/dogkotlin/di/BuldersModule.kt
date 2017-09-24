package com.blackbelt.dogkotlin.di

import com.blackbelt.dogkotlin.view.MainActivity
import com.blackbelt.dogkotlin.view.breeddetails.BreedDetailsActivity
import com.blackbelt.dogkotlin.view.breeddetails.di.BreedDetailsModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {

    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity;

    @ContributesAndroidInjector(modules = arrayOf(BreedDetailsModule::class))
    abstract fun breedDetailsActivity(): BreedDetailsActivity
}