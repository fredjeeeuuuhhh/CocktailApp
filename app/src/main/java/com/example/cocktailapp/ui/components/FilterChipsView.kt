package com.example.cocktailapp.ui.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> FilterChipsView(filters: Map<String, (T) -> Boolean>, currentFilter: String, setFilter: (String) -> Unit, content: @Composable () -> Unit) {
    val scrollState = rememberScrollState()
    Column {
        Row(
            modifier = Modifier
                .horizontalScroll(scrollState)
                .padding(horizontal = 10.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            filters.forEach {
                FilterChip(
                    selected = currentFilter == it.key,
                    onClick = { setFilter(it.key) },
                    label = { Text(it.key, style = MaterialTheme.typography.bodyLarge) },
                )
            }
        }
        content()
    }
}
