package com.example.model

import androidx.annotation.DrawableRes

data class ArchitecturalView(
    val id: String,
    val title: String,
    val category: String,
    @DrawableRes val drawableRes: Int,
    val headline: String,
    val description: String,
    val designHighlights: List<String>,
    val architecturalSpecs: List<Pair<String, String>>,
    val atmosphereNotes: String,
    val lightingMood: String
)
