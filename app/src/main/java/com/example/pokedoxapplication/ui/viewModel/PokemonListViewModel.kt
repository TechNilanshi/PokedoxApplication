package com.example.pokedoxapplication.ui.viewModel

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.example.pokedoxapplication.data.model.PokemonListEntry
import com.example.pokedoxapplication.domain.GetPokemonListUseCase
import com.example.pokedoxapplication.utils.Constants.PAGE_SIZE
import com.example.pokedoxapplication.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * This is viewmodel class for providing the data for pokemon details
 */
@HiltViewModel
class PokemonListViewModel @Inject constructor(private val pokemonListUseCase: GetPokemonListUseCase) :
    ViewModel() {

    private var curPage = 0

    var pokemonList = mutableStateOf<List<PokemonListEntry.PokedexListEntry>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)

    private var cachedPokemonList = listOf<PokemonListEntry.PokedexListEntry>()
    private var isSearchStarting = true
    var isSearching = mutableStateOf(false)

    init {
        loadPokemonPaginated()
    }

    /**
     * This method is responsible for providing pokemon search details by its id or name
     * @param query - id or name
     */
    fun searchPokemonList(query: String) {
        val listToSearch = if (isSearchStarting) {
            pokemonList.value
        } else {
            cachedPokemonList
        }
        // Coroutines for background thread
        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()) {
                pokemonList.value = cachedPokemonList
                isSearching.value = false
                isSearchStarting = true
                return@launch
            }
            val results = listToSearch.filter {
                it.name.contains(query.trim(), ignoreCase = true) ||
                        it.number.toString() == query.trim()
            }
            if (isSearchStarting) {
                cachedPokemonList = pokemonList.value
                isSearchStarting = false
            }
            pokemonList.value = results
            isSearching.value = true
        }
    }

    /**
     * This method is responsible for providing pokemon list while scrolling
     */
    fun loadPokemonPaginated() {
        viewModelScope.launch {
            val result = pokemonListUseCase.getPokemonList(PAGE_SIZE, curPage * PAGE_SIZE)
            when (result) {
                is Resource.Success -> {
                    endReached.value = curPage * PAGE_SIZE >= result.data!!.count
                    curPage++
                    loadError.value = ""
                    isLoading.value = false
                    pokemonList.value += result.data.results
                }

                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }

                is Resource.Loading -> {
                    isLoading.value = true
                }
            }
        }
    }
    /**
     * This method is responsible for providing pokemon color details
     * @param drawable - drawable
     * @param onFinish - returns the color value
     */
    fun calcDominantColor(drawable: Drawable, onFinish: (Color) -> Unit) {
        val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)
        Palette.from(bmp).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { colorValue ->
                onFinish(Color(colorValue))
            }
        }
    }
}