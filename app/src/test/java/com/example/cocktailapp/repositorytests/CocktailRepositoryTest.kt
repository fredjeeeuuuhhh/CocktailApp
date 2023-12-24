package com.example.cocktailapp.repositorytests

import androidx.test.core.app.ApplicationProvider
import com.example.cocktailapp.CocktailApplication
import com.example.cocktailapp.fake.FakeCocktailApiService
import com.example.cocktailapp.fake.FakeCocktailDao
import com.example.cocktailapp.fake.FakeIngredientApiService
import com.example.cocktailapp.fake.FakeIngredientDao
import com.example.cocktailapp.local.cocktails.OfflineCocktailRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class CocktailRepositoryTest {

    // private val networkCocktails = listOf(networkSupplement1, networkSupplement2)
    // private val localCocktails = listOf(localSupplement1, localSupplement2)
    // private val networkIngredients = listOf(networkSupplement1, networkSupplement2)
    // private val localIngredients = listOf(localSupplement1, localSupplement2)

    private lateinit var localCocktailDataSource: FakeCocktailDao
    private lateinit var localIngredientDataSource: FakeIngredientDao
    private lateinit var networkCocktailDataSource: FakeCocktailApiService
    private lateinit var networkIngredientDataSource: FakeIngredientApiService

    private lateinit var cocktailRepository: OfflineCocktailRepository

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<CocktailApplication>()
        localCocktailDataSource = FakeCocktailDao()
        localIngredientDataSource = FakeIngredientDao()
        networkCocktailDataSource = FakeCocktailApiService()
        networkIngredientDataSource = FakeIngredientApiService()
        cocktailRepository = OfflineCocktailRepository(
            localCocktailDataSource,
            localIngredientDataSource,
            networkIngredientDataSource,
            networkCocktailDataSource,
            context,
        )
    }

    @Test
    fun `repository getAll returns all cocktails from local cocktails`() = runTest {
        val cocktails = cocktailRepository.getAll()
        // assertEquals(localCocktails, cocktails.first())
    }

    @Test
    fun `repository getCocktailById returns cocktail with id`() = runTest {
        val cocktail = cocktailRepository.getCocktailById(1)
        // assertEquals(localCocktail1, cocktail.first())
    }

    @Test
    fun `repository editIsFavorite edits isFavorite`() = runTest {
        // cocktailRepository.updateIsFavorite(localCocktail1.id, false).collect()

        // assertEquals(false, localCocktailDataSource.cocktails!![0].isFavorite)
    }
}
