package com.mertyazi.footballapp.model

import java.io.Serializable

data class ResultSet(
    val competitions: String,
    val count: Int,
    val first: String,
    val last: String,
    val played: Int
): Serializable