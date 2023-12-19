package com.example.cocktailapp.ui.ingredients.ingredientsoverview

import com.example.cocktailapp.model.Ingredient

data class IngredientsOverviewState(
    val isRefreshing: Boolean = false,
    val currentIngredientList: List<Ingredient>?,
)
