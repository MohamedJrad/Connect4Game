package com.mohamedjrad.a4inrowgame.di

import com.mohamedjrad.a4inrowgame.ui.gameplay.GamePlayFragment
import com.mohamedjrad.a4inrowgame.ui.home.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeGamePlayFragment(): GamePlayFragment

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment
}