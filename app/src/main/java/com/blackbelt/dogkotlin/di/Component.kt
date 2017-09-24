package com.blackbelt.dogkotlin.di

import com.blackbelt.dogkotlin.DogApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        AndroidSupportInjectionModule::class,
        BuildersModule::class,
        SystemModule::class,
        HelpersModule::class,
        ManagersModule::class))
interface DogComponent {

    fun inject(dogApp: DogApp)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: DogApp): Builder

        fun systemModule(systemModule: SystemModule): Builder

        fun managersModule(managersModule: ManagersModule): Builder

        fun helpersModule(helpersModule: HelpersModule): Builder

        fun build(): DogComponent
    }
}