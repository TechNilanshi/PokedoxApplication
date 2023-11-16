package com.example.pokedoxapplication.data.remote

data class PokemonSpecies(
val egg_groups: List<EggGroups>,
val base_happiness: Int,
val evolution_chain: EvolutionChain,
val flavor_text_entries: List<FlavorTextEntries>
)

class EggGroups (
    val name: String,
    val url: String
)

class EvolutionChain (
    val evolution_chain: String
)

class FlavorTextEntries (
    val flavor_text:String = ""
)