package com.example.cocktailapp.util

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.cocktailapp.di.DefaultAppContainer
import com.example.cocktailapp.local.ingredients.IngredientRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
private const val TAG = "WifiRefreshIngredientsWorker"
class WifiRefreshIngredientsWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    private val ingredientRepository: IngredientRepository = DefaultAppContainer.AppContainerProvider.getAppContainer(context).ingredientRepository
    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                ingredientRepository.refreshIngredientsInWorker()
                Result.success()
            } catch (throwable: Throwable) {
                Result.failure()
            }
        }
    }
}
