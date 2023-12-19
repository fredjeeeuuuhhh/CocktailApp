package com.example.cocktailapp.ui.cocktails.cocktailoverview

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cocktailapp.R
import com.example.cocktailapp.model.Cocktail
import com.example.cocktailapp.ui.CocktailApiState
import com.example.cocktailapp.ui.cocktails.cocktailoverview.components.Cocktails
import com.example.cocktailapp.ui.cocktails.cocktailoverview.components.FilterChipsView
import eu.bambooapps.material3.pullrefresh.PullRefreshIndicator
import eu.bambooapps.material3.pullrefresh.pullRefresh
import eu.bambooapps.material3.pullrefresh.rememberPullRefreshState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CocktailScreen(
    cocktailOverviewViewModel: CocktailOverviewViewModel = viewModel(factory = CocktailOverviewViewModel.Factory),
    onViewDetailClicked: (Cocktail) -> Unit,
) {
    val cocktailOverviewState by cocktailOverviewViewModel.uiState.collectAsState()
    val cocktailApiState = cocktailOverviewViewModel.cocktailApiState
    val pullRefreshState = rememberPullRefreshState(cocktailOverviewState.isRefreshing, onRefresh = cocktailOverviewViewModel::refreshCocktails)
   Box(
        modifier = Modifier
            .pullRefresh(pullRefreshState)
            .fillMaxWidth(),
    ) {
       when(cocktailApiState){
           is CocktailApiState.Loading -> {
               Text("Loading cocktails")
           }
           is CocktailApiState.Error -> {
               Text("Something went wrong while loading tasks from api")
           }
           is CocktailApiState.Succes ->{
               SupplementsOverview(
                   cocktails = cocktailApiState.cocktails,
                   onViewDetailClicked = onViewDetailClicked,
               )
           }
       }

        PullRefreshIndicator(
            refreshing = cocktailOverviewState.isRefreshing,
            state = pullRefreshState,
            modifier = Modifier
                .padding(top = 20.dp)
                .align(Alignment.TopCenter),
        )

    }
}

@Composable
fun SupplementsOverview(
    cocktails: List<Cocktail>,
    onViewDetailClicked: (Cocktail) -> Unit
) {
    val filters = mutableMapOf<String, (Cocktail) -> Boolean>(
        stringResource(R.string.filter_all) to { true },
    )

    for (category in cocktails.map { it.category!! }.distinct())
        filters += category to { it.category == category }

    var currentFilter by rememberSaveable { mutableStateOf(filters.toList()[0].first) }

    FilterChipsView(filters, currentFilter, { currentFilter = it }) {
        Cocktails(
           cocktails.filter(filters[currentFilter]!!),
            onViewDetailClicked,
        )
    }
}
