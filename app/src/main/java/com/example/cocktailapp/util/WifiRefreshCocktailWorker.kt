package com.example.cocktailapp.util

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.cocktailapp.di.DefaultAppContainer
import com.example.cocktailapp.local.CocktailDB
import com.example.cocktailapp.local.cocktails.CrossRef
import com.example.cocktailapp.local.cocktails.asDbCocktail
import com.example.cocktailapp.network.asDomainObjects
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WifiRefreshCocktailWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    private val cocktailApiService = DefaultAppContainer.AppContainerProvider.getAppContainer(context).cocktailRetrofitService
    private val cocktailDao = CocktailDB.getDatabase(context).cocktailDao()
    private val characters = listOf("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z")
    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                characters.forEach { character ->
                    val cocktails = cocktailApiService.getCocktails(character).drinks?.asDomainObjects()
                        ?.map { it.asDbCocktail() }
                    if (cocktails != null) {
                        cocktails.forEach { cocktail ->
                            cocktailDao.insertCocktailIfNotExisting(cocktail)
                        }
                        cocktails.forEach {
                            it.ingredientNames.forEach { string ->
                                cocktailDao.insertCrossRef(CrossRef(it.cocktailId, string))
                            }
                        }
                    }
                }
                Result.success()
            } catch (throwable: Throwable) {
                Result.failure()
            }
        }
    }
}

