package com.example.myapplication.tasks.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.tasks.model.Task

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskCard(
    data: Task,
    onClick: () -> Unit = {},
    onLongClick: () -> Unit = {}
) {
    val bgColor = try {
        Color(android.graphics.Color.parseColor(data.color))
    } catch (e: Exception) {
        Color(0xFFFFEB3B)
    }

    val textDecoration = if (data.isCompleted) TextDecoration.LineThrough else null
    val mainTextColor = if (data.isCompleted) Color.Gray.copy(alpha = 0.8f) else Color(0xFF1C1B1F)
    val bodyTextColor = if (data.isCompleted) Color.Gray.copy(alpha = 0.7f) else Color(0xFF49454F)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            ),
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (data.isCompleted) 1.dp else 5.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            if (data.category.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .background(Color.Black.copy(alpha = 0.06f), RoundedCornerShape(4.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = data.category.uppercase(),
                        fontSize = 9.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 0.5.sp,
                        color = Color.Black.copy(alpha = 0.5f)
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
            }

            Text(
                text = data.title,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textDecoration = textDecoration,
                color = mainTextColor
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = data.body,
                fontSize = 14.sp,
                maxLines = 6,
                overflow = TextOverflow.Ellipsis,
                textDecoration = textDecoration,
                color = bodyTextColor,
                lineHeight = 18.sp
            )
        }
    }
}
