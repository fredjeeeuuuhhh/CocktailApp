package com.example.cocktailapp.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
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
    /*
    Response doesnt contain enough values->
    would result in getbyid vor every element in result -> EXPENSIVE!
    maybe better way? -> keep in mind for later
     */
    //@GET("filter.php")
    //suspend fun filterByAlcoholic(@Query("a") filter:String): List<ApiCocktail>
    //@GET("filter.php")
    //suspend fun filterByCategory(@Query("c") filter:String): List<ApiCocktail>
    //@GET("filter.php")
    //suspend fun filterByGlass(@Query("g") filter:String): List<ApiCocktail>
    //@GET("filter.php")
    //suspend fun searchByIngredient(@Query("i") ingredient:String): List<ApiCocktail>
}
private val json = Json {
    ignoreUnknownKeys = true
    coerceInputValues = true
}
private var retrofit: Retrofit = Retrofit
    .Builder()
    .addConverterFactory(
        json.asConverterFactory("application/json".toMediaType())
    )
    .baseUrl("https://www.thecocktaildb.com/api/json/v1/1/")
    .build()
object CocktailApi{
    val cocktailService: CocktailApiService by lazy {
        retrofit.create(CocktailApiService::class.java)
    }
}