package com.mertyazi.footballapp.model

import java.io.Serializable

data class Score(
    val away: Int,
    val home: Int
): Serializable