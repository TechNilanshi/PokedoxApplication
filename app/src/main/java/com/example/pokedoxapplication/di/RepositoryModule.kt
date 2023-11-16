package com.example.pokedoxapplication.di

import com.example.pokedoxapplication.data.repository.PokedexRepository
import com.example.pokedoxapplication.data.repository.PokedexRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindCountryListRepository(
        pokedexRepositoryImpl: PokedexRepositoryImpl,
    ): PokedexRepository
}