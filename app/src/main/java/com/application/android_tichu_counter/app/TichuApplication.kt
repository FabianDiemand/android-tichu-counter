package com.application.android_tichu_counter.app

import android.app.Application
import android.util.Log
import com.application.android_tichu_counter.domain.dagger.components.ApplicationComponent
import com.application.android_tichu_counter.domain.dagger.components.DaggerApplicationComponent.builder
import com.application.android_tichu_counter.domain.dagger.modules.AppModule

/**
 * Entry-point class for the application.
 * Offers singleton instance of the application.
 *
 * @author Devtronaut
 */
class TichuApplication : Application() {
    lateinit var appComponent: ApplicationComponent

    companion object {
        private const val TAG = "Application"

        // Single application instance
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

    private fun initDagger() = builder()
        .appModule(AppModule(this@TichuApplication))
        .build()
}