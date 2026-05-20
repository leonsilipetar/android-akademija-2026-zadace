package com.example.myapplication.zadaca

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.zadaca.components.CustomButton
import com.example.myapplication.zadaca.components.DescriptionText
import com.example.myapplication.zadaca.components.TitleText
import com.example.myapplication.zadaca.model.MyItemList

@Composable
fun PlaygroundScreen(onStartApp: () -> Unit) {

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(15.dp)) {

        TitleText("Zadaci 1–4")
        DescriptionText("Komponente + lista + state")

        CustomButton("Pokreni App") {
            onStartApp()
        }

        MyItemList()
    }
}