package com.mohamedjrad.a4inrowgame.di

import android.app.Application
import com.mohamedjrad.a4inrowgame.core.App
import com.mohamedjrad.a4inrowgame.ui.gameplay.GamePlayViewModel
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
    modules =
    [
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ViewModelModule::class,
        FragmentModule::class

    ]
)
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }


}