package com.example.cocktailapp.repositorytests

import androidx.test.core.app.ApplicationProvider
import com.example.cocktailapp.CocktailApplication
import com.example.cocktailapp.fake.FakeCocktailApiService
import com.example.cocktailapp.fake.FakeCocktailDao
import com.example.cocktailapp.fake.FakeIngredientApiService
import com.example.cocktailapp.fake.FakeIngredientDao
import com.example.cocktailapp.local.ingredients.OfflineIngredientRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class IngredientRepositoryTest {
    // private val networkCocktails = listOf(networkSupplement1, networkSupplement2)
    // private val localCocktails = listOf(localSupplement1, localSupplement2)
    // private val networkIngredients = listOf(networkSupplement1, networkSupplement2)
    // private val localIngredients = listOf(localSupplement1, localSupplement2)

    private lateinit var localCocktailDataSource: FakeCocktailDao
    private lateinit var localIngredientDataSource: FakeIngredientDao
    private lateinit var networkCocktailDataSource: FakeCocktailApiService
    private lateinit var networkIngredientDataSource: FakeIngredientApiService

    private lateinit var ingredientRepository: OfflineIngredientRepository

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<CocktailApplication>()
        localCocktailDataSource = FakeCocktailDao()
        localIngredientDataSource = FakeIngredientDao()
        networkIngredientDataSource = FakeIngredientApiService()
        networkCocktailDataSource = FakeCocktailApiService()
        ingredientRepository = OfflineIngredientRepository(
            localIngredientDataSource,
            localCocktailDataSource,
            networkIngredientDataSource,
            context,
        )
    }

    @Test
    fun `repository getAll returns all ingredients from local ingredients`() = runTest {
        val ingredients = ingredientRepository.getIngredients()
        // assertEquals(localIngredients, ingredients.first())
    }

    @Test
    fun `repository getIngredientByName returns ingredient with name`() = runTest {
        val ingredient = ingredientRepository.getIngredientByName("Cola")
        // assertEquals(localIngredient1, ingredient.first())
    }

    @Test
    fun `repository editIsOwned edits isOwned`() = runTest {
        // ingredientRepository.updateIsowned(localIngredient1.name, false).collect()

        // assertEquals(false, localIngredientDataSource.ingredients!![0].isFavorite)
    }
}
