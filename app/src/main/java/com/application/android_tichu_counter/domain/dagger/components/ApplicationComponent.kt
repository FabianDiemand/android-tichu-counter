package com.application.android_tichu_counter.domain.dagger.components

import com.application.android_tichu_counter.domain.dagger.modules.AppModule
import com.application.android_tichu_counter.domain.dagger.modules.DispatcherModule
import com.application.android_tichu_counter.ui.activities.LoadGameActivity
import com.application.android_tichu_counter.ui.activities.MainActivity
import com.application.android_tichu_counter.ui.activities.ScoreboardActivity
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        AppModule::class,
        DispatcherModule::class
    ]
)
@Singleton
interface ApplicationComponent {

    fun inject(activity: MainActivity)
    fun inject(activity: LoadGameActivity)
    fun inject(activity: ScoreboardActivity)
}