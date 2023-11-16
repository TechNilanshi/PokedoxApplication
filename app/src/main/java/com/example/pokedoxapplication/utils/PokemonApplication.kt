package com.example.pokedoxapplication.utils

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * Application base class for the Pokedox
 */
@HiltAndroidApp
class PokemonApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}