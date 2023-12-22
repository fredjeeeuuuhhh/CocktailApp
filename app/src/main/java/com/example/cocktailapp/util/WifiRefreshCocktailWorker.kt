package com.example.cocktailapp.util

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.cocktailapp.di.DefaultAppContainer
import com.example.cocktailapp.local.cocktails.CocktailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val TAG = "WifiRefreshCocktailWorker"
class WifiRefreshCocktailWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    private val cocktailRepository: CocktailRepository = DefaultAppContainer.AppContainerProvider.getAppContainer(context).cocktailRepository

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                cocktailRepository.refreshCocktailsInWorker()
                Result.success()
            } catch (throwable: Throwable) {
                Result.failure()
            }
        }
    }
}

