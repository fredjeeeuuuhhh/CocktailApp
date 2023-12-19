package com.example.cocktailapp.local.ingredients

import com.example.cocktailapp.model.Ingredient

fun Ingredient.asDbIngredient(): DbIngredient {
    return DbIngredient(
        ingredientId = id,
        ingredientName = name,
        description = description,
        type = type,
        containsAlcohol = containsAlcohol,
        alcoholPercentage = alcoholPercentage,
        thumbnail = thumbnail,
        isOwned= isOwned?: false,
    )
}

fun DbIngredient.toDomainIngredient(): Ingredient {
    return Ingredient(
        id = ingredientId,
        name = ingredientName,
        description = description,
        type = type,
        containsAlcohol = containsAlcohol,
        alcoholPercentage = alcoholPercentage,
        thumbnail = thumbnail,
        isOwned= isOwned,
    )
}