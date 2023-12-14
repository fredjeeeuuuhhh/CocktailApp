package com.example.cocktailapp.data

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.createSavedStateHandle
import com.example.cocktailapp.network.CocktailApiService
import com.example.cocktailapp.network.IngredientApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val cocktailRepository: CocktailRepository
    val ingredientRepository: IngredientRepository
}

class DefaultAppContainer(private val context: Context): AppContainer {
    private  val BASE_URL = "https://www.thecocktaildb.com/api/json/v1/1/"
    private  val TIMEOUT = 30L
   @OptIn(ExperimentalSerializationApi::class)
   private val Json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        explicitNulls = false
    }
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(
            Json.asConverterFactory("application/json".toMediaType()),
        )
        .baseUrl(BASE_URL)
        .build()



    private val cocktailRetrofitService: CocktailApiService by lazy {
        retrofit.create(CocktailApiService::class.java)
    }
    override val cocktailRepository: CocktailRepository by lazy {
        CocktailApiRepository(cocktailRetrofitService)
    }

    private val ingredientRetrofitService: IngredientApiService by lazy {
        retrofit.create(IngredientApiService::class.java)
    }
    override val ingredientRepository: IngredientRepository by lazy {
        IngredientApiRepository(ingredientRetrofitService)
    }
}