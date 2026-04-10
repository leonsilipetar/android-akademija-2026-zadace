package com.example.myapplication.tasks.model

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.tasks.components.CustomButton
import com.example.myapplication.tasks.ui.ItemCard

@Composable
fun MyItemList() {

    var items by remember {
        mutableStateOf(
            listOf(
                MyData(1, "Naslov 1", "Opis 1"),
                MyData(2, "Naslov 2", "Opis 2"),
                MyData(3, "Naslov 3", "Opis 3"),
                MyData(4, "Naslov 4", "Opis 4"),
                MyData(5, "Naslov 5", "Opis 5")
            )
        )
    }
    Column {

        CustomButton(text = "Shuffle") {
            items = items.shuffled()
        }

        LazyColumn(
            modifier = Modifier.weight(1f)
        ){

            items(items) { item ->

                ItemCard(item)

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}