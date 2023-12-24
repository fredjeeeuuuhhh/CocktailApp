package com.example.cocktailapp.local.cocktails

import com.example.cocktailapp.model.Cocktail

/**
 * Converts a [Cocktail] object into its equivalent [DbCocktail] representation.
 *
 * @return A [DbCocktail] object representing the cocktail in the room database.
 */
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
        nrOfOwnedIngredients = nrOwnedIngredients,
    )
}

/**
 * Converts a [DbCocktail] object into its equivalent [Cocktail] representation.
 *
 * @return A [Cocktail] object representing the domain model of the cocktail.
 */
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
        nrOwnedIngredients = nrOfOwnedIngredients,
    )
}
