package com.mertyazi.footballapp.league_table.business.entities

data class Standing(
    val stage: String,
    val table: List<Table>
)