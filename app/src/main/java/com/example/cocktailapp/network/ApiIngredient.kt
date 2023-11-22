package com.example.cocktailapp.network

import com.example.cocktailapp.model.Cocktail
import com.example.cocktailapp.model.Ingredient
import kotlinx.serialization.Serializable

@Serializable
data class ApiIngredient(
    val idIngredient: String,
    val strIngredient: String,
    val strDescription: String,
    val strType: String,
    val strAlcohol: String,
    val strABV: String?,
)
@Serializable
data class ApiIngredientName(
    val strIngredient1: String,
)
@Serializable
data class ApiIngredientNames(
    val drinks: List<ApiIngredientName>
)

@Serializable
data class ApiIngredientList(
    val ingredients: List<ApiIngredient>
)
fun List<ApiIngredient>.asDomainObjects() =
    map { Ingredient(
        id = it.idIngredient.toInt(),
        name = it.strIngredient,
        description = it.strDescription,
        type = it.strType,
        containsAlcohol = if (it.strAlcohol=="Yes") true else false,
        alcoholPercentage = it.strABV,
        thumbnail = "www.thecocktaildb.com/images/ingredients/${it.strIngredient}-Small.png",
        isOwned = null,
    )}