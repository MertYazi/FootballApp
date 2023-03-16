package com.mertyazi.footballapp.shared.presentation

import com.mertyazi.footballapp.shared.business.entities.AreaX

data class CompetitionXViewState(
    val area: AreaX?,
    val code: String,
    var emblem: String,
    val id: Int,
    var name: String
) {
    constructor(): this(AreaX(), "", "", 0, "")
}