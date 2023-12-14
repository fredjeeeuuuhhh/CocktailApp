package com.example.cocktailapp.network

import retrofit2.http.GET
import retrofit2.http.Query

interface IngredientApiService {

    @GET("list.php?i=list")
    suspend fun getIngredients(): ApiIngredientNames

    @GET("lookup.php")
    suspend fun getIngredientById(@Query("iid") id:Int):ApiIngredientLookupResult

    @GET("search.php")
    suspend fun getIngredientByName(@Query("i") name:String):ApiIngredientLookupResult

}

