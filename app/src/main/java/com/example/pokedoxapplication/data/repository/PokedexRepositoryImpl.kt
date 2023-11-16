package com.example.pokedoxapplication.data.repository

import com.example.pokedoxapplication.data.remote.Pokemon
import com.example.pokedoxapplication.data.remote.PokemonApi
import com.example.pokedoxapplication.data.remote.PokemonList
import com.example.pokedoxapplication.data.remote.PokemonSpecies
import com.example.pokedoxapplication.data.remote.PokemonTypes
import com.example.pokedoxapplication.utils.Resource
import javax.inject.Inject

class PokedexRepositoryImpl @Inject constructor(
    private val api: PokemonApi,
) : PokedexRepository {
    override suspend fun getPokemonList(limit: Int, offset: Int): Resource<PokemonList> {
        val response = try {
            api.getPokemonList(limit, offset)
        } catch (e: Exception) {
            return Resource.Error("An unknown error occured.")
        }
        return Resource.Success(response)
    }

    override suspend fun getPokemonInfo(pokemonName: String): Resource<Pokemon> {
        val response = try {
            api.getPokemonInfo(pokemonName)
        } catch (e: Exception) {
            return Resource.Error("An unknown error occured.")
        }
        return Resource.Success(response)
    }

    override suspend fun getPokemonSpecies(id: Int): Resource<PokemonSpecies> {
        val response = try {
            api.getPokemonSpecies(id)
        } catch (e: Exception) {
            return Resource.Error("An unknown error occured.")
        }
        return Resource.Success(response)
    }

    override suspend fun getPokemonType(id: Int) : Resource<PokemonTypes> {
        val response = try {
            api.getPokemonType(id)
        } catch (e: Exception) {
            return Resource.Error("An unknown error occured.")
        }
        return Resource.Success(response)
    }
}
