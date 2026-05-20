package com.example.myapplication.zadaca.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.zadaca.components.CustomButton
import com.example.myapplication.zadaca.components.DescriptionText
import com.example.myapplication.zadaca.components.TitleText
import com.example.myapplication.zadaca.model.MyData

@Composable
fun ItemCard(data: MyData, onClick: () -> Unit = {}) {

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable{ onClick() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {


            TitleText(data.title)
            DescriptionText(data.description)

            Row(modifier = Modifier.padding(top = 8.dp)) {

                CustomButton(text = "Edit")

                Spacer(modifier = Modifier.width(8.dp))

                CustomButton(text = "Save")
            }
        }
    }
}
