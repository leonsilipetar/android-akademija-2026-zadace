package com.example.myapplication.tasks.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp


@Composable
fun DescriptionText(text: String){
    Text(
        text = text,
        fontSize = 14.sp,
        maxLines = 3,
        lineHeight = 18.sp
    )
}