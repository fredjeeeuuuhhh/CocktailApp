package com.example.cocktailapp.ui.cocktails.cocktailoverview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cocktailapp.model.Cocktail
import com.example.cocktailapp.network.CocktailApiState
import com.example.cocktailapp.ui.cocktails.cocktailoverview.components.ChipSection
import com.example.cocktailapp.ui.cocktails.cocktailoverview.components.Cocktails

@Composable
fun CocktailOverview(
    cocktailOverviewViewModel: CocktailOverviewViewModel = hiltViewModel(),
    onViewDetailClicked: (Cocktail) -> Unit,
) {
    val cocktailApiState = cocktailOverviewViewModel.cocktailApiState
    val uiListState by cocktailOverviewViewModel.uiListState.collectAsState()
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

        when(cocktailApiState){
            is CocktailApiState.Loading -> {
                Text("Loading cocktails")
            }
            is CocktailApiState.Error -> {
                Text("Something went wrong while loading tasks from api")
            }
            is CocktailApiState.Success ->{
                Cocktails(
                    cocktails = uiListState,
                    onViewDetailClicked = onViewDetailClicked,
                )
            }
        }
    }
}



