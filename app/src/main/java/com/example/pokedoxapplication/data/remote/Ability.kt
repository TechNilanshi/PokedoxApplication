package com.example.pokedoxapplication.data.remote

data class Ability(
    val ability: AbilityX,
    val is_hidden: Boolean,
    val slot: Int
)

data class AbilityX(
    val name: String,
    val url: String
)