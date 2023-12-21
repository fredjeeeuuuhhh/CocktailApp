package com.example.cocktailapp.local.ingredients

import android.content.Context
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.cocktailapp.local.cocktails.CocktailDao
import com.example.cocktailapp.model.Ingredient
import com.example.cocktailapp.network.IngredientApiService
import com.example.cocktailapp.network.asDomainImgredientNameOnly
import com.example.cocktailapp.network.asDomainIngredient
import com.example.cocktailapp.util.WifiGetIngredientByNameWorker
import com.example.cocktailapp.util.WifiRefreshIngredientsWorker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.UUID

interface IngredientRepository {
    fun getIngredients(): Flow<List<Ingredient>>
    fun getIngredientByName(name: String): Flow<Ingredient>
    suspend fun updateIsOwned(ingredientName: String, isOwned: Boolean): Flow<Void>
    suspend fun refreshIngredients()
    suspend fun getIngredientByNameInWorker(name: String)
    suspend fun refreshIngredientsInWorker()

    var wifiWorkInfo: Flow<WorkInfo>
    var wifiGetIngredientByNameInfo: Flow<WorkInfo>
}

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

    private var wifiGetIngredientByNameID = UUID(1, 2)
    override var wifiGetIngredientByNameInfo: Flow<WorkInfo> =
        workManager.getWorkInfoByIdFlow(wifiGetIngredientByNameID)
    override fun getIngredientByName(name: String): Flow<Ingredient> {
        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val inputData = Data.Builder().putString("ingredientName", name).build()
        val requestBuilder = OneTimeWorkRequestBuilder<WifiGetIngredientByNameWorker>()
        val request = requestBuilder.setInputData(inputData).setConstraints(constraints).build()
        workManager.enqueue(request)
        wifiGetIngredientByNameID = request.id
        wifiGetIngredientByNameInfo = workManager.getWorkInfoByIdFlow(request.id)

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

    private var workID = UUID(1, 2)
    override var wifiWorkInfo: Flow<WorkInfo> =
        workManager.getWorkInfoByIdFlow(workID)
    override suspend fun refreshIngredients() {
        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val requestBuilder = OneTimeWorkRequestBuilder<WifiRefreshIngredientsWorker>()
        val request = requestBuilder.setConstraints(constraints).build()
        workManager.enqueue(request)
        workID = request.id
        wifiWorkInfo = workManager.getWorkInfoByIdFlow(request.id)
    }

    override suspend fun refreshIngredientsInWorker() {
        val ingredients = ingredientApiService.getIngredients().drinks.map { it.strIngredient1.asDomainImgredientNameOnly().asDbIngredient() }
        ingredients.forEach { ingredient ->
            ingredientDao.insertIngredientIfNotExisting(ingredient)
        }
    }
}
