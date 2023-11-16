package com.example.pokedoxapplication.di

import com.example.pokedoxapplication.data.remote.PokemonApi
import com.example.pokedoxapplication.data.repository.PokedexRepository
import com.example.pokedoxapplication.data.repository.PokedexRepositoryImpl
import com.example.pokedoxapplication.domain.GetPokemonDetailsUseCase
import com.example.pokedoxapplication.domain.GetPokemonListUseCase
import com.example.pokedoxapplication.utils.Constants.BASE_URL
import com.example.pokedoxapplication.utils.HTTPLogger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun providePokemonRepository(
        api: PokemonApi
    ) = PokedexRepositoryImpl(api)

    @Singleton
    @Provides
    fun providePokeApi(): PokemonApi {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(HTTPLogger.getLogger())
            .build()
            .create(PokemonApi::class.java)
    }

    @Provides
    fun provideGetPokemonListUseCase(pokedexRepository: PokedexRepository): GetPokemonListUseCase {
        return GetPokemonListUseCase(pokedexRepository)
    }

    @Provides
    fun provideGetPokemonDetailsUseCase(pokedexRepository: PokedexRepository): GetPokemonDetailsUseCase {
        return GetPokemonDetailsUseCase(pokedexRepository)
    }
}