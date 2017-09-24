package com.blackbelt.dogkotlin.view.breeddetails.di

import com.blackbelt.dogkotlin.api.DogBreed
import com.blackbelt.dogkotlin.view.breeddetails.BreedDetailsActivity
import com.blackbelt.dogkotlin.view.breeddetails.DOG_BREED_KEY
import dagger.Module
import dagger.Provides
import javax.annotation.Nullable

@Module
class BreedDetailsModule {

    @Provides
    @Nullable
    fun provideDogBreed(activity: BreedDetailsActivity): DogBreed? {
        return activity.intent.getParcelableExtra(DOG_BREED_KEY);
    }
}