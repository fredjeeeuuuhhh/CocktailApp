package com.example.cocktailapp.ui.cocktails.cocktailoverview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.SelectableChipColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.cocktailapp.model.Cocktail
import com.example.cocktailapp.ui.cocktails.cocktailoverview.components.Cocktails

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CocktailOverview(
    cocktailOverviewViewModel: CocktailOverviewViewModel = CocktailOverviewViewModel(),
    onViewDetailClicked: (Cocktail) -> Unit,
) {
    val cocktailOverviewState by cocktailOverviewViewModel.uiState.collectAsState()
    var selected by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxSize(),
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.DarkGray),
        ){
            FilterChip(
                selected = selected,
                onClick = { selected = !selected },
                label = {
                    Text("Owned")
                },
                colors = FilterChipDefaults.filterChipColors(
                    iconColor = Color.Black,
                    containerColor = Color.LightGray,
                ),
                leadingIcon = if (selected) {
                    {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Done icon",
                            modifier = Modifier.size(FilterChipDefaults.IconSize)
                        )
                    }
                } else {
                    null
                },
            )
        }
        Cocktails(
            cocktails = cocktailOverviewState.currentCocktailList!!,
            onViewDetailClicked = onViewDetailClicked,
        )
    }

}
