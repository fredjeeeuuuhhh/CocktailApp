package com.example.cocktailapp.util

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.cocktailapp.di.DefaultAppContainer
import com.example.cocktailapp.local.CocktailDB
import com.example.cocktailapp.network.asDomainIngredient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
class WifiGetIngredientByNameWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    private val ingredientApiService = DefaultAppContainer.AppContainerProvider.getAppContainer(context).ingredientRetrofitService
    private val ingredientDao = CocktailDB.getDatabase(context).IngredientDao()
    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                val ingredientName = inputData.getString("ingredientName")
                ingredientName?.let { name ->
                    val ingredients = ingredientApiService.getIngredientByName(name).ingredients.map { it.asDomainIngredient() }
                    ingredients.forEach {
                        ingredientDao.updateIngredient(it.name, it.description ?: "", it.containsAlcohol ?: false, it.alcoholPercentage ?: "", it.type ?: "")
                    }
                    Result.success()
                }
                Result.failure()
            } catch (throwable: Throwable) {
                Result.failure()
            }
        }
    }
}
