package com.example.cocktailapp.local.ingredients

import android.content.Context
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.cocktailapp.local.cocktails.CocktailDao
import com.example.cocktailapp.model.Ingredient
import com.example.cocktailapp.util.WifiGetIngredientByNameWorker
import com.example.cocktailapp.util.WifiRefreshIngredientsWorker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

/**
 * Interface defining methods to retrieve and manipulate Ingredient-related data.
 */
interface IngredientRepository {
    /**
     * Retrieves all ingredients.
     *
     * @return A [Flow] emitting a [List] of [Ingredient] objects.
     */
    fun getIngredients(): Flow<List<Ingredient>>

    /**
     * Retrieves an ingredient by its name.
     *
     * @param name The name of the ingredient.
     * @return A [Flow] emitting an [Ingredient] object.
     */
    fun getIngredientByName(name: String): Flow<Ingredient>

    /**
     * Updates the 'isOwned' status of an ingredient.
     *
     * @param ingredientName The name of the ingredient to update.
     * @param isOwned Boolean indicating whether the user owns this ingredient.
     * @return A [Flow] of type [Void].
     */
    suspend fun updateIsOwned(ingredientName: String, isOwned: Boolean): Flow<Void>

    /**
     * Refreshes the ingredients data.
     */
    suspend fun refreshIngredients()
}

/**
 * Concrete implementation of [IngredientRepository] for offline data operations.
 *
 * @param ingredientDao The data access object for ingredients.
 * @param cocktailDao The data access object for cocktails.
 * @param context The Android application context.
 */
class OfflineIngredientRepository(
    private val ingredientDao: IngredientDao,
    private val cocktailDao: CocktailDao,
    private val context: Context?,
) : IngredientRepository {

    override fun getIngredients(): Flow<List<Ingredient>> {
        return ingredientDao.getAll().map { ingredients ->
            ingredients.map {
                it.toDomainIngredient()
            }
        }
    }

    override fun getIngredientByName(name: String): Flow<Ingredient> {
        if (context != null) {
            val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
            val inputData = Data.Builder().putString("ingredientName", name).build()
            val requestBuilder = OneTimeWorkRequestBuilder<WifiGetIngredientByNameWorker>()
            val request = requestBuilder.setInputData(inputData).setConstraints(constraints).build()
            val workManager = WorkManager.getInstance(context)
            workManager.enqueue(request)
        }

        return ingredientDao.getByName(name).map { it.toDomainIngredient() }
    }

    override suspend fun updateIsOwned(ingredientName: String, isOwned: Boolean): Flow<Void> = flow {
        ingredientDao.updateIsOwned(ingredientName, isOwned)
        cocktailDao.getByIngredientIdOnlyNoFlow(ingredientName).forEach { id ->
            cocktailDao.updateTransaction(id)
        }
    }

    override suspend fun refreshIngredients() {
        if (context != null) {
            val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
            val requestBuilder = OneTimeWorkRequestBuilder<WifiRefreshIngredientsWorker>()
            val request = requestBuilder.setConstraints(constraints).build()
            val workManager = WorkManager.getInstance(context)
            workManager.enqueue(request)
        }
    }
}
