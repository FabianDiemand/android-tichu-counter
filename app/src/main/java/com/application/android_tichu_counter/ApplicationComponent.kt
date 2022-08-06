package com.application.android_tichu_counter

import com.application.android_tichu_counter.ui.activities.LoadGameActivity
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class])
@Singleton
interface ApplicationComponent {

    fun inject(activity: LoadGameActivity)
}