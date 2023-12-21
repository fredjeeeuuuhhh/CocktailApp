package com.example.cocktailapp.local.cocktails

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface CocktailDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCocktail(cocktail: DbCocktail)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCrossRef(ref: CrossRef)

    @Transaction
    suspend fun insertCocktailIfNotExisting(cocktail: DbCocktail) {
        val existingCocktail = getCocktailById(cocktail.cocktailId)
        if (existingCocktail == null) {
            insertCocktail(cocktail)
        }
    }

    @Transaction
    suspend fun updateTransaction(id: Int) {
        val nrOfOwned = getOwnedIngredientsForCocktail(id)
        updateNrOfOwned(id, nrOfOwned)
    }

    @Query("UPDATE dbcocktail SET nrOfOwnedIngredients = :nrOfOwned WHERE cocktailId = :cocktailId")
    suspend fun updateNrOfOwned(cocktailId: Int, nrOfOwned: Int)

    @Query("UPDATE dbcocktail SET isFavorite = :isFavorite WHERE cocktailId = :cocktailId")
    suspend fun updateIsFavorite(cocktailId: Int, isFavorite: Boolean)

    @Query("SELECT cocktailId FROM DbCocktail WHERE cocktailId IN (SELECT cocktailId FROM CrossRef WHERE ingredientName LIKE :ingredientName)")
    suspend fun getByIngredientIdOnlyNoFlow(ingredientName: String): List<Int>

    @Query("SELECT * FROM dbcocktail WHERE cocktailId = :id")
    suspend fun getCocktailById(id: Int): DbCocktail

    @Query(
        "SELECT COUNT(ingredientName)\n" +
            "FROM dbingredient\n" +
            "WHERE EXISTS (\n" +
            "    SELECT ingredientName \n" +
            "    FROM CrossRef \n" +
            "    WHERE cocktailId = :cocktailId \n" +
            "    AND REPLACE(REPLACE(ingredientName, ' ', ''), '_', '') IN (\n" +
            "        SELECT REPLACE(REPLACE(ingredientName, ' ', ''), '_', '') \n" +
            "        FROM dbingredient\n" +
            "    )\n" +
            ")\n" +
            "AND isOwned = 1",
    )
    suspend fun getOwnedIngredientsForCocktail(cocktailId: Int): Int

    @Query("SELECT * FROM dbcocktail ORDER BY isFavorite DESC")
    fun getAll(): Flow<List<DbCocktail>>

    @Query("SELECT * FROM dbcocktail WHERE cocktailId = :cocktailId")
    fun getById(cocktailId: Int): Flow<DbCocktail>

    @Query("SELECT * FROM DbCocktail WHERE cocktailId IN (SELECT cocktailId FROM CrossRef WHERE ingredientName LIKE :ingredientName)")
    fun getByIngredient(ingredientName: String): Flow<List<DbCocktail>>
}
