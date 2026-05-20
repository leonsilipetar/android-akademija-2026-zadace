package com.example.myapplication.tasks.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.tasks.model.Task
import com.example.myapplication.zadaca.components.DescriptionText
import com.example.myapplication.zadaca.components.TitleText

import java.text.SimpleDateFormat
import java.util.*

fun formatDate(timestamp: Long): String {

    val now = Calendar.getInstance()
    val noteDate = Calendar.getInstance().apply {
        timeInMillis = timestamp
    }

    val isToday =
        now.get(Calendar.YEAR) == noteDate.get(Calendar.YEAR) &&
                now.get(Calendar.DAY_OF_YEAR) == noteDate.get(Calendar.DAY_OF_YEAR)

    val format = if (isToday) {
        SimpleDateFormat("HH:mm", Locale.getDefault()) // samo vrijeme
    } else {
        SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()) // samo datum
    }

    return format.format(Date(timestamp))
}
@Composable
fun TaskCard(data: Task, onClick: () -> Unit = {}) {

    Card(
        modifier = Modifier
            .padding(0.dp, 8.dp )
            .fillMaxWidth()
            .clickable{ onClick() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                TitleText(data.title)
            }


            DescriptionText(data.body)

        }
    }
}
