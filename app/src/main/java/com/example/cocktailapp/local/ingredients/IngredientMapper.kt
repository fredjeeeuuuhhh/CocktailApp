package com.example.cocktailapp.local.ingredients

import com.example.cocktailapp.model.Ingredient

/**
 * Converts an [Ingredient] object into its equivalent [DbIngredient] representation.
 *
 * @return A [DbIngredient] object representing the ingredient in the room database.
 */
fun Ingredient.asDbIngredient(): DbIngredient {
    return DbIngredient(
        ingredientName = name,
        description = description,
        type = type,
        containsAlcohol = containsAlcohol,
        alcoholPercentage = alcoholPercentage,
        thumbnail = thumbnail,
        isOwned = isOwned ?: false,
    )
}

/**
 * Converts a [DbIngredient] object into its equivalent [Ingredient] representation.
 *
 * @return An [Ingredient] object representing the domain model of the ingredient.
 */
fun DbIngredient.toDomainIngredient(): Ingredient {
    return Ingredient(
        name = ingredientName,
        description = description,
        type = type,
        containsAlcohol = containsAlcohol,
        alcoholPercentage = alcoholPercentage,
        thumbnail = thumbnail,
        isOwned = isOwned,
    )
}
