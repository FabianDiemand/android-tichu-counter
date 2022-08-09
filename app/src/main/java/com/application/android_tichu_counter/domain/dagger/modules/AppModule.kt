package com.application.android_tichu_counter.domain.dagger.modules

import android.content.Context
import com.application.android_tichu_counter.app.TichuApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: TichuApplication) {

    @Provides
    @Singleton
    fun providesApplication(): TichuApplication = application

    @Provides
    @Singleton
    fun providesApplicationContext(): Context = application
}