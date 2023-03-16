package com.mertyazi.footballapp.shared.data.entities

data class MatchesEntity(
    val filters: FiltersEntity,
    val matches: MutableList<MatcheEntity>,
    val resultSet: ResultSetEntity
)