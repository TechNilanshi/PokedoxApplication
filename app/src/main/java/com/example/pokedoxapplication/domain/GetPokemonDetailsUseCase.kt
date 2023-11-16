package com.example.pokedoxapplication.domain

import com.example.pokedoxapplication.data.remote.Pokemon
import com.example.pokedoxapplication.data.remote.PokemonSpecies
import com.example.pokedoxapplication.data.remote.PokemonTypes
import com.example.pokedoxapplication.data.repository.PokedexRepository
import com.example.pokedoxapplication.utils.Resource
import javax.inject.Inject

class GetPokemonDetailsUseCase@Inject constructor(private val repository: PokedexRepository) {
    suspend fun getPokemonInfo(pokemonName: String): Resource<Pokemon> {
        return repository.getPokemonInfo(pokemonName)
    }

    suspend fun getPokemonSpecies(id: Int): Resource<PokemonSpecies> {
        return repository.getPokemonSpecies(id)
    }

    suspend fun getPokemonType(id: Int):  Resource<PokemonTypes>{
        return repository.getPokemonType(id)
    }
}
