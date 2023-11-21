package com.example.cocktailapp.ui.cocktails.cocktailoverview

import com.example.cocktailapp.model.Cocktail

data class CocktailOverviewState(
    val currentCocktailList: List<Cocktail>,
    val currentFilters:List<String>,
)
