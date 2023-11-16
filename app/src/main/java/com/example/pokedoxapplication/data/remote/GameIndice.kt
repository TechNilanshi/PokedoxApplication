package com.example.pokedoxapplication.data.remote

data class GameIndice(
    val game_index: Int,
    val version: Version
)

data class Version(
    val name: String,
    val url: String
)