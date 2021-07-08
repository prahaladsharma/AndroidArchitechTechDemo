package com.viaplaytest.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.viaplaytest.ui.viewmodel.ViaPlayViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ViaPlayViewModel::class)
    abstract fun bindThemeViewModel(viewModel: ViaPlayViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
