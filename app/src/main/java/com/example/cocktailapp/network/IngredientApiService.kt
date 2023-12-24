package com.example.cocktailapp.network

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Service interface for interacting with Ingredient API endpoints.
 */
interface IngredientApiService {
    /**
     * Retrieves a list of available ingredients.
     *
     * @return An [ApiIngredientNames] object containing a [List] of ingredient names.
     */
    @GET("list.php?i=list")
    suspend fun getIngredients(): ApiIngredientNames

    /**
     * Retrieves detailed information about an ingredient by its name.
     *
     * @param name The name of the ingredient to search for.
     * @return An [ApiIngredientLookupResult] object containing detailed information about the ingredient.
     */
    @GET("search.php")
    suspend fun getIngredientByName(@Query("i") name: String): ApiIngredientLookupResult
}
