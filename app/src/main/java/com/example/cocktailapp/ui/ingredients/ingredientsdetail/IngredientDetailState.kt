package com.example.cocktailapp.ui.ingredients.ingredientsdetail

import com.example.cocktailapp.model.Cocktail
import com.example.cocktailapp.model.Ingredient

data class IngredientDetailState(
    val currentIngredient: Ingredient?,
    val cocktailsContainingIngredient: List<Cocktail>?,
)
