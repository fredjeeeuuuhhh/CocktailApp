package com.example.cocktailapp.util

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.cocktailapp.di.DefaultAppContainer
import com.example.cocktailapp.local.CocktailDB
import com.example.cocktailapp.local.cocktails.asDbCocktail
import com.example.cocktailapp.network.asDomainObjectsFromSearch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
class WifiSearchByIngredientWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    private val cocktailApiService = DefaultAppContainer.AppContainerProvider.getAppContainer(context).cocktailRetrofitService
    private val cocktailDao = CocktailDB.getDatabase(context).cocktailDao()
    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                val ingredientName = inputData.getString("ingredientName")
                ingredientName?.let { name ->
                    val cocktails = cocktailApiService.searchByIngredient(name).drinks.asDomainObjectsFromSearch().map { it.asDbCocktail() }
                    cocktails.forEach { cocktail ->
                        cocktailDao.insertCocktailIfNotExisting(cocktail)
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
