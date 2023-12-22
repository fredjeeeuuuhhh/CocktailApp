package com.example.cocktailapp

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.cocktailapp.local.CocktailDB
import com.example.cocktailapp.local.ingredients.IngredientDao
import com.example.cocktailapp.local.ingredients.asDbIngredient
import com.example.cocktailapp.local.ingredients.toDomainIngredient
import com.example.cocktailapp.model.Ingredient
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class IngredientDaoTest {
    private lateinit var ingredientDao: IngredientDao
    private lateinit var cocktailDb: CocktailDB

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

    private suspend fun addOneCocktailToDb() {
        ingredientDao.insertIngredient(ingredient1.asDbIngredient())
    }

    private suspend fun addTwoCocktailsToDb() {
        ingredientDao.insertIngredient(ingredient1.asDbIngredient())
        ingredientDao.insertIngredient(ingredient2.asDbIngredient())
    }

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        cocktailDb = Room.inMemoryDatabaseBuilder(context, CocktailDB::class.java)
            .allowMainThreadQueries()
            .build()
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
        addOneCocktailToDb()
        val allItems = ingredientDao.getAll().first()
        Assert.assertEquals(allItems[0].toDomainIngredient(), ingredient1)
    }

    @Test
    @Throws(Exception::class)
    fun daoGetAllTasks_returnsAllTasksFromDB() = runBlocking {
        addTwoCocktailsToDb()
        val allItems = ingredientDao.getAll().first()
        Assert.assertEquals(allItems[0].toDomainIngredient(), ingredient1)
        Assert.assertEquals(allItems[1].toDomainIngredient(), ingredient2)
    }
}
