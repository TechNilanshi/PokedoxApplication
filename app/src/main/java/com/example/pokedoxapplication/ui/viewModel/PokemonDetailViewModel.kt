package com.example.pokedoxapplication.ui.viewModel

import androidx.lifecycle.ViewModel
import com.example.pokedoxapplication.data.remote.Pokemon
import com.example.pokedoxapplication.data.remote.PokemonSpecies
import com.example.pokedoxapplication.data.remote.PokemonTypes
import com.example.pokedoxapplication.domain.GetPokemonDetailsUseCase
import com.example.pokedoxapplication.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * This is view model class for providing the data for pokemon details
 */
@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val pokemonDetailUseCase: GetPokemonDetailsUseCase
): ViewModel() {

    /**
     * This method is responsible for providing pokemon information by its name
     * @param pokemonName - name of the pokemon
     */
    suspend fun getPokemonInfo(pokemonName: String): Resource<Pokemon> {
        return pokemonDetailUseCase.getPokemonInfo(pokemonName)
    }

    /**
     * This method is responsible for providing pokemon species details by its id
     * @param id - id of the pokemon
     */
    suspend fun getPokemonSpecies(id:Int): Resource<PokemonSpecies>{
        return pokemonDetailUseCase.getPokemonSpecies(id)
    }

    /**
     * This method is responsible for providing pokemon species type by its id
     * @param id - id of the pokemon
     */
    suspend fun getPokemonType(id: Int):  Resource<PokemonTypes>{
        return pokemonDetailUseCase.getPokemonType(id)
    }
}