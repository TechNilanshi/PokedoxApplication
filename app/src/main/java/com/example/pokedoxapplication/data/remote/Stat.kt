package com.example.pokedoxapplication.data.remote

data class Stat(
    val base_stat: Int,
    val effort: Int,
    val stat: StatX
)

data class StatX(
    val name: String,
    val url: String
)