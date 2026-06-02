package com.example.myapplication.tasks.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun TitleText(text: String, color: Color){
    Text(
        text = text,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp,
        color = color
    )
}