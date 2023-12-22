package com.example.cocktailapp

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.cocktailapp.local.CocktailDB
import com.example.cocktailapp.local.cocktails.CocktailDao
import com.example.cocktailapp.local.cocktails.asDbCocktail
import com.example.cocktailapp.local.cocktails.toDomainCocktail
import com.example.cocktailapp.model.Cocktail
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class CocktailDaoTest {
    private lateinit var cocktailDao: CocktailDao
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

    private suspend fun addOneCocktailToDb() {
        cocktailDao.insertCocktail(cocktail1.asDbCocktail())
    }

    private suspend fun addTwoCocktailsToDb() {
        cocktailDao.insertCocktail(cocktail1.asDbCocktail())
        cocktailDao.insertCocktail(cocktail2.asDbCocktail())
    }

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        cocktailDb = Room.inMemoryDatabaseBuilder(context, CocktailDB::class.java)
            .allowMainThreadQueries()
            .build()
        cocktailDao = cocktailDb.cocktailDao()
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
        val allItems = cocktailDao.getAll().first()
        assertEquals(allItems[0].toDomainCocktail(), cocktail1)
    }

    @Test
    @Throws(Exception::class)
    fun daoGetAllTasks_returnsAllTasksFromDB() = runBlocking {
        addTwoCocktailsToDb()
        val allItems = cocktailDao.getAll().first()
        assertEquals(allItems[0].toDomainCocktail(), cocktail1)
        assertEquals(allItems[1].toDomainCocktail(), cocktail2)
    }
}
