package io.github.devtronaut.android_tichu_counter.domain.dagger.components

import dagger.Component
import io.github.devtronaut.android_tichu_counter.domain.dagger.modules.AppModule
import io.github.devtronaut.android_tichu_counter.domain.dagger.modules.DispatcherModule
import io.github.devtronaut.android_tichu_counter.ui.activities.LoadGameActivity
import io.github.devtronaut.android_tichu_counter.ui.activities.MainActivity
import io.github.devtronaut.android_tichu_counter.ui.activities.ScoreboardActivity
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