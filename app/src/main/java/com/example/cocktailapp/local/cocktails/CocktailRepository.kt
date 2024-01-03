package com.example.cocktailapp.local.cocktails

import android.content.Context
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.cocktailapp.model.Cocktail
import com.example.cocktailapp.util.WifiGetCocktailByIdWorker
import com.example.cocktailapp.util.WifiRefreshCocktailWorker
import com.example.cocktailapp.util.WifiSearchByIngredientWorker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

/**
 * Interface defining methods to retrieve and manipulate Cocktail-related data.
 */
interface CocktailRepository {

    /**
     * Retrieves all cocktails from the repository.
     *
     * @return A [Flow] emitting a list of [Cocktail] objects.
     */
    fun getAll(): Flow<List<Cocktail>>

    /**
     * Retrieves a specific cocktail by its ID.
     *
     * @param id The unique identifier of the cocktail.
     * @return A [Flow] emitting a [Cocktail] object.
     */
    fun getCocktailById(id: Int): Flow<Cocktail>

    /**
     * Searches for cocktails based on a specified ingredient name.
     *
     * @param ingredientName The name of the ingredient to search for.
     * @return A [Flow] emitting a [List] of [Cocktail] objects.
     */
    fun searchByIngredient(ingredientName: String): Flow<List<Cocktail>>

    /**
     * Updates the 'isFavorite' status of a cocktail.
     *
     * @param cocktailId The unique identifier of the cocktail to update.
     * @param isFavorite Boolean indicating whether the cocktail is marked as a favorite.
     * @return A [Flow] of type [Void].
     */
    suspend fun updateIsFavorite(cocktailId: Int, isFavorite: Boolean): Flow<Void>

    /**
     * Refreshes the cocktails data.
     */
    suspend fun refreshCocktails()
}

/**
 * Concrete implementation of [CocktailRepository] for offline data operations.
 *
 * @param cocktailDao The data access object for cocktails.
 * @param context The Android application context.
 */
class OfflineCocktailRepository(
    private val cocktailDao: CocktailDao,
    private val context: Context?,
) : CocktailRepository {

    override fun getAll(): Flow<List<Cocktail>> {
        return cocktailDao.getAll().map { cocktails ->
            cocktails.map {
                it.toDomainCocktail()
            }
        }
    }

    override suspend fun updateIsFavorite(cocktailId: Int, isFavorite: Boolean): Flow<Void> = flow {
        cocktailDao.updateIsFavorite(cocktailId, isFavorite)
    }

    override fun getCocktailById(id: Int): Flow<Cocktail> {
        if (context != null) {
            val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
            val inputData = Data.Builder().putInt("id", id).build()
            val requestBuilder = OneTimeWorkRequestBuilder<WifiGetCocktailByIdWorker>()
            val request = requestBuilder.setInputData(inputData).setConstraints(constraints).build()
            val workManager = WorkManager.getInstance(context)
            workManager.enqueue(request)
        }

        return cocktailDao.getById(id).map {
            it.toDomainCocktail()
        }
    }

    override fun searchByIngredient(ingredientName: String): Flow<List<Cocktail>> {
        if (context != null) {
            val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
            val inputData = Data.Builder().putString("ingredientName", ingredientName).build()
            val requestBuilder = OneTimeWorkRequestBuilder<WifiSearchByIngredientWorker>()
            val request = requestBuilder.setInputData(inputData).setConstraints(constraints).build()
            val workManager = WorkManager.getInstance(context)
            workManager.enqueue(request)
        }

        return cocktailDao.getByIngredient(ingredientName).map { cocktails ->
            cocktails.map {
                it.toDomainCocktail()
            }
        }
    }

    override suspend fun refreshCocktails() {
        if (context != null) {
            val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
            val requestBuilder = OneTimeWorkRequestBuilder<WifiRefreshCocktailWorker>()
            val request = requestBuilder.setConstraints(constraints).build()
            val workManager = WorkManager.getInstance(context)
            workManager.enqueue(request)
        }
    }
}
