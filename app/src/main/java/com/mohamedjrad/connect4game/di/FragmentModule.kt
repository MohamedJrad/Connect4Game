package com.mohamedjrad.connect4game.di

import com.mohamedjrad.connect4game.ui.gameplay.GamePlayFragment
import com.mohamedjrad.connect4game.ui.home.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeGamePlayFragment(): GamePlayFragment

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment
}