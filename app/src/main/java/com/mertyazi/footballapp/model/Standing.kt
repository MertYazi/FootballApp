package com.mertyazi.footballapp.model

import java.io.Serializable

data class Standing(
    val group: String,
    val stage: String,
    val table: List<Table>,
    val type: String
): Serializable