package com.mertyazi.footballapp.model

import java.io.Serializable

data class Competition(
    val code: String,
    val emblem: String,
    val id: Int,
    val name: String,
    val type: String
): Serializable