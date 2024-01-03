package com.example.cocktailapp.util

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.cocktailapp.di.DefaultAppContainer
import com.example.cocktailapp.local.CocktailDB
import com.example.cocktailapp.local.ingredients.asDbIngredient
import com.example.cocktailapp.network.asDomainIngredientNameOnly
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
class WifiRefreshIngredientsWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    private val ingredientApiService = DefaultAppContainer.AppContainerProvider.getAppContainer(context).ingredientRetrofitService
    private val ingredientDao = CocktailDB.getDatabase(context).IngredientDao()
    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                val ingredients = ingredientApiService.getIngredients().drinks.map { it.strIngredient1.asDomainIngredientNameOnly().asDbIngredient() }
                ingredients.forEach { ingredient ->
                    ingredientDao.insertIngredientIfNotExisting(ingredient)
                }
                Result.success()
            } catch (throwable: Throwable) {
                Result.failure()
            }
        }
    }
}
