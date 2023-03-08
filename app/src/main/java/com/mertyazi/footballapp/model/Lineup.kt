package com.mertyazi.footballapp.model

import java.io.Serializable

data class Lineup(
    val id: Int,
    val name: String,
    val position: String,
    val shirtNumber: Int
): Serializable