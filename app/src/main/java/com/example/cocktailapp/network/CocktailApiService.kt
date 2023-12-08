package com.example.cocktailapp.network

import kotlinx.coroutines.flow.flow
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailApiService {
    @GET("search.php")
    suspend fun getCocktails(@Query("f") firstLetter:String): ApiDrinks
    @GET("lookup.php")
    suspend fun getCocktailById(@Query("i") id:Int):ApiDrinks
    @GET("random.php")
    suspend fun getRandomCocktail():ApiDrinks
    @GET("filter.php")
    suspend fun searchByIngredient(@Query("i") ingredient:String): CocktailApiSearchResult

    companion object Factory {
        fun create(retrofit: Retrofit): CocktailApiService = retrofit.create(CocktailApiService::class.java)
    }
}

fun CocktailApiService.getCocktailsAsFlow(firstChar:String) = flow { emit(getCocktails(firstChar))}