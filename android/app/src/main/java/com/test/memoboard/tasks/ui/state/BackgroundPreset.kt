package com.test.memoboard.tasks.ui.state

import androidx.compose.ui.graphics.Color
import com.test.memoboard.R

data class BackgroundPreset(
    val id: String,
    val name: String,
    val primaryColor: Color,
    val secondaryColor: Color? = null,
    val imageResId: Int? = null,
    val isDark: Boolean = false
) {
    companion object {
        val Default = BackgroundPreset(
            id = "default",
            name = "Classic",
            primaryColor = Color(0xFFF8F9FA)
        )
        
        val Sunny = BackgroundPreset(
            id = "sunny",
            name = "Sunny",
            primaryColor = Color(0xFFFFEB3B),
            secondaryColor = Color(0xFFFBC02D)
        )
        
        val SoftLavender = BackgroundPreset(
            id = "lavender",
            name = "Lavender",
            primaryColor = Color(0xFFE1BEE7),
            secondaryColor = Color(0xFFCE93D8)
        )

        val SkyBlue = BackgroundPreset(
            id = "skyblue",
            name = "Sky",
            primaryColor = Color(0xFFBBDEFB),
            secondaryColor = Color(0xFF90CAF9)
        )

        val Midnight = BackgroundPreset(
            id = "midnight",
            name = "Midnight",
            primaryColor = Color(0xFF263238),
            secondaryColor = Color(0xFF000000),
            isDark = true
        )

        val Corkboard = BackgroundPreset(
            id = "corkboard",
            name = "Pluto",
            primaryColor = Color.Transparent,
            imageResId = R.drawable.bg_corkboard,
            isDark = true
        )

        val PaperLines = BackgroundPreset(
            id = "paperlines",
            name = "Paper Lines",
            primaryColor = Color.Transparent,
            imageResId = R.drawable.bg_paper
        )

        val Paper = BackgroundPreset(
            id = "paper",
            name = "Paper",
            primaryColor = Color.Transparent,
            imageResId = R.drawable.bg_paper2
        )

        val Chalkboard = BackgroundPreset(
            id = "chalkboard",
            name = "Chalk Board",
            primaryColor = Color.Transparent,
            imageResId = R.drawable.bg_chalkboard,
            isDark = true
        )
        
        val allPresets = listOf(Default, Sunny, SoftLavender, SkyBlue, Midnight, Corkboard, Paper, PaperLines, Chalkboard)
        
        fun getById(id: String?) = allPresets.find { it.id == id } ?: Default
    }
}
