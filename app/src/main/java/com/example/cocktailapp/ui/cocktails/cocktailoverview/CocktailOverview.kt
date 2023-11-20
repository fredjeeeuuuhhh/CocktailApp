package com.example.cocktailapp.ui.cocktails.cocktailoverview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cocktailapp.model.Cocktail
import com.example.cocktailapp.ui.cocktails.cocktailoverview.components.CocktailFilterChip
import com.example.cocktailapp.ui.cocktails.cocktailoverview.components.Cocktails

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun CocktailOverview(
    cocktailOverviewViewModel: CocktailOverviewViewModel = CocktailOverviewViewModel(),
    onViewDetailClicked: (Cocktail) -> Unit,
) {
    val cocktailOverviewState by cocktailOverviewViewModel.uiState.collectAsState()
    val cocktails = cocktailOverviewState.currentCocktailList
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer),
            horizontalArrangement = Arrangement.SpaceAround,

        ) {
            CocktailFilterChip(label = "Favorites",{ flag -> cocktailOverviewViewModel.favoritesValueChanged(flag)})
            CocktailFilterChip(label = "Alcohol",{ flag -> cocktailOverviewViewModel.AlcoholValueChanged(flag)})
            CocktailFilterChip(label = "Non Alcohol",{ flag -> cocktailOverviewViewModel.NonAlcoholValueChanged(flag)})
            //CocktailFilterChip(label = "Category",{ flag -> cocktailOverviewViewModel.favoritesValueChanged(flag)})
            //CocktailFilterChip(label = "Glass",{ flag -> cocktailOverviewViewModel.favoritesValueChanged(flag)})
        }
        Divider(Modifier, 2.dp, MaterialTheme.colorScheme.outline)
        Cocktails(
            cocktails = cocktails,
            onViewDetailClicked = onViewDetailClicked,
        )
    }
}


