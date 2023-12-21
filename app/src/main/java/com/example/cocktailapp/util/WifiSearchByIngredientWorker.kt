package com.example.cocktailapp.util

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.cocktailapp.local.cocktails.CocktailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
private const val TAG = "WifiSearchByIngredientWorker"
class WifiSearchByIngredientWorker(context: Context, params: WorkerParameters, private val cocktailRepository: CocktailRepository) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                val ingredientName = inputData.getString("ingredientName")
                ingredientName?.let { name ->
                    cocktailRepository.searchByIngredientInWorker(name)
                    Result.success()
                }
                Result.failure()
            } catch (throwable: Throwable) {
                Result.failure()
            }
        }
    }
}
