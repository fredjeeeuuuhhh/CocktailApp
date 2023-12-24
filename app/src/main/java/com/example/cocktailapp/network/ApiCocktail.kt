package com.example.cocktailapp.network

import com.example.cocktailapp.model.Cocktail
import kotlinx.serialization.Serializable

/**
 * Represents an API response for a detailed cocktail item.
 *
 * @property idDrink The unique identifier for the cocktail.
 * @property strDrink The name or title of the cocktail.
 * @property strCategory The category of the cocktail (e.g., "Martini", "Margarita").
 * @property strAlcoholic If it contains alcohol, or if it is optional
 * @property strGlass The type of glass used for serving the cocktail.
 * @property strInstructions Instructions to prepare the cocktail.
 * @property strDrinkThumb The URL or path to the image representing the cocktail.
 * @property strIngredient1-15 Ingredients used in the cocktail (up to 15).
 * @property strMeasure1-15 Corresponding measurements for the ingredients (up to 15).
 */
@Serializable
data class ApiCocktail(
    val idDrink: String,
    val strDrink: String,
    val strCategory: String,
    val strAlcoholic: String,
    val strGlass: String,
    val strInstructions: String,
    val strDrinkThumb: String,
    val strIngredient1: String?,
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
    val strMeasure1: String?,
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

/**
 * Represents an API response for a simplified cocktail item used in search results.
 *
 * @property strDrink The name or title of the cocktail.
 * @property strDrinkThumb The URL or path to the image representing the cocktail.
 * @property idDrink The unique identifier for the cocktail.
 */
@Serializable
data class ApiCocktailForSearch(
    val strDrink: String,
    val strDrinkThumb: String,
    val idDrink: String,
)

/**
 * Represents a list of cocktails obtained from an API search.
 *
 * @property drinks List of simplified cocktail items for search results.
 */
@Serializable
data class CocktailApiSearchResult(
    val drinks: List<ApiCocktailForSearch>,
)

/**
 * Represents a list of cocktails obtained from an API response.
 *
 * @property drinks List of detailed cocktail items.
 */
@Serializable
data class ApiDrinks(
    val drinks: List<ApiCocktail>?,
)

/**
 * Extension function converting a list of [ApiCocktail] items to a list of [Cocktail] domain objects.
 *
 * @return A [List] of [Cocktail] domain objects representing the cocktails.
 */
fun List<ApiCocktail>.asDomainObjects() =
    map { apiCocktail ->
        Cocktail(
            id = apiCocktail.idDrink.toInt(),
            title = apiCocktail.strDrink,
            category = apiCocktail.strCategory,
            alcoholFilter = apiCocktail.strAlcoholic,
            typeOfGlass = apiCocktail.strGlass,
            instructions = apiCocktail.strInstructions,
            image = apiCocktail.strDrinkThumb + "/preview",
            ingredientNames =
            listOfNotNull(
                apiCocktail.strIngredient1,
                apiCocktail.strIngredient2,
                apiCocktail.strIngredient3,
                apiCocktail.strIngredient4,
                apiCocktail.strIngredient5,
                apiCocktail.strIngredient6,
                apiCocktail.strIngredient7,
                apiCocktail.strIngredient8,
                apiCocktail.strIngredient9,
                apiCocktail.strIngredient10,
                apiCocktail.strIngredient11,
                apiCocktail.strIngredient12,
                apiCocktail.strIngredient13,
                apiCocktail.strIngredient14,
                apiCocktail.strIngredient15,
            ),
            measurements =
            listOfNotNull(
                apiCocktail.strMeasure1,
                apiCocktail.strMeasure2,
                apiCocktail.strMeasure3,
                apiCocktail.strMeasure4,
                apiCocktail.strMeasure5,
                apiCocktail.strMeasure6,
                apiCocktail.strMeasure7,
                apiCocktail.strMeasure8,
                apiCocktail.strMeasure9,
                apiCocktail.strMeasure10,
                apiCocktail.strMeasure11,
                apiCocktail.strMeasure12,
                apiCocktail.strMeasure13,
                apiCocktail.strMeasure14,
                apiCocktail.strMeasure15,
            ),
        )
    }

/**
 * Extension function converting a list of [ApiCocktailForSearch] items to a list of simplified [Cocktail] domain objects.
 *
 * @return A [List] of simplified [Cocktail] domain objects representing the cocktails for search results.
 */
fun List<ApiCocktailForSearch>.asDomainObjectsFromSearch() =
    map { apiCocktail ->
        Cocktail(
            id = apiCocktail.idDrink.toInt(),
            title = apiCocktail.strDrink,
            image = apiCocktail.strDrinkThumb + "/preview",
        )
    }
