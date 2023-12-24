package com.example.cocktailapp.network

import com.example.cocktailapp.model.Ingredient
import kotlinx.serialization.Serializable

/**
 * Represents an API response for a detailed ingredient item.
 *
 * @property idIngredient The unique identifier for the ingredient.
 * @property strIngredient The name of the ingredient.
 * @property strDescription A description or additional information about the ingredient.
 * @property strType The type or category of the ingredient.
 * @property strAlcohol Indicates whether the ingredient contains alcohol.
 * @property strABV The alcohol by volume (ABV) percentage.
 */
@Serializable
data class ApiIngredient(
    val idIngredient: String,
    val strIngredient: String,
    val strDescription: String?,
    val strType: String?,
    val strAlcohol: String,
    val strABV: String?,
)

/**
 * Represents an API response containing a single ingredient name.
 *
 * @property strIngredient1 The name of the ingredient.
 */
@Serializable
data class ApiIngredientName(
    val strIngredient1: String,
)

/**
 * Represents an API response containing a list of ingredient names.
 *
 * @property drinks List of ingredient names.
 */
@Serializable
data class ApiIngredientNames(
    val drinks: List<ApiIngredientName>,
)

/**
 * Represents a list of ingredients obtained from an API response.
 *
 * @property ingredients List of detailed ingredient items.
 */
@Serializable
data class ApiIngredientLookupResult(
    val ingredients: List<ApiIngredient>,
)

/**
 * Extension function converting a string to a simplified [Ingredient] object containing only the name and a thumbnail.
 *
 * @return A simplified [Ingredient] object representing the ingredient name.
 */
fun String.asDomainIngredientNameOnly(): Ingredient {
    return Ingredient(
        name = this,
        thumbnail = "https://www.thecocktaildb.com/images/ingredients/$this-Small.png",
    )
}

/**
 * Extension function converting an [ApiIngredient] item to a [Ingredient] domain object.
 *
 * @return A [Ingredient] domain object representing the detailed ingredient information.
 */
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
