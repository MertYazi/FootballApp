package com.mertyazi.footballapp.model

import java.io.Serializable

data class Season(
    val currentMatchday: Int,
    val endDate: String,
    val id: Int,
    val stages: List<String>,
    val startDate: String,
    val winner: Any
): Serializable