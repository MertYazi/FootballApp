package com.mertyazi.footballapp.model

import java.io.Serializable

data class Coach(
    val id: Int,
    val name: String,
    val nationality: String
): Serializable