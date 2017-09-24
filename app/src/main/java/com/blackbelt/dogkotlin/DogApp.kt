package com.blackbelt.dogkotlin

import android.app.Activity
import android.app.Application
import com.blackbelt.dogkotlin.di.*
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class DogApp : Application(), HasActivityInjector {

    companion object {
        @JvmStatic
        lateinit var dogComponent: DogComponent
    }

    @Inject
    lateinit var mAndroidDispatchingInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        dogComponent = DaggerDogComponent
                .builder()
                .application(this)
                .systemModule(SystemModule(this))
                .managersModule(ManagersModule())
                .helpersModule(HelpersModule())
                .build();
        dogComponent.inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = mAndroidDispatchingInjector;
}