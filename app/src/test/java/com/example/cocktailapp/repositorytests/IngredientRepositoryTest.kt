package com.example.cocktailapp.repositorytests

import androidx.test.core.app.ApplicationProvider
import com.example.cocktailapp.CocktailApplication
import com.example.cocktailapp.fake.FakeCocktailApiService
import com.example.cocktailapp.fake.FakeCocktailDao
import com.example.cocktailapp.fake.FakeIngredientApiService
import com.example.cocktailapp.fake.FakeIngredientDao
import com.example.cocktailapp.local.cocktails.asDbCocktail
import com.example.cocktailapp.local.ingredients.OfflineIngredientRepository
import com.example.cocktailapp.local.ingredients.asDbIngredient
import com.example.cocktailapp.model.Cocktail
import com.example.cocktailapp.model.Ingredient
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class IngredientRepositoryTest {
    private var cocktail1 = Cocktail(
        1,
        "Bacardi cola",
        "Cocktail",
        "Alcohol",
        "Cola glass",
        "1. Poor bacardi then cola",
        "www.thecocktaildb.com/images/media/drink/vrwquq1478252802.jpg/preview",
        listOf("Bacardi", "Cola"),
        listOf("1 oz", "1 can"),
        isFavorite = true,
    )
    private var cocktail2 = Cocktail(
        1,
        "Vodka Redbull",
        "Cocktail",
        "Alcohol",
        "cup",
        "1. Poor vodka then redbull",
        "https://www.thecocktaildb.com/images/media/drink/vrwquq1478252802.jpg/preview",
        listOf("Vodka", "Redbull"),
        listOf("1 oz", "1 can"),
    )
    private var ingredient1 = Ingredient(
        "Cola",
        "Coca cola",
        "Soda",
        false,
        "0",
        "https://www.thecocktaildb.com/images/ingredients/cola-Small.png",
        isOwned = true,
    )
    private var ingredient2 = Ingredient(
        "Bacardi",
        "Strong good alcohol",
        "Alcohol",
        true,
        "40",
        "https://www.thecocktaildb.com/images/ingredients/bacardi-Small.png",
    )
    private var ingredient3 = Ingredient(
        "Vodka",
        "Vodka",
        "Vodka",
        true,
        "40",
        "https://www.thecocktaildb.com/images/ingredients/vodka-Small.png",
        isOwned = true,
    )
    private var ingredient4 = Ingredient(
        "Redd bull",
        "Gives you wings",
        "Soda",
        false,
        "0",
        "https://www.thecocktaildb.com/images/ingredients/redbull-Small.png",
    )

    private val localCocktails = listOf(cocktail1, cocktail2)
    private val localIngredients = listOf(ingredient1, ingredient2, ingredient3, ingredient4)

    private lateinit var localCocktailDataSource: FakeCocktailDao
    private lateinit var localIngredientDataSource: FakeIngredientDao
    private lateinit var networkCocktailDataSource: FakeCocktailApiService
    private lateinit var networkIngredientDataSource: FakeIngredientApiService

    private lateinit var ingredientRepository: OfflineIngredientRepository

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<CocktailApplication>()
        localCocktailDataSource = FakeCocktailDao(
            localCocktails.map { it.asDbCocktail() }.toMutableList(),
            localIngredients.map { it.asDbIngredient() }.toMutableList(),
        )
        localIngredientDataSource = FakeIngredientDao(
            localCocktails.map { it.asDbCocktail() }.toMutableList(),
            localIngredients.map { it.asDbIngredient() }.toMutableList(),
        )
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
        assertEquals(localIngredients, ingredients.first())
    }

    @Test
    fun `repository getIngredientByName returns ingredient with name`() = runTest {
        val ingredient = ingredientRepository.getIngredientByName(ingredient1.name)
        assertEquals(ingredient1, ingredient.first())
    }

    @Test
    fun `repository editIsOwned edits isOwned`() = runTest {
        ingredientRepository.updateIsOwned(ingredient1.name, false).collect()
        assertEquals(false, localIngredientDataSource.ingredients[0].isOwned)
    }
}
