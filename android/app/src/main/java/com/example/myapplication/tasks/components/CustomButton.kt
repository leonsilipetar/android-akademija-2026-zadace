package com.example.myapplication.tasks.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun CustomButton(text: String, onClick: () -> Unit = {}) {
    Button(
        onClick = onClick,
        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
            containerColor = androidx.compose.ui.graphics.Color.Black,
            contentColor = androidx.compose.ui.graphics.Color.White
        )
    ) {
        Text(text = text)
    }
}