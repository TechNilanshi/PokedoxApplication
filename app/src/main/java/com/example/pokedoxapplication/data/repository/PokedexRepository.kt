package com.example.pokedoxapplication.data.repository

import com.example.pokedoxapplication.data.remote.Pokemon
import com.example.pokedoxapplication.data.remote.PokemonList
import com.example.pokedoxapplication.data.remote.PokemonSpecies
import com.example.pokedoxapplication.data.remote.PokemonTypes
import com.example.pokedoxapplication.utils.Resource


interface PokedexRepository {
    suspend fun getPokemonList(limit: Int, offset: Int): Resource<PokemonList>
    suspend fun getPokemonInfo(pokemonName: String): Resource<Pokemon>
    suspend fun getPokemonSpecies(id: Int): Resource<PokemonSpecies>
    suspend fun getPokemonType(id: Int): Resource<PokemonTypes>
}
