package com.example.cocktailapp.di

import android.content.Context
import com.example.cocktailapp.local.CocktailDB
import com.example.cocktailapp.local.cocktails.CocktailRepository
import com.example.cocktailapp.local.cocktails.OfflineCocktailRepository
import com.example.cocktailapp.local.ingredients.IngredientRepository
import com.example.cocktailapp.local.ingredients.OfflineIngredientRepository
import com.example.cocktailapp.network.CocktailApiService
import com.example.cocktailapp.network.IngredientApiService
import com.example.cocktailapp.network.NetworkConnectionInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

interface AppContainer {
    val cocktailRepository: CocktailRepository
    val ingredientRepository: IngredientRepository
}

@OptIn(ExperimentalSerializationApi::class)
class DefaultAppContainer(private val context: Context) : AppContainer {

    private val networkCheck = NetworkConnectionInterceptor(context)
    private val client = OkHttpClient.Builder()
        .addInterceptor(networkCheck)
        .build()

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        explicitNulls = false
    }
    private val BASE_URL = "https://www.thecocktaildb.com/api/json/v1/1/"
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(
            json.asConverterFactory("application/json".toMediaType()),
        )
        .baseUrl(BASE_URL)
        .client(client)
        .build()

    private val cocktailRetrofitService: CocktailApiService by lazy {
        retrofit.create(CocktailApiService::class.java)
    }

    private val ingredientRetrofitService: IngredientApiService by lazy {
        retrofit.create(IngredientApiService::class.java)
    }

    override val cocktailRepository: CocktailRepository by lazy {
        OfflineCocktailRepository(CocktailDB.getDatabase(context).cocktailDao(), CocktailDB.getDatabase(context).IngredientDao(), ingredientRetrofitService, cocktailRetrofitService, context)
    }

    override val ingredientRepository: IngredientRepository by lazy {
        OfflineIngredientRepository(CocktailDB.getDatabase(context).IngredientDao(), CocktailDB.getDatabase(context).cocktailDao(), ingredientRetrofitService, context)
    }

    object AppContainerProvider {
        private var appContainer: AppContainer? = null

        fun getAppContainer(context: Context): AppContainer {
            if (appContainer == null) {
                appContainer = DefaultAppContainer(context.applicationContext)
            }
            return appContainer!!
        }
    }
}
