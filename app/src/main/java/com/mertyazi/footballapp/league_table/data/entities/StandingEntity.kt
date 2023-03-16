package com.mertyazi.footballapp.league_table.data.entities

data class StandingEntity(
    val group: String,
    val stage: String,
    val table: List<TableEntity>,
    val type: String
)