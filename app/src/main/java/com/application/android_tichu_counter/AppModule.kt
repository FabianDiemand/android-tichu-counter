package com.application.android_tichu_counter

import android.app.Application
import android.content.Context
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