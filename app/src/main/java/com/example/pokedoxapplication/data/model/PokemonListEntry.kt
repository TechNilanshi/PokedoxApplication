package com.example.pokedoxapplication.data.model

class PokemonListEntry {
    data class PokedexList(
        val count: Int,
        val results: List<PokedexListEntry>,
    )

    data class PokedexListEntry(
        val name: String = "",
        val url: String = "",
        val number: Int = 1,
    )
}