package com.example.pokedoxapplication.data.remote


data class GenerationVii(
    val icons: Icons,
    val ultra_sun_ultra_moon: UltraSunUltraMoon
)

data class UltraSunUltraMoon(
    val front_default: String,
    val front_female: Any,
    val front_shiny: String,
    val front_shiny_female: Any
)

data class Icons(
    val front_default: String,
    val front_female: Any
)