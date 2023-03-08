package com.mertyazi.footballapp.model

import java.io.Serializable

data class MatchesResponse(
    val filters: Filters,
    val matches: MutableList<Matche>,
    val resultSet: ResultSet
): Serializable