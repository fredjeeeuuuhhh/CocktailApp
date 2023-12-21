package com.example.cocktailapp.network

import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailApiService {
    @GET("search.php")
    suspend fun getCocktails(@Query("f") firstLetter: String): ApiDrinks

    @GET("lookup.php")
    suspend fun getCocktailById(@Query("i") id: Int): ApiDrinks

    @GET("filter.php")
    suspend fun searchByIngredient(@Query("i") ingredient: String): CocktailApiSearchResult
}
