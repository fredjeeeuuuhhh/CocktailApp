package com.example.cocktailapp.local.ingredients

import android.content.Context
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.cocktailapp.local.cocktails.CocktailDao
import com.example.cocktailapp.model.Ingredient
import com.example.cocktailapp.network.IngredientApiService
import com.example.cocktailapp.network.asDomainIngredient
import com.example.cocktailapp.network.asDomainIngredientNameOnly
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

    /**
     * Retrieves an ingredient by its name in a worker context.
     *
     * @param name The name of the ingredient to retrieve.
     */
    suspend fun getIngredientByNameInWorker(name: String)

    /**
     * Refreshes the ingredients data in a worker context.
     */
    suspend fun refreshIngredientsInWorker()
}

/**
 * Concrete implementation of [IngredientRepository] for offline data operations.
 *
 * @param ingredientDao The data access object for ingredients.
 * @param cocktailDao The data access object for cocktails.
 * @param ingredientApiService The API service for ingredient-related operations.
 * @param context The Android application context.
 */
class OfflineIngredientRepository(
    private val ingredientDao: IngredientDao,
    private val cocktailDao: CocktailDao,
    private val ingredientApiService: IngredientApiService,
    context: Context,
) : IngredientRepository {
    private val workManager = WorkManager.getInstance(context)
    override fun getIngredients(): Flow<List<Ingredient>> {
        return ingredientDao.getAll().map { ingredients ->
            ingredients.map {
                it.toDomainIngredient()
            }
        }
    }

    override fun getIngredientByName(name: String): Flow<Ingredient> {
        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val inputData = Data.Builder().putString("ingredientName", name).build()
        val requestBuilder = OneTimeWorkRequestBuilder<WifiGetIngredientByNameWorker>()
        val request = requestBuilder.setInputData(inputData).setConstraints(constraints).build()
        workManager.enqueue(request)

        return ingredientDao.getByName(name).map { it.toDomainIngredient() }
    }

    override suspend fun getIngredientByNameInWorker(name: String) {
        val ingredients = ingredientApiService.getIngredientByName(name).ingredients.map { it.asDomainIngredient() }
        ingredients.forEach {
            ingredientDao.updateIngredient(it.name, it.description ?: "", it.containsAlcohol ?: false, it.alcoholPercentage ?: "", it.type ?: "")
        }
    }

    override suspend fun updateIsOwned(ingredientName: String, isOwned: Boolean): Flow<Void> = flow {
        ingredientDao.updateIsOwned(ingredientName, isOwned)
        cocktailDao.getByIngredientIdOnlyNoFlow(ingredientName).forEach { id ->
            cocktailDao.updateTransaction(id)
        }
    }

    override suspend fun refreshIngredients() {
        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val requestBuilder = OneTimeWorkRequestBuilder<WifiRefreshIngredientsWorker>()
        val request = requestBuilder.setConstraints(constraints).build()
        workManager.enqueue(request)
    }

    override suspend fun refreshIngredientsInWorker() {
        val ingredients = ingredientApiService.getIngredients().drinks.map { it.strIngredient1.asDomainIngredientNameOnly().asDbIngredient() }
        ingredients.forEach { ingredient ->
            ingredientDao.insertIngredientIfNotExisting(ingredient)
        }
    }
}
