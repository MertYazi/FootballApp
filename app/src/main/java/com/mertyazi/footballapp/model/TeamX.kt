package com.mertyazi.footballapp.model

import java.io.Serializable

data class TeamX(
    val crest: String,
    val id: Int,
    val name: String,
    val shortName: String,
    val tla: String
): Serializable