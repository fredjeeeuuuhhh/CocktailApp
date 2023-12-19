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

sealed interface CocktailDetailApiState{
    object Error:CocktailDetailApiState
    object Loading: CocktailDetailApiState
    data class Succes(val cocktail: Cocktail): CocktailDetailApiState
}

sealed interface IngredientDetailApiState{
    object Error: IngredientDetailApiState
    object Loading: IngredientDetailApiState
    data class Succes(val ingredient:Ingredient,val cocktailsContainingIngredient: List<Cocktail>): IngredientDetailApiState
}