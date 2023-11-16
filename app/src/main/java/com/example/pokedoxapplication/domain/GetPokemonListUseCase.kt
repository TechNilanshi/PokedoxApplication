package com.example.pokedoxapplication.domain

import com.example.pokedoxapplication.data.model.PokemonListEntry
import com.example.pokedoxapplication.data.repository.PokedexRepository
import com.example.pokedoxapplication.utils.Constants.IMAGE_BASE_URL
import com.example.pokedoxapplication.utils.Resource
import java.util.Locale
import javax.inject.Inject

class GetPokemonListUseCase @Inject constructor(private val repository: PokedexRepository) {
    suspend fun getPokemonList(
        limit: Int,
        offset: Int,
    ): Resource<PokemonListEntry.PokedexList> {
        when (val result = repository.getPokemonList(limit, offset)) {
            is Resource.Success -> {
                val pokedexEntries = result.data?.results?.mapIndexed { _, entry ->
                    val number = if (entry.url.endsWith("/")) {
                        entry.url.dropLast(1).takeLastWhile { it.isDigit() }
                    } else {
                        entry.url.takeLastWhile { it.isDigit() }
                    }
                    val url =
                        "$IMAGE_BASE_URL$number.png"
                    PokemonListEntry.PokedexListEntry(
                        entry.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
                        url,
                        number.toInt(),
                    )
                }
                return Resource.Success(
                    PokemonListEntry.PokedexList(
                        results = pokedexEntries ?: listOf(),
                        count = result.data?.count ?: 0,
                    ),
                )
            }

            is Resource.Error -> {
                return Resource.Error(message = result.message ?: "")
            }

            is Resource.Loading -> {
                return Resource.Loading()
            }
        }
    }

    fun getSearchQueryResult(
        query: String,
        listToSearch: List<PokemonListEntry.PokedexListEntry>,
    ): List<PokemonListEntry.PokedexListEntry> {
        return listToSearch.filter {
            it.name.contains(query.trim(), ignoreCase = true) ||
                    it.number.toString() == query.trim()
        }
    }
}
