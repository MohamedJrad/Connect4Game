package com.mohamedjrad.connect4game.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mohamedjrad.connect4game.ui.gameplay.GamePlayViewModel
import com.mohamedjrad.connect4game.ui.home.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(GamePlayViewModel::class)
    abstract fun bindGamePlayViewModel(viewModel:GamePlayViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}