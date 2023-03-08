package com.mertyazi.footballapp.model

import java.io.Serializable

data class SeasonX(
    val currentMatchday: Int,
    val endDate: String,
    val id: Int,
    val startDate: String,
    val winner: String
): Serializable