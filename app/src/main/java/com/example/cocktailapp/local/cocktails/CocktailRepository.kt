package com.example.cocktailapp.local.cocktails

import android.content.Context
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.cocktailapp.local.ingredients.IngredientDao
import com.example.cocktailapp.local.ingredients.asDbIngredient
import com.example.cocktailapp.model.Cocktail
import com.example.cocktailapp.network.CocktailApiService
import com.example.cocktailapp.network.IngredientApiService
import com.example.cocktailapp.network.asDomainIngredient
import com.example.cocktailapp.network.asDomainObjects
import com.example.cocktailapp.network.asDomainObjectsFromSearch
import com.example.cocktailapp.util.WifiGetCocktailByIdWorker
import com.example.cocktailapp.util.WifiRefreshCocktailWorker
import com.example.cocktailapp.util.WifiSearchByIngredientWorker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

interface CocktailRepository {
    fun getAll(): Flow<List<Cocktail>>
    fun getCocktailById(id: Int): Flow<Cocktail>
    fun searchByIngredient(ingredientName: String): Flow<List<Cocktail>>
    suspend fun updateIsFavorite(cocktailId: Int, isFavorite: Boolean): Flow<Void>
    suspend fun refreshCocktails()
    suspend fun refreshCocktailsInWorker()
    suspend fun getCocktailByIdInWorker(id: Int)
    suspend fun searchByIngredientInWorker(ingredientName: String)
}

class OfflineCocktailRepository(
    private val cocktailDao: CocktailDao,
    private val ingredientDao: IngredientDao,
    private val ingredientApiService: IngredientApiService,
    private val cocktailApiService: CocktailApiService,
    context: Context,
) : CocktailRepository {
    private val workManager = WorkManager.getInstance(context)

    //  private val characters = listOf("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z")
    private val characters = listOf("a", "b", "c")
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
        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val inputData = Data.Builder().putInt("id", id).build()
        val requestBuilder = OneTimeWorkRequestBuilder<WifiGetCocktailByIdWorker>()
        val request = requestBuilder.setInputData(inputData).setConstraints(constraints).build()
        workManager.enqueue(request)

        return cocktailDao.getById(id).map {
            it.toDomainCocktail()
        }
    }
    override suspend fun getCocktailByIdInWorker(id: Int) {
        val ingredientsNames = cocktailApiService.getCocktailById(id).drinks?.asDomainObjects()
            ?.first()?.ingredientNames

        if (ingredientsNames != null) {
            ingredientsNames.forEach { ingredientName ->
                val ingredient = ingredientApiService.getIngredientByName(ingredientName).ingredients.map { it.asDomainIngredient() }.first().asDbIngredient()
                ingredientDao.insertIngredientIfNotExisting(ingredient)
                cocktailDao.insertCrossRef(CrossRef(id, ingredientName))
            }
        }
    }

    override fun searchByIngredient(ingredientName: String): Flow<List<Cocktail>> {
        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val inputData = Data.Builder().putString("ingredientName", ingredientName).build()
        val requestBuilder = OneTimeWorkRequestBuilder<WifiSearchByIngredientWorker>()
        val request = requestBuilder.setInputData(inputData).setConstraints(constraints).build()
        workManager.enqueue(request)

        return cocktailDao.getByIngredient(ingredientName).map { cocktails ->
            cocktails.map {
                it.toDomainCocktail()
            }
        }
    }

    override suspend fun searchByIngredientInWorker(ingredientName: String) {
        val cocktails = cocktailApiService.searchByIngredient(ingredientName).drinks.asDomainObjectsFromSearch().map { it.asDbCocktail() }
        cocktails.forEach { cocktail ->
            cocktailDao.insertCocktailIfNotExisting(cocktail)
        }
    }

    override suspend fun refreshCocktails() {
        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val requestBuilder = OneTimeWorkRequestBuilder<WifiRefreshCocktailWorker>()
        val request = requestBuilder.setConstraints(constraints).build()
        workManager.enqueue(request)
    }

    override suspend fun refreshCocktailsInWorker() {
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
    }
}
