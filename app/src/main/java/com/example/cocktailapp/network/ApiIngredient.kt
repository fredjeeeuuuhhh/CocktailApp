package com.example.cocktailapp.network

import com.example.cocktailapp.model.Ingredient
import kotlinx.serialization.Serializable

@Serializable
data class ApiIngredient(
    val idIngredient: String,
    val strIngredient: String,
    val strDescription: String?,
    val strType: String?,
    val strAlcohol: String,
    val strABV: String?,
)

@Serializable
data class ApiIngredientName(
    val strIngredient1: String,
)

@Serializable
data class ApiIngredientNames(
    val drinks: List<ApiIngredientName>,
)

@Serializable
data class ApiIngredientLookupResult(
    val ingredients: List<ApiIngredient>,
)

fun String.asDomainImgredientNameOnly(): Ingredient {
    return Ingredient(
        name = this,
        thumbnail = "https://www.thecocktaildb.com/images/ingredients/$this-Small.png",
    )
}

fun ApiIngredient.asDomainIngredient(): Ingredient {
    return Ingredient(
        name = strIngredient,
        description = strDescription,
        type = strType,
        containsAlcohol = strAlcohol == "Yes",
        alcoholPercentage = strABV,
        thumbnail = "https://www.thecocktaildb.com/images/ingredients/$strIngredient-Small.png",
        isOwned = false,
    )
}
