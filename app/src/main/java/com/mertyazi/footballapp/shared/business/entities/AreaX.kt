package com.mertyazi.footballapp.shared.business.entities

data class AreaX(
    val code: String,
    val flag: String,
    val id: Int,
    val name: String
) {
    constructor(): this("", "", 0, "")
}