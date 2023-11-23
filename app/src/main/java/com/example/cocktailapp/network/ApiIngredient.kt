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
    val drinks: List<ApiIngredientName>
)

@Serializable
data class ApiIngredientLookupResult(
    val ingredients: List<ApiIngredient>
)
fun List<ApiIngredient>.asDomainObjects() =
    map { Ingredient(
        id = it.idIngredient.toInt(),
        name = it.strIngredient,
        description = it.strDescription,
        type = it.strType,
        containsAlcohol = it.strAlcohol=="Yes",
        alcoholPercentage = it.strABV,
        thumbnail = "${"https://"}www.thecocktaildb.com/images/ingredients/${it.strIngredient}-Small.png",
    )}

fun List<ApiIngredient>.asDomainObject() =
    map { Ingredient(
        id = it.idIngredient.toInt(),
        name = it.strIngredient,
        description = it.strDescription,
        type = it.strType,
        containsAlcohol = it.strAlcohol=="Yes",
        alcoholPercentage = it.strABV,
        thumbnail = "${"https://"}www.thecocktaildb.com/images/ingredients/${it.strIngredient}-Small.png",
    )}.first()

fun List<ApiIngredientName>.asDomainObjectsWithNameOnly() =
    map { Ingredient(
        name = it.strIngredient1,
        thumbnail = "${"https://"}www.thecocktaildb.com/images/ingredients/${it.strIngredient1}-Small.png",
    )}

