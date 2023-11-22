package com.example.cocktailapp.network

import com.example.cocktailapp.model.Cocktail
import kotlinx.serialization.Serializable

@Serializable
data class ApiCocktail(
    val idDrink: String,
    val strDrink: String,
    val strCategory: String,
    val strAlcoholic: String,
    val strGlass: String,
    val strInstructions: String,
    val strDrinkThumb: String,
    val strIngredient1: String,
    val strIngredient2: String?,
    val strIngredient3: String?,
    val strIngredient4: String?,
    val strIngredient5: String?,
    val strIngredient6: String?,
    val strIngredient7: String?,
    val strIngredient8: String?,
    val strIngredient9: String?,
    val strIngredient10: String?,
    val strIngredient11: String?,
    val strIngredient12: String?,
    val strIngredient13: String?,
    val strIngredient14: String?,
    val strIngredient15: String?,
    val strMeasure1: String,
    val strMeasure2: String?,
    val strMeasure3: String?,
    val strMeasure4: String?,
    val strMeasure5: String?,
    val strMeasure6: String?,
    val strMeasure7: String?,
    val strMeasure8: String?,
    val strMeasure9: String?,
    val strMeasure10: String?,
    val strMeasure11: String?,
    val strMeasure12: String?,
    val strMeasure13: String?,
    val strMeasure14: String?,
    val strMeasure15: String?,
)
@Serializable
data class ApiDrinks(
    val drinks:List<ApiCocktail>
)

fun List<ApiCocktail>.asDomainObjects() =
    map { Cocktail(
        id = it.idDrink.toInt(),
        title = it.strDrink,
        category = it.strCategory,
        alcoholFilter = it.strAlcoholic,
        typeOfGlass = it.strGlass,
        instructions = it.strInstructions,
        image = it.strDrinkThumb,
        ingredients = null,
        ingredientNames =
        listOf(it.strIngredient1,it.strIngredient2,it.strIngredient3,it.strIngredient4,it.strIngredient5,
            it.strIngredient6,it.strIngredient7,it.strIngredient8,it.strIngredient9,it.strIngredient10,
            it.strIngredient11,it.strIngredient12,it.strIngredient13,it.strIngredient14,it.strIngredient15
        ).filterNotNull(),
        measurements =
        listOf(it.strMeasure1,it.strMeasure2,it.strMeasure3,it.strMeasure4,it.strMeasure5,
            it.strMeasure6,it.strMeasure7,it.strMeasure8,it.strMeasure9,it.strMeasure10,
            it.strMeasure11,it.strMeasure12,it.strMeasure13,it.strMeasure14,it.strMeasure15
        ).filterNotNull(),
        isFavorite = null,
    )}
