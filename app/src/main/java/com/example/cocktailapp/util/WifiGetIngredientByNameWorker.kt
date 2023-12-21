package com.example.cocktailapp.util

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.cocktailapp.local.ingredients.IngredientRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
private const val TAG = "WifiGetIngredientByNameWorker"
class WifiGetIngredientByNameWorker(context: Context, params: WorkerParameters, private val ingredientRepository: IngredientRepository) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                val ingredientName = inputData.getString("ingredientName")
                ingredientName?.let { name ->
                    ingredientRepository.getIngredientByNameInWorker(name)
                    Result.success()
                }
                Result.failure()
            } catch (throwable: Throwable) {
                Result.failure()
            }
        }
    }
}
