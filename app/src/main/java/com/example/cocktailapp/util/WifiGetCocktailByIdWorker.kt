package com.example.cocktailapp.util

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.cocktailapp.di.DefaultAppContainer
import com.example.cocktailapp.local.CocktailDB
import com.example.cocktailapp.local.cocktails.CrossRef
import com.example.cocktailapp.local.ingredients.asDbIngredient
import com.example.cocktailapp.network.asDomainIngredient
import com.example.cocktailapp.network.asDomainObjects
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WifiGetCocktailByIdWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    private val cocktailApiService = DefaultAppContainer.AppContainerProvider.getAppContainer(context).cocktailRetrofitService
    private val ingredientApiService = DefaultAppContainer.AppContainerProvider.getAppContainer(context).ingredientRetrofitService
    private val cocktailDao = CocktailDB.getDatabase(context).cocktailDao()
    private val ingredientDao = CocktailDB.getDatabase(context).IngredientDao()
    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                val cocktailId = inputData.getInt("id", 0)
                if (cocktailId != 0) {
                    val ingredientsNames = cocktailApiService.getCocktailById(cocktailId).drinks?.asDomainObjects()
                        ?.first()?.ingredientNames

                    ingredientsNames?.forEach { ingredientName ->
                        val ingredient = ingredientApiService.getIngredientByName(ingredientName).ingredients.map { it.asDomainIngredient() }.first().asDbIngredient()
                        ingredientDao.insertIngredientIfNotExisting(ingredient)
                        cocktailDao.insertCrossRef(CrossRef(cocktailId, ingredientName))
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
