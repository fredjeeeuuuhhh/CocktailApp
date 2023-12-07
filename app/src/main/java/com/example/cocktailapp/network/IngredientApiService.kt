package com.example.cocktailapp.network

import kotlinx.coroutines.flow.flow
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

interface IngredientApiService {

    @GET("list.php?i=list")
    suspend fun getIngredients(): ApiIngredientNames

    @GET("lookup.php")
    suspend fun getIngredientById(@Query("iid") id:Int):ApiIngredientLookupResult

    @GET("search.php")
    suspend fun getIngredientByName(@Query("i") name:String):ApiIngredientLookupResult
    companion object Factory {
        fun create(retrofit: Retrofit): IngredientApiService = retrofit.create(IngredientApiService::class.java)
    }
}
fun IngredientApiService.getIngredientsAsFlow() = flow { emit(getIngredients())}
