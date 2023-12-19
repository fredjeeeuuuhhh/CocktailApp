package com.example.cocktailapp.ui.cocktails.cocktailoverview

import com.example.cocktailapp.model.Cocktail

data class CocktailOverviewState(
    val isRefreshing: Boolean = false,
    val currentCocktailList: List<Cocktail>?,
)
