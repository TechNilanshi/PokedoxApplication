package com.example.pokedoxapplication.data.remote

data class PokemonTypes( val damage_relations: DamageRelations,
                         val name: String = "")

class DamageRelations(
    val double_damage_from: List<DoubleDamage>,
)

class DoubleDamage(
    val name: String = "",
    val url: String = ""
)