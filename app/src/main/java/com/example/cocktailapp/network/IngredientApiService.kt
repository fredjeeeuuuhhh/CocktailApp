package com.example.cocktailapp.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

interface IngredientApiService {
    /*
    only the names -> i want ful details->
    would result in get by id every element-> EXPENSIVE!
    maybe other way-> keep in mind for later
    */
    //@GET("list.php?i=list")
    //suspend fun getIngredients(): ApiIngredientNames
    @GET("lookup.php")
    suspend fun getIngredientByName(@Query("iid") id:Int):ApiIngredientList

}

private val json = Json { ignoreUnknownKeys = true }

private var retrofit: Retrofit = Retrofit
    .Builder()
    .addConverterFactory(
        json.asConverterFactory("application/json".toMediaType())
    )
    .baseUrl("https://www.thecocktaildb.com/api/json/v1/1/")
    .build()
object IngredientApi{
    val ingredientService: IngredientApiService by lazy {
        retrofit.create(IngredientApiService::class.java)
    }
}