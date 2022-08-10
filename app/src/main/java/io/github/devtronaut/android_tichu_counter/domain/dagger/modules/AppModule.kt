package io.github.devtronaut.android_tichu_counter.domain.dagger.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import io.github.devtronaut.android_tichu_counter.app.TichuApplication
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