package com.example.cocktailapp.local.cocktails

import com.example.cocktailapp.model.Cocktail

fun Cocktail.asDbCocktail(): DbCocktail {
    return DbCocktail(
        cocktailId = id,
        title = title,
        category = category,
        alcoholFilter = alcoholFilter,
        typeOfGlass = typeOfGlass,
        instructions = instructions,
        image = image,
        ingredientNames = ingredientNames ?: emptyList(),
        measurements = measurements ?: emptyList(),
        isFavorite = isFavorite ?: false,
    )
}

fun DbCocktail.toDomainCocktail(): Cocktail {
    return Cocktail(
        id = cocktailId,
        title = title,
        category = category,
        alcoholFilter = alcoholFilter,
        typeOfGlass = typeOfGlass,
        instructions = instructions,
        image = image,
        ingredientNames = ingredientNames,
        measurements = measurements,
        isFavorite = isFavorite,
    )
}
