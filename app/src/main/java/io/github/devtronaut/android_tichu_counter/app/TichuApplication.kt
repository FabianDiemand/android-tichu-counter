package io.github.devtronaut.android_tichu_counter.app

import android.app.Application
import android.util.Log
import io.github.devtronaut.android_tichu_counter.domain.dagger.components.ApplicationComponent
import io.github.devtronaut.android_tichu_counter.domain.dagger.components.DaggerApplicationComponent
import io.github.devtronaut.android_tichu_counter.domain.dagger.modules.AppModule

/**
 * Entry-point class for the application.
 *
 * Copyright (C) 2022  Devtronaut
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * Find a copy of the GNU GPL in the root-level file "LICENCE".
 */
class TichuApplication : Application() {
    lateinit var appComponent: ApplicationComponent

    companion object {
        private const val TAG = "Application"

        // Single TichuApplication instance
        private lateinit var instance: TichuApplication

        /**
         * Get single instance of the application.
         *
         * @return instance of the application
         */
        // Required for PreferenceUtils to create the preferences for the application
        @Synchronized
        fun getInstance(): TichuApplication {
            return instance
        }
    }

    // Extend function to instantiate the single instance of the application
    override fun onCreate() {
        super.onCreate()
        instance = this
        this.appComponent = this.initDagger()

        Log.d(TAG, "Initialized application instance.")
    }

    // Required to make application, context and dispatchers injectable!
    // See ../domain/dagger/components & ../domain/dagger/modules
    private fun initDagger() = DaggerApplicationComponent.builder()
        .appModule(AppModule(this@TichuApplication))
        .build()
}