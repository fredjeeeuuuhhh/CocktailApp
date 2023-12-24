package com.example.cocktailapp.network

import com.example.cocktailapp.model.Cocktail
import com.example.cocktailapp.model.Ingredient

sealed interface CocktailApiState {
    object Error : CocktailApiState
    object Loading : CocktailApiState
    class Success(cocktails: List<Cocktail>) : CocktailApiState
}

sealed interface IngredientApiState {
    object Error : IngredientApiState
    object Loading : IngredientApiState
    class Success(ingredients: List<Ingredient>) : IngredientApiState
}

sealed interface CocktailDetailApiState {
    object Error : CocktailDetailApiState
    object Loading : CocktailDetailApiState
    class Success(cocktail: Cocktail) : CocktailDetailApiState
}

sealed interface IngredientDetailApiState {
    object Error : IngredientDetailApiState
    object Loading : IngredientDetailApiState
    class Success(ingredient: Ingredient) : IngredientDetailApiState
}
