package com.example.cocktailapp.network

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Service interface for interacting with Cocktail API endpoints.
 */
interface CocktailApiService {
    /**
     * Retrieves cocktails based on the first letter of their names.
     *
     * @param firstLetter The first letter of the cocktail names to search for.
     * @return An [ApiDrinks] object containing a list of cocktails.
     */
    @GET("search.php")
    suspend fun getCocktails(@Query("f") firstLetter: String): ApiDrinks

    /**
     * Retrieves a cocktail by its unique identifier.
     *
     * @param id The unique identifier of the cocktail to retrieve.
     * @return An [ApiDrinks] object containing detailed information about the cocktail.
     */
    @GET("lookup.php")
    suspend fun getCocktailById(@Query("i") id: Int): ApiDrinks

    /**
     * Searches for cocktails based on a specific ingredient.
     *
     * @param ingredient The ingredient name to search for in cocktails.
     * @return A [CocktailApiSearchResult] object containing a list of cocktails using the ingredient.
     */
    @GET("filter.php")
    suspend fun searchByIngredient(@Query("i") ingredient: String): CocktailApiSearchResult
}
