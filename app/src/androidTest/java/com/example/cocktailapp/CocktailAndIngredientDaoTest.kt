package com.example.cocktailapp

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.cocktailapp.local.CocktailDB
import com.example.cocktailapp.local.cocktails.CocktailDao
import com.example.cocktailapp.local.cocktails.CrossRef
import com.example.cocktailapp.local.cocktails.asDbCocktail
import com.example.cocktailapp.local.cocktails.toDomainCocktail
import com.example.cocktailapp.local.ingredients.IngredientDao
import com.example.cocktailapp.local.ingredients.asDbIngredient
import com.example.cocktailapp.local.ingredients.toDomainIngredient
import com.example.cocktailapp.model.Cocktail
import com.example.cocktailapp.model.Ingredient
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class CocktailAndIngredientDaoTest {
    private lateinit var cocktailDao: CocktailDao
    private lateinit var ingredientDao: IngredientDao
    private lateinit var cocktailDb: CocktailDB

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
        2,
        "Vodka Redbull",
        "Cocktail",
        "Alcohol",
        "cup",
        "1. Poor vodka then redbull",
        "https://www.thecocktaildb.com/images/media/drink/vrwquq1478252802.jpg/preview",
        listOf("Vodka", "Redbull"),
        listOf("1 oz", "1 can"),
        isFavorite = false,
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
        isOwned = false,
    )
    private suspend fun addOneCocktailToDb() {
        cocktailDao.insertCocktail(cocktail1.asDbCocktail())
    }

    private suspend fun addTwoCocktailsToDb() {
        cocktailDao.insertCocktail(cocktail1.asDbCocktail())
        cocktailDao.insertCocktail(cocktail2.asDbCocktail())
    }

    private suspend fun addOneIngredientToDb() {
        ingredientDao.insertIngredient(ingredient1.asDbIngredient())
    }
    private suspend fun addTwoIngredientsToDb() {
        ingredientDao.insertIngredient(ingredient1.asDbIngredient())
        ingredientDao.insertIngredient(ingredient2.asDbIngredient())
    }
    private suspend fun addCrossRefsToDB() {
        cocktailDao.insertCrossRef(CrossRef(cocktail1.id, ingredient1.name))
        cocktailDao.insertCrossRef(CrossRef(cocktail1.id, ingredient2.name))
    }

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        cocktailDb = Room.inMemoryDatabaseBuilder(context, CocktailDB::class.java)
            .allowMainThreadQueries()
            .build()
        cocktailDao = cocktailDb.cocktailDao()
        ingredientDao = cocktailDb.IngredientDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        cocktailDb.close()
    }

    @Test
    @Throws(Exception::class)
    fun daoInert_insertCocktailIntoDB() = runBlocking {
        // arrange
        addOneCocktailToDb()
        // act
        val items = cocktailDao.getAll().first()
        // assert
        assertEquals(items[0].toDomainCocktail(), cocktail1)
    }

    @Test
    @Throws(Exception::class)
    fun daoInert_insertIngredientIntoDB() = runBlocking {
        // arrange
        addOneIngredientToDb()
        // act
        val allItems = ingredientDao.getAll().first()
        // assert
        assertEquals(allItems[0].toDomainIngredient(), ingredient1)
    }

    @Test
    @Throws(Exception::class)
    fun daoGetAllCocktails_returnsAllCocktailsFromDB() = runBlocking {
        // arrange
        addTwoCocktailsToDb()
        // act
        val allItems = cocktailDao.getAll().first()
        // assert
        assertEquals(allItems[0].toDomainCocktail(), cocktail1)
        assertEquals(allItems[1].toDomainCocktail(), cocktail2)
    }

    @Test
    @Throws(Exception::class)
    fun daoGetAllIngredients_returnsAllIngredientsFromDB() = runBlocking {
        // arrange
        addTwoIngredientsToDb()
        // act
        val allItems = ingredientDao.getAll().first()
        // assert
        assertEquals(allItems[0].toDomainIngredient(), ingredient1)
        assertEquals(allItems[1].toDomainIngredient(), ingredient2)
    }

    @Test
    @Throws(Exception::class)
    fun daoUpdateIsFavorite_updatesIsFavoriteFieldFromCocktail() = runBlocking {
        // arrange
        addTwoCocktailsToDb()
        // act
        cocktailDao.updateIsFavorite(cocktail1.id, !cocktail1.isFavorite)
        cocktailDao.updateIsFavorite(cocktail2.id, !cocktail2.isFavorite)
        val allItems = cocktailDao.getAll().first()
        // assert
        assertEquals(allItems[1].toDomainCocktail().isFavorite, !cocktail1.isFavorite)
        assertEquals(allItems[0].toDomainCocktail().isFavorite, !cocktail2.isFavorite)
    }

    @Test
    @Throws(Exception::class)
    fun daoUpdateIsOwned_updatesIsOwnedFieldFromIngredient() = runBlocking {
        // arrange
        addTwoIngredientsToDb()
        // act
        ingredientDao.updateIsOwned(ingredient1.name, !ingredient1.isOwned!!)
        ingredientDao.updateIsOwned(ingredient2.name, !ingredient2.isOwned!!)
        val allItems = ingredientDao.getAll().first()
        // assert
        assertEquals(allItems[0].toDomainIngredient().isOwned, !ingredient2.isOwned!!)
        assertEquals(allItems[1].toDomainIngredient().isOwned, !ingredient1.isOwned!!)
    }

    @Test
    @Throws(Exception::class)
    fun daoGetById_returnsTheCocktailWithGivenId() = runBlocking {
        // arrange
        addOneCocktailToDb()
        // act
        val item = cocktailDao.getCocktailById(cocktail1.id)
        // assert
        assertEquals(item.toDomainCocktail(), cocktail1)
    }

    @Test
    @Throws(Exception::class)
    fun daoGetByName_returnsTheIngredientWithGivenName() = runBlocking {
        // arrange
        addOneIngredientToDb()
        // act
        val item = ingredientDao.getIngredientByName(ingredient1.name)
        // assert
        assertEquals(item.toDomainIngredient(), ingredient1)
    }

    @Test
    @Throws(Exception::class)
    fun daoSearchByIngredient_returnsTheCocktailsContainingGivenIngredientName() = runBlocking {
        // arrange
        addTwoCocktailsToDb()
        addTwoIngredientsToDb()
        addCrossRefsToDB()
        // act
        val item1 = cocktailDao.getByIngredient(ingredient1.name).first()
        val item2 = cocktailDao.getByIngredient(ingredient2.name).first()
        // assert
        assertEquals(item1[0].toDomainCocktail(), cocktail1)
        assertEquals(item2[0].toDomainCocktail(), cocktail1)
        assertNotEquals(item1[0].toDomainCocktail(), cocktail2)
        assertNotEquals(item2[0].toDomainCocktail(), cocktail2)
    }

    @Test
    @Throws(Exception::class)
    fun daoUpdateIsOwnedFromIngredient_cocktailsNrOfOwnedIngredientsUpdates() = runBlocking {
        // arrange
        addTwoCocktailsToDb()
        addTwoIngredientsToDb()
        addCrossRefsToDB()
        // act
        val nrOfOwnedIngredients = cocktailDao.getOwnedIngredientsForCocktail(cocktail1.id)
        ingredientDao.updateIsOwned(ingredient1.name, !ingredient1.isOwned!!)
        cocktailDao.updateTransaction(cocktail1.id)
        val item = cocktailDao.getCocktailById(cocktail1.id)
        // assert
        assertNotEquals(item.toDomainCocktail().nrOwnedIngredients, nrOfOwnedIngredients)
    }
}
