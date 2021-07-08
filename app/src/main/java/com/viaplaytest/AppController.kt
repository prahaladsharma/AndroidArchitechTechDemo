package com.viaplaytest

import android.app.Activity
import android.app.Application
import com.facebook.stetho.Stetho
import com.viaplaytest.di.AppInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class AppController : Application(), HasActivityInjector {
    @Inject
    public lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) Stetho.initializeWithDefaults(this)

        AppInjector.init(this)
    }

    override fun activityInjector() = dispatchingAndroidInjector
}
