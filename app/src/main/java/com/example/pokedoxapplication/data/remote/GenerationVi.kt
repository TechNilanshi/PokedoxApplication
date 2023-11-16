package com.example.pokedoxapplication.data.remote

data class GenerationVi(
    val omegaruby_alphasapphire: OmegarubyAlphasapphire,
    val x_y: XY
)

data class OmegarubyAlphasapphire(
    val front_default: String,
    val front_female: Any,
    val front_shiny: String,
    val front_shiny_female: Any
)

data class XY(
    val front_default: String,
    val front_female: Any,
    val front_shiny: String,
    val front_shiny_female: Any
)