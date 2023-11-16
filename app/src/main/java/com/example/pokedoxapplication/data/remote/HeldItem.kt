package com.example.pokedoxapplication.data.remote

data class HeldItem(
    val item: Item,
    val version_details: List<VersionDetail>
)

data class Item(
    val name: String,
    val url: String
)

data class VersionDetail(
    val rarity: Int,
    val version: Version
)