package com.example.pokedoxapplication.data.remote

data class Move(
    val move: MoveX,
    val version_group_details: List<VersionGroupDetail>
)

data class MoveX(
    val name: String,
    val url: String
)