package io.github.devtronaut.android_tichu_counter.domain.dagger.components

import dagger.Component
import io.github.devtronaut.android_tichu_counter.domain.dagger.modules.AppModule
import io.github.devtronaut.android_tichu_counter.domain.dagger.modules.DispatcherModule
import io.github.devtronaut.android_tichu_counter.ui.activities.LoadGameActivity
import io.github.devtronaut.android_tichu_counter.ui.activities.MainActivity
import io.github.devtronaut.android_tichu_counter.ui.activities.ScoreboardActivity
import javax.inject.Singleton

/**
 * Interface defines the application graph for Dagger injections.
 *
 * Copyright (C) 2022 Devtronaut
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * Find a copy of the GNU AGPLv3 in the root-level file "LICENCE".
 */
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