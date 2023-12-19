package com.example.cocktailapp.network

sealed interface CocktailApiState {
    object Error : CocktailApiState
    object Loading : CocktailApiState
    object Success : CocktailApiState
}

sealed interface IngredientApiState {
    object Error : IngredientApiState
    object Loading : IngredientApiState
    object Success : IngredientApiState
}

sealed interface CocktailDetailApiState {
    object Error : CocktailDetailApiState
    object Loading : CocktailDetailApiState
    object Success : CocktailDetailApiState
}

sealed interface IngredientDetailApiState {
    object Error : IngredientDetailApiState
    object Loading : IngredientDetailApiState
    object Success : IngredientDetailApiState
}
