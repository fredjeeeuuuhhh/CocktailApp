package com.example.cocktailapp.ui

import com.example.cocktailapp.model.Cocktail
import com.example.cocktailapp.model.Ingredient

sealed interface CocktailApiState{
    object Error: CocktailApiState
    object Loading: CocktailApiState
    data class Succes(val cocktails: List<Cocktail>): CocktailApiState
}

sealed interface IngredientApiState{
    object Error: IngredientApiState
    object Loading: IngredientApiState
    data class Succes(val ingredients: List<Ingredient>): IngredientApiState
}