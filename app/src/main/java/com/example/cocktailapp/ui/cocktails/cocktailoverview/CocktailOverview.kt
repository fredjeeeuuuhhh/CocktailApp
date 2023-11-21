package com.example.cocktailapp.ui.cocktails.cocktailoverview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cocktailapp.model.Cocktail
import com.example.cocktailapp.ui.cocktails.cocktailoverview.components.ChipSection
import com.example.cocktailapp.ui.cocktails.cocktailoverview.components.Cocktails

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
       ChipSection(
           onFilterRequest = {filters -> cocktailOverviewViewModel.setFilters(filters)}
       )
        Divider(
            Modifier,
            2.dp,
            MaterialTheme.colorScheme.outline
        )
        Cocktails(
            cocktails = cocktails,
            onViewDetailClicked = onViewDetailClicked,
        )
    }
}



