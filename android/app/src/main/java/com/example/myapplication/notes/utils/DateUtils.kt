package com.example.myapplication.notes.utils

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
        SimpleDateFormat("HH:mm", Locale.getDefault())
    } else {
        SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    }

    return format.format(Date(timestamp))
}