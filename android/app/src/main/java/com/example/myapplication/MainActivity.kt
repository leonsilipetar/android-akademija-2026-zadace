// Zadaci 1–4 nalaze se u package-u "tasks"
// Notes aplikacija (Zadatak 2) nalazi se u navigation flowu
package com.example.myapplication


import androidx.compose.runtime.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.myapplication.notes.model.Note
import com.example.myapplication.notes.navigation.AppNavigation
import com.example.myapplication.tasks.components.TitleText
import com.example.myapplication.tasks.components.DescriptionText
import com.example.myapplication.tasks.components.CustomButton
import com.example.myapplication.tasks.model.MyData
import com.example.myapplication.tasks.ui.ItemCard
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun ListScreen(navController: NavController, items: List<MyData>) {

    var query by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        TextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Search") },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        )

        val filteredItems = items.filter {
            it.title.contains(query, ignoreCase = true)
        }

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(filteredItems) { item ->
                ItemCard(
                    data = item,
                    onClick = {
                        navController.navigate("detail/${item.id}")
                    }
                )
            }
        }
    }
}

@Composable
fun DetailScreen(
    navController: NavController,
    id: Int,
    items: List<MyData>
) {

    val item = items.find { it.id == id }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        CustomButton(text = "Back") {
            navController.popBackStack()
        }

        item?.let {
            TitleText(it.title)
            DescriptionText(it.description)
        }
    }
}