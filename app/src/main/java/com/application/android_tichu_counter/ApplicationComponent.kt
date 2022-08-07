package com.application.android_tichu_counter

import com.application.android_tichu_counter.ui.activities.LoadGameActivity
import com.application.android_tichu_counter.ui.activities.MainActivity
import com.application.android_tichu_counter.ui.activities.ScoreboardActivity
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class])
@Singleton
interface ApplicationComponent {

    fun inject(activity: MainActivity)
    fun inject(activity: LoadGameActivity)
    fun inject(activity: ScoreboardActivity)
}