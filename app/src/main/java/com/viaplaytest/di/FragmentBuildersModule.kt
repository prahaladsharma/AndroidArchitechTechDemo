package com.viaplaytest.di


import com.viaplaytest.ui.activities.ViaplaySectionListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeThemeFragment(): ViaplaySectionListFragment

}
