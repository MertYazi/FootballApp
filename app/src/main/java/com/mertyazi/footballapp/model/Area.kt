package com.mertyazi.footballapp.model

import java.io.Serializable

data class Area(
    val code: String,
    val flag: String,
    val id: Int,
    var name: String
): Serializable