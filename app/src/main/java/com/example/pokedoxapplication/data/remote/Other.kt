package com.example.pokedoxapplication.data.remote

data class Other(
    val dream_world: DreamWorld,
    val home: Home,
    val official_artwork: OfficialArtwork
)

data class DreamWorld(
    val front_default: String,
    val front_female: Any
)

data class Home(
    val front_default: String,
    val front_female: Any,
    val front_shiny: String,
    val front_shiny_female: Any
)

data class OfficialArtwork(
    val front_default: String
)