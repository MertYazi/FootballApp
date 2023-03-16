package com.mertyazi.footballapp.shared.data.entities

data class GoalEntity(
    val minute: Int,
    val score: ScoreEntity,
    val scorer: ScorerEntity,
    val team: TeamEntity,
    val type: String
)