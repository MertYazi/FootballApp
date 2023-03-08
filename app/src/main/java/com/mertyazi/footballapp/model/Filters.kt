package com.mertyazi.footballapp.model

import java.io.Serializable

data class Filters(
    val dateFrom: String,
    val dateTo: String,
    val permission: String
): Serializable